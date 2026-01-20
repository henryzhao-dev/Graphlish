package com.qimiao.graphlishproject.Service;

import com.qimiao.graphlishproject.AI.Interface.AIProvider;
import com.qimiao.graphlishproject.AI.ZhipuAIProviderImpl;
import com.qimiao.graphlishproject.DTO.Bussiness.WordExplanationDTO;
import com.qimiao.graphlishproject.DTO.Frontend.WordDTO;
import com.qimiao.graphlishproject.Entity.Word;
import com.qimiao.graphlishproject.Entity.WordExplanation;
import com.qimiao.graphlishproject.Entity.WordImage;
import com.qimiao.graphlishproject.Mapper.WordExplanationMapper;
import com.qimiao.graphlishproject.Mapper.WordImageMapper;
import com.qimiao.graphlishproject.Mapper.WordMapper;
import com.qimiao.graphlishproject.Service.Interface.ImageSearchService;
import com.qimiao.graphlishproject.Service.Interface.ImageStorageService;
import com.qimiao.graphlishproject.Service.Interface.ImageUrlResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {

    //dependency inject
    private final ImageSearchService imageSearchService;
    private final ImageStorageService imageStorageService;
    private final ImageUrlResolver imageUrlResolver;

    private final WordMapper wordMapper;
    private final WordImageMapper wordImageMapper;
    private final WordExplanationMapper wordExplanationMapper;

    private final AIProvider aiProvider;

    public WordService(
            ImageSearchService imageSearchService,
            ImageStorageService imageStorageService,
            ImageUrlResolver imageUrlResolver,

            WordMapper wordMapper,
            WordImageMapper wordImageMapper,
            WordExplanationMapper wordExplanationMapper,

            AIProvider aiProvider
    ) {
        this.imageSearchService = imageSearchService;
        this.imageStorageService = imageStorageService;
        this.imageUrlResolver = imageUrlResolver;

        this.wordMapper = wordMapper;
        this.wordImageMapper = wordImageMapper;
        this.wordExplanationMapper = wordExplanationMapper;

        this.aiProvider = aiProvider;
    }

    //main method
    public WordDTO getWord(String keyword) {

        //normalize keyword
        String normalizedKeyword = normalize(keyword);

        //query word based on keyword
        Word word = wordMapper.queryByText(normalizedKeyword);

        //create WordDTO object
        WordDTO wordDTO = new WordDTO();

        //invoke method of getting image to set wordDTO parameters
        wordDTO = getWordImage(normalizedKeyword, wordDTO, word);

        //invoke method of getting explanation to set wordDTO parameters
        wordDTO = getWordExplanation(normalizedKeyword, wordDTO, word);

        return wordDTO;

    }





    //section of get words image
    //
    @Transactional
    public WordDTO getWordImage(String normalizedKeyword, WordDTO wordDTO, Word word) {

        //query word
        //Word word = wordMapper.queryByText(normalizedKeyword);

        //if word have existed, executing here
        if (word != null) {
            return getUrlFromExistingWord(word, wordDTO);
        }

        //if word doesn't exist, executing here
        return createWordWithImages(normalizedKeyword, wordDTO);

    }

    //method that word have existed
    private WordDTO getUrlFromExistingWord(Word word, WordDTO wordDTO) {

        List<WordImage> images =
                wordImageMapper.queryByWordId(word.getId());//word exist, so we use ID query

        List<String> imageUrls = new ArrayList<>();
        for (WordImage image : images) {
            imageUrls.add(
                    imageUrlResolver.resolve(image.getImagePath()));
        }

        wordDTO.setImageUrls(imageUrls);
        wordDTO.setText(word.getText());

        return wordDTO;

        //return new WordDTO(word.getText(), imageUrls);

    }



    private WordDTO createWordWithImages(String normalizedKeyword, WordDTO wordDTO) {

        //search images from third-part API
        List<String> sourceImageUrls =
                imageSearchService.searchImages(normalizedKeyword);

        //take first 3 pictures (prevent images too much)
        int limit = Math.min(3, sourceImageUrls.size());
        List<String> selectedUrls = sourceImageUrls.subList(0, limit);

        //insert new word
        Word word = new Word();
        word.setText(normalizedKeyword);
        wordMapper.insert(word);
        Long wordId = word.getId();

        //download + upload + save word_image(image_path) in database
        List<String> imageUrlsForFront = new ArrayList<>();

        for (int i = 0; i < selectedUrls.size(); i++) {

            String sourceUrl = selectedUrls.get(i);

            //upload to R2 and get image_path
            String imagePath =
                    imageStorageService.storeImage(sourceUrl, normalizedKeyword);

            //save to database
            WordImage image = new WordImage();
            image.setWordId(wordId);
            image.setImagePath(imagePath);
            image.setSortOrder(i+1);
            wordImageMapper.insert(image);

            //directly splicing URL of frontend (one less database query)
            String completeUrl = imageUrlResolver.resolve(imagePath);
            imageUrlsForFront.add(completeUrl);
        }

        wordDTO.setText(word.getText());
        wordDTO.setImageUrls(imageUrlsForFront);

        return wordDTO;

        //return new WordDTO(normalizedKeyword, imageUrlsForFront);

    }


    private String normalize(String keyword) {

        return keyword == null
                ? null
                : keyword.trim().toLowerCase();

    }



    //section of get words explanation
    //
    @Transactional
    public WordDTO getWordExplanation(String normalizedKeyword, WordDTO wordDTO, Word word) {


        //if word have existed, executing here
        if (word != null) {
            return getExplanationFromExistingWord(word,  wordDTO);
        }

        //if word doesn't exist, executing here
        return createWordWithExplanation(normalizedKeyword, wordDTO);

    }


    public WordDTO getExplanationFromExistingWord(Word word, WordDTO wordDTO) {

        WordExplanation explanation =
                wordExplanationMapper.queryByWordId(word.getId());

        wordDTO.setExplanation(explanation.getExplanation());
        wordDTO.setExample(explanation.getExample());

        return wordDTO;

    }

    public WordDTO createWordWithExplanation(String normalizedKeyword, WordDTO wordDTO) {

        //calling AI to generate explanation
        try {
            //querying by keyword to get word object because previous method---createWordWithImages
            //has already create new data by normalizedKeyword
            Word word = wordMapper.queryByText(normalizedKeyword);

            //calling AI to get result
            String aiResult =
                    aiProvider.explainWord(normalizedKeyword);

            //parse AI result
            String explanation = extractBetween(aiResult, "Explanation:", "Example:");
            String example = extractAfter(aiResult, "Example");

            //set wordExplanation object
            WordExplanation wordExplanation = new WordExplanation();
            wordExplanation.setWordId(word.getId());
            wordExplanation.setExplanation(explanation.trim());
            wordExplanation.setExample(example.trim());

            //save to database
            wordExplanationMapper.insert(wordExplanation);

            //set fields of explanation and example in wordDTO
            wordDTO.setExplanation(explanation);
            wordDTO.setExample(example);

            //return wordDTO to main getWord method
            return wordDTO;

        } catch (Exception e) {

            wordDTO.setExplanation("AI explanation is temporarily unavailable");
            wordDTO.setExample(null);
            return wordDTO;

        }
    }

    private String extractBetween(String text, String start, String end) {
        int s = text.indexOf(start);
        int e = text.indexOf(end);
        if (s == -1 || e == -1) return "";
        return text.substring(s + start.length(), e);
    }

    private String extractAfter(String text, String start) {
        int s = text.indexOf(start);
        if (s == -1) return "";
        return text.substring(s + start.length());
    }



}

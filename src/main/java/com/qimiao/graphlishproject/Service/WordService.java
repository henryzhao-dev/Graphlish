package com.qimiao.graphlishproject.Service;

import com.qimiao.graphlishproject.DTO.WordDTO;
import com.qimiao.graphlishproject.Entity.Word;
import com.qimiao.graphlishproject.Entity.WordImage;
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

    public WordService(
            ImageSearchService imageSearchService,
            ImageStorageService imageStorageService,
            ImageUrlResolver imageUrlResolver,
            WordMapper wordMapper,
            WordImageMapper wordImageMapper
    ) {
        this.imageSearchService = imageSearchService;
        this.imageStorageService = imageStorageService;
        this.imageUrlResolver = imageUrlResolver;
        this.wordMapper = wordMapper;
        this.wordImageMapper = wordImageMapper;
    }


    @Transactional
    public WordDTO getWordImage(String keyword) {

        //normalize keyword
        String normalizedKeyword = normalize(keyword);

        //query word
        Word word = wordMapper.queryByText(normalizedKeyword);

        //if word have existed, executing here
        if (word != null) {
            return buildFromExistingWord(word);
        }

        //if word doesn't exist, executing here
        return createWordWithImages(normalizedKeyword);


    }

    //method that word have existed
    private WordDTO buildFromExistingWord(Word word) {

        List<WordImage> images =
                wordImageMapper.queryByWordId(word.getId());//word exist, so we use ID query

        List<String> imageUrls = new ArrayList<>();
        for (WordImage image : images) {
            imageUrls.add(
                    imageUrlResolver.resolve(image.getImagePath()));
        }

        return new WordDTO(word.getText(), imageUrls);

    }



    private WordDTO createWordWithImages(String keyword) {

        //search images from third-part API
        List<String> sourceImageUrls =
                imageSearchService.searchImages(keyword);

        //take first 3 pictures (prevent images too much)
        int limit = Math.min(3, sourceImageUrls.size());
        List<String> selectedUrls = sourceImageUrls.subList(0, limit);

        //insert new word
        Word word = new Word();
        word.setText(keyword);
        wordMapper.insert(word);
        Long wordId = word.getId();

        //download + upload + save word_image(image_path) in database
        List<String> imageUrlsForFront = new ArrayList<>();

        for (int i = 0; i < selectedUrls.size(); i++) {

            String sourceUrl = selectedUrls.get(i);

            //upload to R2 and get image_path
            String imagePath =
                    imageStorageService.storeImage(sourceUrl, keyword);

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

        return new WordDTO(keyword, imageUrlsForFront);

    }



    private String normalize(String keyword) {

        return keyword == null
                ? null
                : keyword.trim().toLowerCase();

    }

}

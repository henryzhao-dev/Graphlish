package com.qimiao.graphlishproject.Controller;


import com.qimiao.graphlishproject.DTO.Frontend.WordDTO;
import com.qimiao.graphlishproject.Service.WordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Word")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/getWordImageAndExplanation")
    public WordDTO getWordImageAndExplanation(@RequestParam String keyword) {

        return wordService.getWord(keyword);

    }

}

package com.qimiao.graphlishproject.Controller;


import com.qimiao.graphlishproject.DTO.WordDTO;
import com.qimiao.graphlishproject.Service.WordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/WordImage")
public class WordImageController {

    private final WordService wordService;

    public WordImageController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/getImage")
    public WordDTO getImage(@RequestParam String keyword) {
        return wordService.getWordImage(keyword);
    }

}

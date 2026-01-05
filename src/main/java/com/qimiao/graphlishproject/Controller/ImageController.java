package com.qimiao.graphlishproject.Controller;

import com.qimiao.graphlishproject.Service.UnsplashImageSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private UnsplashImageSearchServiceImpl imageSearchService;

    @GetMapping("/searchImage")
    public List<String> searchImage(String keyword){

        List<String> images = imageSearchService.searchImages(keyword);
        return images;

    }



}

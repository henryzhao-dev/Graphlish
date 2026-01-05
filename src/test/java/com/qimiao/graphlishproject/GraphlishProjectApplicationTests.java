package com.qimiao.graphlishproject;

import com.qimiao.graphlishproject.Service.ImageStorageServiceImpl;
import com.qimiao.graphlishproject.Service.UnsplashImageSearchServiceImpl;
import com.qimiao.graphlishproject.Service.Interface.ImageStorageService;
import com.qimiao.graphlishproject.Service.Interface.ImageSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GraphlishProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test(){

        ImageSearchService service = new UnsplashImageSearchServiceImpl();

        List<String> images = service.searchImages("apple");

        for (String image : images) {
            System.out.println(image);
            System.out.println();
        }

    }



}

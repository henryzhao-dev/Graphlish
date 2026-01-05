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

    @Test
    public void test1(){

        ImageStorageService service = new ImageStorageServiceImpl();

        String image = service.acquireImage("https://images.unsplash.com/photo-1630563451961-ac2ff27616ab?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w4NDYwNTN8MHwxfHNlYXJjaHwxfHxhcHBsZXxlbnwwfHx8fDE3NjcwOTgzNDF8MA&ixlib=rb-4.1.0&q=80&w=1080","apple");

        System.out.println(image);
    }

}

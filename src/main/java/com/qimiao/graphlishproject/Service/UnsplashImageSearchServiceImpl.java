package com.qimiao.graphlishproject.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qimiao.graphlishproject.DTO.UnsplashResponse;
import com.qimiao.graphlishproject.Service.Interface.ImageSearchService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnsplashImageSearchServiceImpl implements ImageSearchService {

    private static final String ACCESS_KEY = "4deg9qacExLKpruBjKmdBDj5s5BVJJFT2yr7T2O_9fU";
    private static final String API_URL = "https://api.unsplash.com/search/photos";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public List<String> searchImages(String keyword) {

        String url = API_URL +
                "?query=" + keyword +
                "&per_page=3" +
                "&client_id=" + ACCESS_KEY;

        try {

            String json = restTemplate.getForObject(url, String.class);

            UnsplashResponse response =
                    objectMapper.readValue(json, UnsplashResponse.class);

            List<String> imageUrls = new ArrayList<>();
            if (response.results != null){

                for (UnsplashResponse.Result result : response.results) {

                    if (result.urls != null && result.urls.regular != null){
                        imageUrls.add(result.urls.regular);
                    }
                }
            }

            return imageUrls;

        } catch (Exception e) {
            throw new RuntimeException("Unsplash search image error");
        }



    }
}

package com.qimiao.graphlishproject.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qimiao.graphlishproject.Config.UnsplashProperties;
import com.qimiao.graphlishproject.DTO.Tech.UnsplashResponse;
import com.qimiao.graphlishproject.Service.Interface.ImageSearchService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnsplashImageSearchServiceImpl implements ImageSearchService {


    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UnsplashProperties unsplashProperties;

    public UnsplashImageSearchServiceImpl(UnsplashProperties unsplashProperties) {
        this.unsplashProperties = unsplashProperties;
    }


    @Override
    public List<String> searchImages(String keyword) {

        String apiUrl = unsplashProperties.getApiUrl();
        String accessKey = unsplashProperties.getAccessKey();


        String url = apiUrl +
                "?query=" + keyword +
                "&per_page=3" +
                "&client_id=" + accessKey;

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

package com.qimiao.graphlishproject.DTO;

import lombok.Data;

import java.util.List;

@Data
public class WordDTO {

    private String text;
    private List<String> imageUrls;


    public WordDTO(String text, List<String> imageUrls) {
        this.text = text;
        this.imageUrls = imageUrls;
    }

}

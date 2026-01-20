package com.qimiao.graphlishproject.DTO.Frontend;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WordDTO {

    //word itself
    private String text;
    //word image
    private List<String> imageUrls;
    //AI explanation
    private String explanation;
    //AI example
    private String example;
    //AI availability
    private boolean aiAvailable;



}

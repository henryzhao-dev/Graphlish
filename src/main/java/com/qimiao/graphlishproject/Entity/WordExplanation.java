package com.qimiao.graphlishproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordExplanation {

    private Long id;
    private Long wordId;
    private String explanation;
    private String example;

}

package com.qimiao.graphlishproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Word {

    private Long id;
    private String text;
    private String explainType;
    private LocalDateTime createdAt;

}

package com.qimiao.graphlishproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordImage {

    private Long id;
    private Long wordId;
    private String imagePath;
    private String source;
    private Integer sortOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;

}

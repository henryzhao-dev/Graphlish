package com.qimiao.graphlishproject.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "unsplash")
public class UnsplashProperties {

    private String accessKey;
    private String apiUrl;
}
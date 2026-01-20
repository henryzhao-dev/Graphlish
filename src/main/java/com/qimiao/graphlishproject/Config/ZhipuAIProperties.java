package com.qimiao.graphlishproject.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "zhipu.ai")
public class ZhipuAIProperties {

    private String endpoint;
    private String apiKey;
    private String model;
}
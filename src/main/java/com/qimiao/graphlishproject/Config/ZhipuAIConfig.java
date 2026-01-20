package com.qimiao.graphlishproject.Config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ZhipuAIProperties.class)
public class ZhipuAIConfig {
    //not write Bean here temporarily
    //main function is to make the Properties effective
}
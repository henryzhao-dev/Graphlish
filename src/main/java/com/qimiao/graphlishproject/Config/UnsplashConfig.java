package com.qimiao.graphlishproject.Config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UnsplashProperties.class)
public class UnsplashConfig {
}
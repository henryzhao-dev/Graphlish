package com.qimiao.graphlishproject.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "r2")
public class R2Properties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String region;
    private String bucketName;
    private String publicDomain;

}

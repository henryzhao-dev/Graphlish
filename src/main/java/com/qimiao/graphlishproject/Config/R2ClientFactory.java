package com.qimiao.graphlishproject.Config;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

public class R2ClientFactory {

    public static S3Client create() {
        return S3Client.builder()
                .endpointOverride(URI.create("https://4d13b2f81824bec182e3971a0b7bbc44.r2.cloudflarestorage.com"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        "aab6e98ea7a59c030c6f6edd65e38c63",
                                        "c059e1402ba31a86c09bc32a9fe045f5ae57e1c8bb2065bd54641b1d2df82714"
                                )
                        )
                )
                .region(Region.US_EAST_1)
                .build();
    }
}
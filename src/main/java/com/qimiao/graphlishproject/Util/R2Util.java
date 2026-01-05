package com.qimiao.graphlishproject.Util;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URI;
import java.nio.file.Paths;
import java.time.Duration;

public class R2Util {

    // ======== 你要填写的信息 =========
    private static final String ACCOUNT_ID = "4d13b2f81824bec182e3971a0b7bbc44";
    private static final String ACCESS_KEY = "aab6e98ea7a59c030c6f6edd65e38c63";
    private static final String SECRET_KEY = "c059e1402ba31a86c09bc32a9fe045f5ae57e1c8bb2065bd54641b1d2df82714";
    private static final String BUCKET_NAME = "graphlish";
    // ==================================

    private static final String ENDPOINT = 
            "https://" + ACCOUNT_ID + ".r2.cloudflarestorage.com";

    private static final AwsBasicCredentials CREDS =
            AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

    private static final Region REGION = Region.of("auto");


    // ================================
    // 1. 上传文件（你已经跑通的逻辑）
    // ================================
    public static void uploadFile(String objectKey, String localFilePath) {
        S3Client s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(CREDS))
                .endpointOverride(URI.create(ENDPOINT))
                .region(REGION)
                .build();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(objectKey)
                .build();

        s3.putObject(putObjectRequest, Paths.get(localFilePath));
        System.out.println("upload success");
    }


    // ================================
    // 2. 生成临时访问链接（核心方法）
    // ================================
    public static String generatePresignedUrl(String objectKey, Duration expireTime) {

        S3Presigner presigner = S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(CREDS))
                .endpointOverride(URI.create(ENDPOINT))
                .region(REGION)
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(expireTime)
                .getObjectRequest(getObjectRequest)
                .build();

        return presigner.presignGetObject(presignRequest).url().toString();
    }
}
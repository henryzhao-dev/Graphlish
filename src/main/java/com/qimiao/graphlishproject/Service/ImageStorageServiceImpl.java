package com.qimiao.graphlishproject.Service;

import com.qimiao.graphlishproject.Config.R2ClientFactory;
import com.qimiao.graphlishproject.Service.Interface.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final S3Client s3Client = R2ClientFactory.create();

    private static final String BUCKET_NAME = "graphlish";


    @Override
    public String storeImage(String sourceImageUrl, String word) {

        byte[] imageBytes = restTemplate.getForObject(sourceImageUrl, byte[].class);

        if (imageBytes == null || imageBytes.length == 0){
            throw new RuntimeException("image failed to download");
        }

        //generate objectKey(image_path)
        String imagePath = buildImagePath(word);

        //upload to R2
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(imagePath)
                .contentType("image/jpeg")
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(imageBytes));

        //return image_path (store in database)
        return imagePath;


    }

    private String buildImagePath(String word){
        return "words/"
                + word.toLowerCase()
                + "/"
                + UUID.randomUUID()
                + ".jpg";
    }

}

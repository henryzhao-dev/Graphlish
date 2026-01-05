package com.qimiao.graphlishproject.Service.Interface;

public interface ImageStorageService {

    /**
     * 从外部 URL 获取图片，并上传到 R2
     * @return image_path
     */
    String storeImage(String imageUrl, String word);
}
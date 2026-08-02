package com.upendra.portfolio.media.service;


import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.media.dto.response.UploadResponse;


public interface MediaService {


    UploadResponse uploadImage(
            MultipartFile file,
            String folder
    );


    UploadResponse uploadFile(
            MultipartFile file,
            String folder
    );


    void deleteMedia(
            String publicId,
            String resourceType
    );


}
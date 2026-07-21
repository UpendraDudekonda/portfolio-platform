package com.upendra.portfolio.port.client;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.media.UploadResponse;

@Component
public class MediaClientFallback implements MediaClient {

    @Override
    public ApiResponse<UploadResponse> uploadImage(
            MultipartFile file,
            String folder) {

        return ApiResponse.<UploadResponse>builder()
                .success(false)
                .message("Media service is temporarily unavailable. Unable to upload image.")
                .data(null)
                .build();
    }

    @Override
    public ApiResponse<UploadResponse> uploadFile(
            MultipartFile file,
            String folder) {

        return ApiResponse.<UploadResponse>builder()
                .success(false)
                .message("Media service is temporarily unavailable. Unable to upload file.")
                .data(null)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteMedia(
            String publicId,
            String resourceType) {

        return ApiResponse.<Void>builder()
                .success(false)
                .message("Media service is temporarily unavailable. Unable to delete media.")
                .build();
    }
}
package com.upendra.portfolio.port.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.media.UploadResponse;


@FeignClient(name = "media-service",
				fallback=MediaClientFallback.class
)
public interface MediaClient {

    @PostMapping(
            value = "/api/v1/media/upload/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ApiResponse<UploadResponse> uploadImage(
            @RequestPart("file") MultipartFile file,
            @RequestParam("folder") String folder
    );

    @PostMapping(
            value = "/api/v1/media/upload/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ApiResponse<UploadResponse> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("folder") String folder
    );

    @DeleteMapping("/api/v1/media")
    ApiResponse<Void> deleteMedia(
            @RequestParam("publicId") String publicId,
            @RequestParam("resourceType") String resourceType
    );
}
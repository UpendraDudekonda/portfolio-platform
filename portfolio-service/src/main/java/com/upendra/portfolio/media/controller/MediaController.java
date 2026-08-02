package com.upendra.portfolio.media.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.media.dto.response.UploadResponse;
import com.upendra.portfolio.media.service.MediaService;


import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaController {



    private final MediaService mediaService;




    @PostMapping(
            value="/upload/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<UploadResponse> uploadImage(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam(defaultValue="profile")
            String folder

    ){


        return ApiResponse.<UploadResponse>builder()

                .success(true)

                .message(
                    "Image uploaded successfully"
                )

                .data(
                    mediaService.uploadImage(
                            file,
                            folder
                    )
                )

                .build();

    }







    @PostMapping(
            value="/upload/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<UploadResponse> uploadFile(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam(defaultValue="documents")
            String folder

    ){


        return ApiResponse.<UploadResponse>builder()

                .success(true)

                .message(
                    "File uploaded successfully"
                )

                .data(
                    mediaService.uploadFile(
                            file,
                            folder
                    )
                )

                .build();

    }







    @DeleteMapping
    public ApiResponse<Void> deleteMedia(

            @RequestParam String publicId,

            @RequestParam(defaultValue="image")
            String resourceType

    ){


        mediaService.deleteMedia(
                publicId,
                resourceType
        );


        return ApiResponse.<Void>builder()

                .success(true)

                .message(
                    "Media deleted successfully"
                )

                .build();

    }


}
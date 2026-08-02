package com.upendra.portfolio.media.service.impl;


import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.upendra.portfolio.media.dto.response.UploadResponse;
import com.upendra.portfolio.media.helper.CloudinaryHelper;
import com.upendra.portfolio.media.service.MediaService;


import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {


    private final CloudinaryHelper cloudinaryHelper;



    @Override
    public UploadResponse uploadImage(
            MultipartFile file,
            String folder) {


        Map result =
                cloudinaryHelper.uploadImage(
                        file,
                        folder
                );


        return mapResponse(
                result,
                file
        );

    }





    @Override
    public UploadResponse uploadFile(
            MultipartFile file,
            String folder) {


        Map result =
                cloudinaryHelper.uploadFile(
                        file,
                        folder
                );


        return mapResponse(
                result,
                file
        );

    }





    @Override
    public void deleteMedia(
            String publicId,
            String resourceType) {


        cloudinaryHelper.deleteFile(
                publicId,
                resourceType
        );

    }





    private UploadResponse mapResponse(
            Map result,
            MultipartFile file) {


        return UploadResponse.builder()

                .publicId(
                        result.get("public_id")
                        .toString()
                )

                .url(
                        result.get("url")
                        .toString()
                )

                .secureUrl(
                        result.get("secure_url")
                        .toString()
                )

                .resourceType(
                        result.get("resource_type")
                        .toString()
                )

                .size(
                        Long.valueOf(
                            result.get("bytes")
                            .toString()
                        )
                )

                .originalFilename(
                        file.getOriginalFilename()
                )

                .build();

    }


}
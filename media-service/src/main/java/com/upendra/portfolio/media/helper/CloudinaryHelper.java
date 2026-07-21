package com.upendra.portfolio.media.helper;


import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class CloudinaryHelper {


    private final Cloudinary cloudinary;



    /**
     * Upload Image
     */
    public Map uploadImage(
            MultipartFile file,
            String folder) {


        try {


            return cloudinary.uploader()
                    .upload(

                            file.getBytes(),

                            ObjectUtils.asMap(

                                    "folder",
                                    "portfolio/" + folder,

                                    "resource_type",
                                    "image"

                            )

                    );


        } catch (IOException e) {

            throw new RuntimeException(
                    "Image upload failed",
                    e
            );
        }

    }





    /**
     * Upload Raw Files
     * Example:
     * PDF Resume
     */
    public Map uploadFile(
            MultipartFile file,
            String folder) {


        try {


            return cloudinary.uploader()
                    .upload(

                            file.getBytes(),

                            ObjectUtils.asMap(

                                    "folder",
                                    "portfolio/" + folder,

                                    "resource_type",
                                    "raw"

                            )

                    );


        } catch (IOException e) {


            throw new RuntimeException(
                    "File upload failed",
                    e
            );

        }


    }





    /**
     * Delete Media
     */
    public void deleteFile(
            String publicId,
            String resourceType) {


        try {


            cloudinary.uploader()
                    .destroy(

                            publicId,

                            ObjectUtils.asMap(

                                    "resource_type",
                                    resourceType

                            )

                    );


        } catch (IOException e) {


            throw new RuntimeException(
                    "File deletion failed",
                    e
            );

        }

    }

}
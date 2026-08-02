package com.upendra.portfolio.port.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.auth.security.SecurityUtils;
import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.request.CreateCertificationRequest;
import com.upendra.portfolio.port.dto.request.UpdateCertificationRequest;
import com.upendra.portfolio.port.dto.response.CertificationResponse;
import com.upendra.portfolio.port.service.CertificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/profile/certification")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping
    public ApiResponse<CertificationResponse> createCertification(
           
            @Valid @RequestBody CreateCertificationRequest request) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
        return ApiResponse.<CertificationResponse>builder()
                .success(true)
                .message("Certification created successfully.")
                .data(certificationService.createCertification(
                		userUuid,
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CertificationResponse>> getMyCertifications(
            ) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
        return ApiResponse.<List<CertificationResponse>>builder()
                .success(true)
                .message("Certifications fetched successfully.")
                .data(certificationService.getMyCertifications(
                		userUuid))
                .build();
    }

    @PutMapping("/{certificationId}")
    public ApiResponse<CertificationResponse> updateCertification(
            
            @PathVariable Long certificationId,
            @Valid @RequestBody UpdateCertificationRequest request) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
    	
        return ApiResponse.<CertificationResponse>builder()
                .success(true)
                .message("Certification updated successfully.")
                .data(certificationService.updateCertification(
                		userUuid,
                        certificationId,
                        request))
                .build();
    }

    @DeleteMapping("/{certificationId}")
    public ApiResponse<Void> deleteCertification(
            
            @PathVariable Long certificationId) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
    	
        certificationService.deleteCertification(
        		userUuid,
                certificationId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Certification deleted successfully.")
                .build();
    }
    
    
    @PostMapping(
    	    value = "/{certificationId}/upload",
    	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    	)
    	public ApiResponse<CertificationResponse> uploadCertificate(

    	      

    	        @PathVariable Long certificationId,

    	        @RequestParam("file") MultipartFile file) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
    	
    	    return ApiResponse.<CertificationResponse>builder()
    	            .success(true)
    	            .message("Certificate uploaded successfully.")
    	            .data(certificationService.uploadCertificate(
    	            		userUuid,
    	                    certificationId,
    	                    file))
    	            .build();
    	}
    
    @DeleteMapping("/{certificationId}/upload")
    public ApiResponse<CertificationResponse> deleteCertificate(

            

            @PathVariable Long certificationId) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
    	
        return ApiResponse.<CertificationResponse>builder()
                .success(true)
                .message("Certificate removed successfully.")
                .data(certificationService.deleteCertificate(
                		userUuid,
                        certificationId))
                .build();
    }
}

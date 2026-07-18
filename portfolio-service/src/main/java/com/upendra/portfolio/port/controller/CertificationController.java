package com.upendra.portfolio.port.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestHeader("X-User-UUID") String userUuid,
            @Valid @RequestBody CreateCertificationRequest request) {

        return ApiResponse.<CertificationResponse>builder()
                .success(true)
                .message("Certification created successfully.")
                .data(certificationService.createCertification(
                        UUID.fromString(userUuid),
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CertificationResponse>> getMyCertifications(
            @RequestHeader("X-User-UUID") String userUuid) {

        return ApiResponse.<List<CertificationResponse>>builder()
                .success(true)
                .message("Certifications fetched successfully.")
                .data(certificationService.getMyCertifications(
                        UUID.fromString(userUuid)))
                .build();
    }

    @PutMapping("/{certificationId}")
    public ApiResponse<CertificationResponse> updateCertification(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long certificationId,
            @Valid @RequestBody UpdateCertificationRequest request) {

        return ApiResponse.<CertificationResponse>builder()
                .success(true)
                .message("Certification updated successfully.")
                .data(certificationService.updateCertification(
                        UUID.fromString(userUuid),
                        certificationId,
                        request))
                .build();
    }

    @DeleteMapping("/{certificationId}")
    public ApiResponse<Void> deleteCertification(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long certificationId) {

        certificationService.deleteCertification(
                UUID.fromString(userUuid),
                certificationId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Certification deleted successfully.")
                .build();
    }
}

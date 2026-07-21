package com.upendra.portfolio.port.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.port.dto.request.CreateCertificationRequest;
import com.upendra.portfolio.port.dto.request.UpdateCertificationRequest;
import com.upendra.portfolio.port.dto.response.CertificationResponse;

public interface CertificationService {

    CertificationResponse createCertification(
            UUID userUuid,
            CreateCertificationRequest request);

    List<CertificationResponse> getMyCertifications(
            UUID userUuid);

    CertificationResponse updateCertification(
            UUID userUuid,
            Long certificationId,
            UpdateCertificationRequest request);

    void deleteCertification(
            UUID userUuid,
            Long certificationId);
    
    CertificationResponse uploadCertificate(
            UUID userUuid,
            Long certificationId,
            MultipartFile file);

    CertificationResponse deleteCertificate(
            UUID userUuid,
            Long certificationId);

}


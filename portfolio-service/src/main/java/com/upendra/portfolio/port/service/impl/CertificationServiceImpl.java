package com.upendra.portfolio.port.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.common.exception.BadRequestException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.client.MediaClient;
import com.upendra.portfolio.port.dto.request.CreateCertificationRequest;
import com.upendra.portfolio.port.dto.request.UpdateCertificationRequest;
import com.upendra.portfolio.port.dto.response.CertificationResponse;
import com.upendra.portfolio.port.entity.Certification;
import com.upendra.portfolio.port.repository.CertificationRepository;
import com.upendra.portfolio.port.service.CertificationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;
    
    private final MediaClient mediaClient;

    @Override
    public CertificationResponse createCertification(
            UUID userUuid,
            CreateCertificationRequest request) {

        validateCertification(request.getIssueDate(), request.getExpiryDate());

        if (certificationRepository.existsByUserUuidAndName(
                userUuid,
                request.getName())) {

            throw new BadRequestException(
                    "Certification already exists.");
        }

        Certification certification = Certification.builder()
                .userUuid(userUuid)
                .name(request.getName())
                .issuingOrganization(request.getIssuingOrganization())
                .issueDate(request.getIssueDate())
                .expiryDate(request.getExpiryDate())
                .credentialId(request.getCredentialId())
                .credentialUrl(request.getCredentialUrl())
                .displayOrder(request.getDisplayOrder())
                .build();

        Certification saved = certificationRepository.save(certification);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public List<CertificationResponse> getMyCertifications(UUID userUuid) {

        return certificationRepository
                .findByUserUuidOrderByDisplayOrderAsc(userUuid)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CertificationResponse updateCertification(
            UUID userUuid,
            Long certificationId,
            UpdateCertificationRequest request) {

        Certification certification = certificationRepository
                .findByIdAndUserUuid(certificationId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Certification not found."));

        if (request.getName() != null) {
            certification.setName(request.getName());
        }

        if (request.getIssuingOrganization() != null) {
            certification.setIssuingOrganization(request.getIssuingOrganization());
        }

        if (request.getIssueDate() != null) {
            certification.setIssueDate(request.getIssueDate());
        }

        if (request.getExpiryDate() != null) {
            certification.setExpiryDate(request.getExpiryDate());
        }

        if (request.getCredentialId() != null) {
            certification.setCredentialId(request.getCredentialId());
        }

        if (request.getCredentialUrl() != null) {
            certification.setCredentialUrl(request.getCredentialUrl());
        }

        if (request.getDisplayOrder() != null) {
            certification.setDisplayOrder(request.getDisplayOrder());
        }

        validateCertification(
                certification.getIssueDate(),
                certification.getExpiryDate());

        Certification saved = certificationRepository.save(certification);

        return mapToResponse(saved);
    }

    @Override
    public void deleteCertification(
            UUID userUuid,
            Long certificationId) {

        Certification certification = certificationRepository
                .findByIdAndUserUuid(certificationId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Certification not found."));

        certificationRepository.delete(certification);
    }

    private void validateCertification(
            LocalDate issueDate,
            LocalDate expiryDate) {

        if (issueDate != null
                && expiryDate != null
                && expiryDate.isBefore(issueDate)) {

            throw new BadRequestException(
                    "Expiry date cannot be before issue date.");
        }
    }

    private CertificationResponse mapToResponse(
            Certification certification) {

         return CertificationResponse.builder()
                .id(certification.getId())
                .name(certification.getName())
                .issuingOrganization(certification.getIssuingOrganization())
                .issueDate(certification.getIssueDate())
                .expiryDate(certification.getExpiryDate())
                .credentialId(certification.getCredentialId())
                .credentialUrl(certification.getCredentialUrl())
                .certificateUrl(certification.getCertificateUrl())
                .displayOrder(certification.getDisplayOrder())
                .createdAt(certification.getCreatedAt())
                .updatedAt(certification.getUpdatedAt())
                .build();
    }

    @Override
    public CertificationResponse uploadCertificate(
            UUID userUuid,
            Long certificationId,
            MultipartFile file) {

        Certification certification = certificationRepository
                .findByIdAndUserUuid(certificationId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Certification not found."));

        // Delete old certificate if it exists
        if (certification.getCertificatePublicId() != null
                && !certification.getCertificatePublicId().isBlank()) {

            mediaClient.deleteMedia(
                    certification.getCertificatePublicId(),
                    "raw");
        }

        // Upload new certificate
        var response = mediaClient.uploadFile(
                file,
                "portfolio/certification");

        var upload = response.getData();

        certification.setCertificateUrl(upload.getSecureUrl());
        certification.setCertificatePublicId(upload.getPublicId());

        Certification saved = certificationRepository.save(certification);

        return mapToResponse(saved);
    }

    @Override
    public CertificationResponse deleteCertificate(
            UUID userUuid,
            Long certificationId) {

        Certification certification = certificationRepository
                .findByIdAndUserUuid(certificationId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Certification not found."));

        if (certification.getCertificatePublicId() == null
                || certification.getCertificatePublicId().isBlank()) {

            throw new BadRequestException("No certificate uploaded.");
        }

        mediaClient.deleteMedia(
                certification.getCertificatePublicId(),
                "raw");

        certification.setCertificateUrl(null);
        certification.setCertificatePublicId(null);

        Certification saved = certificationRepository.save(certification);

        return mapToResponse(saved);
    }
}

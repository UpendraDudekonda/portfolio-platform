package com.upendra.portfolio.port.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.exception.BadRequestException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.dto.request.CreateSocialLinkRequest;
import com.upendra.portfolio.port.dto.request.UpdateSocialLinkRequest;
import com.upendra.portfolio.port.dto.response.SocialLinkResponse;
import com.upendra.portfolio.port.entity.SocialLink;
import com.upendra.portfolio.port.repository.SocialLinkRepository;
import com.upendra.portfolio.port.service.SocialLinkService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SocialLinkServiceImpl implements SocialLinkService {

    private final SocialLinkRepository socialLinkRepository;

    @Override
    public SocialLinkResponse createSocialLink(
            UUID userUuid,
            CreateSocialLinkRequest request) {

        if (socialLinkRepository.existsByUserUuidAndPlatform(
                userUuid,
                request.getPlatform())) {

            throw new BadRequestException(
                    request.getPlatform() + " link already exists.");
        }

        SocialLink socialLink = SocialLink.builder()
                .userUuid(userUuid)
                .platform(request.getPlatform())
                .url(request.getUrl())
                .displayOrder(request.getDisplayOrder())
                .build();

        SocialLink saved = socialLinkRepository.save(socialLink);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public List<SocialLinkResponse> getMySocialLinks(UUID userUuid) {

        return socialLinkRepository
                .findByUserUuidOrderByDisplayOrderAsc(userUuid)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SocialLinkResponse updateSocialLink(
            UUID userUuid,
            Long socialLinkId,
            UpdateSocialLinkRequest request) {

        SocialLink socialLink = socialLinkRepository
                .findByIdAndUserUuid(socialLinkId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Social link not found."));

        if (request.getPlatform() != null
                && request.getPlatform() != socialLink.getPlatform()) {

            if (socialLinkRepository.existsByUserUuidAndPlatform(
                    userUuid,
                    request.getPlatform())) {

                throw new BadRequestException(
                        request.getPlatform() + " already exists.");
            }

            socialLink.setPlatform(request.getPlatform());
        }

        if (request.getUrl() != null) {
            socialLink.setUrl(request.getUrl());
        }

        if (request.getDisplayOrder() != null) {
            socialLink.setDisplayOrder(request.getDisplayOrder());
        }

        SocialLink saved = socialLinkRepository.save(socialLink);

        return mapToResponse(saved);
    }

    @Override
    public void deleteSocialLink(
            UUID userUuid,
            Long socialLinkId) {

        SocialLink socialLink = socialLinkRepository
                .findByIdAndUserUuid(socialLinkId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Social link not found."));

        socialLinkRepository.delete(socialLink);
    }

    private SocialLinkResponse mapToResponse(
            SocialLink socialLink) {

        return SocialLinkResponse.builder()
                .id(socialLink.getId())
                .platform(socialLink.getPlatform())
                .url(socialLink.getUrl())
                .displayOrder(socialLink.getDisplayOrder())
                .createdAt(socialLink.getCreatedAt())
                .updatedAt(socialLink.getUpdatedAt())
                .build();
    }
}

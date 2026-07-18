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
import com.upendra.portfolio.port.dto.request.CreateSocialLinkRequest;
import com.upendra.portfolio.port.dto.request.UpdateSocialLinkRequest;
import com.upendra.portfolio.port.dto.response.SocialLinkResponse;
import com.upendra.portfolio.port.service.SocialLinkService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/profile/social-links")
@RequiredArgsConstructor
public class SocialLinkController {

    private final SocialLinkService socialLinkService;

    @PostMapping
    public ApiResponse<SocialLinkResponse> createSocialLink(
            @RequestHeader("X-User-UUID") String userUuid,
            @Valid @RequestBody CreateSocialLinkRequest request) {

        return ApiResponse.<SocialLinkResponse>builder()
                .success(true)
                .message("Social link created successfully.")
                .data(socialLinkService.createSocialLink(
                        UUID.fromString(userUuid),
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<SocialLinkResponse>> getMySocialLinks(
            @RequestHeader("X-User-UUID") String userUuid) {

        return ApiResponse.<List<SocialLinkResponse>>builder()
                .success(true)
                .message("Social links fetched successfully.")
                .data(socialLinkService.getMySocialLinks(
                        UUID.fromString(userUuid)))
                .build();
    }

    @PutMapping("/{socialLinkId}")
    public ApiResponse<SocialLinkResponse> updateSocialLink(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long socialLinkId,
            @Valid @RequestBody UpdateSocialLinkRequest request) {

        return ApiResponse.<SocialLinkResponse>builder()
                .success(true)
                .message("Social link updated successfully.")
                .data(socialLinkService.updateSocialLink(
                        UUID.fromString(userUuid),
                        socialLinkId,
                        request))
                .build();
    }

    @DeleteMapping("/{socialLinkId}")
    public ApiResponse<Void> deleteSocialLink(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long socialLinkId) {

        socialLinkService.deleteSocialLink(
                UUID.fromString(userUuid),
                socialLinkId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Social link deleted successfully.")
                .build();
    }
}
package com.upendra.portfolio.port.service;

import java.util.List;
import java.util.UUID;

import com.upendra.portfolio.port.dto.request.CreateSocialLinkRequest;
import com.upendra.portfolio.port.dto.request.UpdateSocialLinkRequest;
import com.upendra.portfolio.port.dto.response.SocialLinkResponse;

public interface SocialLinkService {

    SocialLinkResponse createSocialLink(UUID userUuid,CreateSocialLinkRequest request);

    List<SocialLinkResponse> getMySocialLinks(
            UUID userUuid);

    SocialLinkResponse updateSocialLink(UUID userUuid,Long socialLinkId,UpdateSocialLinkRequest request);

    void deleteSocialLink(
            UUID userUuid,
            Long socialLinkId);

}


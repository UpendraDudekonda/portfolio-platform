package com.upendra.portfolio.port.dto.request;

import org.hibernate.validator.constraints.URL;

import com.upendra.portfolio.port.enums.SocialPlatform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSocialLinkRequest {

    private SocialPlatform platform;

    @URL
    private String url;

    private Integer displayOrder;

}

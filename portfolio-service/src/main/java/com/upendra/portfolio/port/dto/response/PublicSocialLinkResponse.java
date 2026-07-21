package com.upendra.portfolio.port.dto.response;

import java.time.LocalDateTime;

import com.upendra.portfolio.port.enums.SocialPlatform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicSocialLinkResponse {
	
	private SocialPlatform platform;

    private String url;

    private Integer displayOrder;

}

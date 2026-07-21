package com.upendra.portfolio.port.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicProjectImageResponse {
	
	private String imageUrl;

    private Integer displayOrder;

}

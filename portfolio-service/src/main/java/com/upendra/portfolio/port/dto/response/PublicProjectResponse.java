package com.upendra.portfolio.port.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicProjectResponse {
	
	 private String title;

	    private String shortDescription;

	    private String description;

	    private String technologies;

	    private String githubUrl;

	    private String liveUrl;

	  //  private String imageUrl;
	    
	    private List<PublicProjectImageResponse> images;

	    private Boolean featured;

	    private Integer displayOrder;


}

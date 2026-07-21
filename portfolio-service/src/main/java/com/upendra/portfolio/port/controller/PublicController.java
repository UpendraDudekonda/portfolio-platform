package com.upendra.portfolio.port.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.response.PublicPortfolioResponse;
import com.upendra.portfolio.port.service.PublicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "Public APIs",
description = "Public Portfolio APIs")
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class PublicController {

    private final PublicService publicService;

    
    @Operation(
            summary = "Get complete portfolio",
            description = "Returns profile, skills, education, projects, certifications and social links."
    )
    @ApiResponses({
    	 @io.swagger.v3.oas.annotations.responses.ApiResponse(
    		        responseCode = "200",
    		        description = "Portfolio fetched successfully"
    		    ),
    		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
    		        responseCode = "404",
    		        description = "Portfolio not found"
    		    )
    })
    @GetMapping("/public")
    public ApiResponse<PublicPortfolioResponse> getPortfolio() {

        return ApiResponse.<PublicPortfolioResponse>builder()
                .success(true)
                .message("Portfolio fetched successfully.")
                .data(publicService.getPortfolio())
                .build();
    }
}
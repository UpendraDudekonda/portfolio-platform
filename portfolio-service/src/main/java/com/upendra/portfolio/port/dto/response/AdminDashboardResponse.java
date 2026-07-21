package com.upendra.portfolio.port.dto.response;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AdminDashboardResponse {


    private Boolean profileCompleted;


    private Long skills;


    private Long projects;


    private Long experience;


    private Long education;


    private Long certifications;


    private Long socialLinks;


    private Long messages;


    private Long unreadMessages;

}
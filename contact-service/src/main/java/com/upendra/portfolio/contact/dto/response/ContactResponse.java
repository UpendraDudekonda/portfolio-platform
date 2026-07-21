package com.upendra.portfolio.contact.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactResponse {


private Long id;

private String name;

private String email;

private String subject;

private String message;

private Boolean readStatus;

private LocalDateTime createdAt;

private LocalDateTime updatedAt;


}

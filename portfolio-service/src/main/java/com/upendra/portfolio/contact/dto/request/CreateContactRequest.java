package com.upendra.portfolio.contact.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateContactRequest {


@NotBlank
private String name;


@NotBlank
@Email
private String email;


@NotBlank
private String subject;


@NotBlank
@Size(max=3000)
private String message;


}

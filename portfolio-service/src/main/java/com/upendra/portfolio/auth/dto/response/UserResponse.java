package com.upendra.portfolio.auth.dto.response;

import java.util.UUID;

import com.upendra.portfolio.auth.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private UUID uuid;

    private String firstName;

    private String lastName;

    private String email;
    
    private Role role;
}

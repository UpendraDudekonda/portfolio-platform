package com.upendra.portfolio.auth.config;

import com.upendra.portfolio.auth.AuthServiceApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    private final AuthServiceApplication authServiceApplication;

    PasswordConfig(AuthServiceApplication authServiceApplication) {
        this.authServiceApplication = authServiceApplication;
    }
	
    @Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

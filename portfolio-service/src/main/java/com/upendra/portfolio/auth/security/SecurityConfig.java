
package com.upendra.portfolio.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

            // CORS
            .cors(cors -> {})

            // JWT based authentication - CSRF disabled
            .csrf(csrf -> csrf.disable())

            // Stateless authentication
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth

                    // Allow preflight requests
                    .requestMatchers(
                            HttpMethod.OPTIONS,
                            "/**"
                    )
                    .permitAll()

                    // Public APIs
                    .requestMatchers(
                            "/api/v1/auth/**",
                            "/api/v1/profile/public",
                            "/api/v1/contact/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    )
                    .permitAll()

                    // Everything else requires JWT
                    .anyRequest()
                    .authenticated()
            )

            // JWT authentication
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}


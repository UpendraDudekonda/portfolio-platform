package com.upendra.portfolio.gateway.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.gateway.config.JwtProperties;
import com.upendra.portfolio.gateway.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;
	
	

  
	private Key getSigningKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
	    return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public Claims extractAllClaims(String token) {

	    return Jwts.parser()
	            .verifyWith((SecretKey) getSigningKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	}
	
	@Override
	public String extractEmail(String token) {
	    return extractAllClaims(token).getSubject();
	}
	
	@Override
	public UUID extractUuid(String token) {
	    return UUID.fromString(
	            extractAllClaims(token).get("uuid", String.class)
	    );
	}
	
	@Override
	public String extractRole(String token) {
	    return extractAllClaims(token).get("role", String.class);
	}
	
	private boolean isTokenExpired(String token) {
	    return extractAllClaims(token)
	            .getExpiration()
	            .before(new Date());
	}
	
	@Override
	public boolean isTokenValid(String token) {
	    return !isTokenExpired(token);
	}
	

}

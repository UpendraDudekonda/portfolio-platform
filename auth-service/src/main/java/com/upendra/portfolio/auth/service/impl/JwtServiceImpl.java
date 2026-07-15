package com.upendra.portfolio.auth.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.auth.config.JwtProperties;
import com.upendra.portfolio.auth.entity.User;
import com.upendra.portfolio.auth.service.JwtService;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

	private final JwtProperties jwtProperties;
	
	private String generateToken(User user, long expiration) {

	    return Jwts.builder()
	            .subject(user.getEmail())
	            .claim("uuid", user.getUuid().toString())
	            .claim("role", user.getRole().name())
	            .issuedAt(new Date())
	            .expiration(new Date(System.currentTimeMillis() + expiration))
	            .signWith(getSigningKey())
	            .compact();
	}
	
	@Override
	public String generateAccessToken(User user) {
	    return generateToken(user, jwtProperties.getAccessTokenExpiration());
	}
	
	@Override
	public String generateRefreshToken(User user) {
	    return generateToken(user, jwtProperties.getRefreshTokenExpiration());
	}
	
	
	private Key getSigningKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
	    return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private Claims extractAllClaims(String token) {

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
	public String extractRole(String token) {
	    return extractAllClaims(token).get("role", String.class);
	}
	
	@Override
	public boolean isTokenExpired(String token) {
	    return extractAllClaims(token)
	            .getExpiration()
	            .before(new Date());
	}
	
	@Override
	public boolean isTokenValid(String token, User user) {

	    String email = extractEmail(token);

	    return email.equals(user.getEmail())
	            && !isTokenExpired(token);
	}

	//in producton type clain also added to the token (type=ACCESS or REFRESH)
	@Override
	public boolean isRefreshTokenValid(String token, User user) {
		
		return isTokenValid(token, user);// && "REFRESH".equals(extractType(token));
	}

	
	

}

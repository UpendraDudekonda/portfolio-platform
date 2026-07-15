package com.upendra.portfolio.gateway.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.upendra.portfolio.gateway.service.JwtService;
import com.upendra.portfolio.gateway.util.CookieUtil;
import com.upendra.portfolio.gateway.util.PublicRouteValidator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter,Ordered {

	 private final JwtService jwtService;

	    private final CookieUtil cookieUtil;

	    private final PublicRouteValidator publicRouteValidator;
	    
	    @Override
		public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
			
	    	System.err.println("===== JWT FILTER EXECUTED =====");
	    	String path = exchange.getRequest()
	    	        .getURI()
	    	        .getPath();

	    	if (publicRouteValidator.isPublic(path)) {
	    	    return chain.filter(exchange);
	    	}
	    	
	    	// protected enpoints
	    	String accessToken =
	    	        cookieUtil.getAccessToken(exchange.getRequest());
	    	
	    	System.err.println("Access Token: " + accessToken);
	    	
	    	if (accessToken == null || accessToken.isBlank()) {
	    	    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	    	    return exchange.getResponse().setComplete();
	    	}
	    	
	    	//JwtService verifies the signature and expiration
	    	try {
	    	    if (!jwtService.isTokenValid(accessToken)) {
	    	        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	    	        return exchange.getResponse().setComplete();
	    	    }
	    	} catch (Exception ex) {
	    	    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	    	    return exchange.getResponse().setComplete();
	    	}
	    	
	    	UUID uuid = jwtService.extractUuid(accessToken);
	    	String email = jwtService.extractEmail(accessToken);
	    	String role = jwtService.extractRole(accessToken);
	    	
	    	System.err.println(uuid);
	    	System.err.println(email);
	    	System.err.println(role);
	    	
	    	System.err.println("Forwarding request with headers...");
	    	
	    	ServerHttpRequest mutatedRequest = exchange.getRequest()
	    		    .mutate()
	    		    .header("X-User-UUID", uuid.toString())
	    		    .header("X-User-Email", email)
	    		    .header("X-User-Role", role)
	    		    .build();

	    		return chain.filter(
	    		    exchange.mutate()
	    		            .request(mutatedRequest)
	    		            .build()
	    		);
		}
	    
	    @Override
	    public int getOrder() {
	        return -1;
	    }

		
}

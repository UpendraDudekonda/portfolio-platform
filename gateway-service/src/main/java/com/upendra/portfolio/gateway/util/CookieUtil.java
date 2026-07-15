package com.upendra.portfolio.gateway.util;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
	

	    public String getAccessToken(ServerHttpRequest request) {

	        HttpCookie cookie = request.getCookies()
	                .getFirst("access_token");

	        return cookie != null ? cookie.getValue() : null;
	    }
	

}

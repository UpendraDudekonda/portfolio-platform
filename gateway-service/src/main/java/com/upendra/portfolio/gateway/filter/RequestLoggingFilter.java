package com.upendra.portfolio.gateway.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        filterChain.doFilter(request, response);

        long end = System.currentTimeMillis();

        System.out.println(
                request.getMethod() +
                " " +
                request.getRequestURI() +
                " -> " +
                response.getStatus() +
                " (" +
                (end - start) +
                " ms)"
        );
    }

}

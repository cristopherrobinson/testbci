package com.example.bci.exception;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        CustomErrorResponse errorResponse = new CustomErrorResponse(response.getStatus(), "Acceso denegado. Autenticación requerida.");
        String jsonErrorResponse = new ObjectMapper().writeValueAsString(errorResponse);
        response.getWriter().write(jsonErrorResponse);
    }
}

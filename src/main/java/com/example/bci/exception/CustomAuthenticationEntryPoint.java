package com.example.bci.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    /**
    * Maneja los casos en los que un usuario intenta acceder a un recurso protegido sin estar autenticado.
    * Establece una respuesta HTTP con el estado de error 401 (No Autorizado) y envía un mensaje de error
    * en formato JSON. Además, registra los detalles de la solicitud fallida, incluyendo el método HTTP,
    * la URL del recurso solicitado y el mensaje de la excepción de autenticación.
    *
    * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
    * @param response Objeto HttpServletResponse donde se establece la respuesta de error.
    * @param authException La excepción de autenticación que se ha lanzado indicando el fallo.
    * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
    */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        CustomErrorResponse errorResponse = new CustomErrorResponse(response.getStatus(), "Acceso denegado.");
        
        String requestURL = request.getRequestURL().toString();
        String requestMethod = request.getMethod();

        logger.info("Acceso denegado para el request. Método: {}, URL: {}, Error: {}", requestMethod, requestURL,
                authException.getMessage());

        String jsonErrorResponse = new ObjectMapper().writeValueAsString(errorResponse);

        response.getWriter().write(jsonErrorResponse);
    }
}

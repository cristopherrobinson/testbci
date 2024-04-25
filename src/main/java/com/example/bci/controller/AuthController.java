package com.example.bci.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.bci.dto.AuthRequestDto;
import com.example.bci.dto.AuthResponseDto;
import com.example.bci.exception.CustomException;
import com.example.bci.service.UsuarioService;
import com.example.bci.util.TokenGenerator;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
    * Este método maneja la solicitud de inicio de sesión de los usuarios. Autentica al usuario utilizando las credenciales proporcionadas y, si son válidas, genera un token JWT.
    * 
    * @param request Contiene las credenciales del usuario (email de usuario y contraseña).
    * @return Retorna un objeto ResponseEntity que contiene el token JWT si la autenticación es exitosa.
    * @throws CustomException Si ocurre un error durante el proceso de autenticación.
    * @Operation(summary = "Genera token de acceso al usuario correctamente autenticado")
    */
    @Operation(summary = "Genera token de acceso al usuario correctamente autenticado")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) throws CustomException {
        logger.info("Login de usuario: ".concat(request.getUsername()));
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = TokenGenerator.generateToken(request.getUsername());
        usuarioService.lastLogin(request, jwt);
        return ResponseEntity.ok(new AuthResponseDto(jwt));
    }
    
}

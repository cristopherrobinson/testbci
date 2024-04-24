package com.example.bci.controller;

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

import com.example.bci.dto.AuthRequest;
import com.example.bci.dto.AuthResponse;
import com.example.bci.exception.CustomException;
import com.example.bci.service.UsuarioService;
import com.example.bci.util.TokenGenerator;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) throws CustomException {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = TokenGenerator.generateToken(request.getUsername());
        usuarioService.lastLogin(request, jwt);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
    
}

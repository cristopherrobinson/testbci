package com.example.bci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bci.entities.CustomErrorResponse;
import com.example.bci.entities.Usuario;
import com.example.bci.exception.CustomException;
import com.example.bci.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Registra un nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Usuario no creado")
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
            return new ResponseEntity<>(usuarioRegistrado, HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(new CustomErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}

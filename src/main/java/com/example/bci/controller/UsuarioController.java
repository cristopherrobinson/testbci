package com.example.bci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bci.entities.Usuario;
import com.example.bci.exception.CustomErrorResponse;
import com.example.bci.exception.CustomException;
import com.example.bci.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);


    /**
     * Endpoint de la API para registrar un nuevo usuario.
     * 
     * Este método expone un endpoint HTTP POST que permite a los clientes de la API
     * registrar un nuevo usuario. Utiliza anotaciones para definir la documentación de la API
     * y las respuestas esperadas. En caso de éxito, devuelve un estado HTTP 201 junto con el usuario
     * registrado. Si ocurre un error durante el registro, como datos de entrada inválidos, devuelve
     * un estado HTTP 400 con un mensaje de error.
     * 
     * @param usuario El objeto 'Usuario' que se recibe en el cuerpo de la solicitud POST, representando
     *                los datos del usuario a registrar.
     * @return ResponseEntity<?> Retorna una respuesta con el usuario registrado y un estado HTTP 201 en caso
     *         de éxito, o un mensaje de error con un estado HTTP 400 si el registro falla.
     */
    @Operation(summary = "Registra un nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Usuario no creado")
    @PostMapping("/agregar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
            logger.info("Usuario registrado correctamente: ".concat(usuario.getEmail()));
            return new ResponseEntity<>(usuarioRegistrado, HttpStatus.CREATED);
        } catch (CustomException e) {
            logger.error("Ha ocurrido un error al registrar el usuario: ".concat(e.getMessage()));
            return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    

}

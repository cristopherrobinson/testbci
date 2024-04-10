package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.bci.BCIApplication;
import com.example.bci.controller.UsuarioController;
import com.example.bci.entities.Usuario;
import com.example.bci.exception.CustomException;

import com.example.bci.validators.PasswordPolicy;
import com.example.bci.service.UsuarioServiceImpl;

@SpringBootTest(classes = BCIApplication.class)
public class UsuarioTest {


    @Mock
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private PasswordPolicy passwordPolicy;


    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuarioUsuarioValidoDevuelveUsuario() throws CustomException {
        
        Usuario usuario = new Usuario();
        usuario.setName("Cristopher");
        usuario.setEmail("cristopher.canoles@sociuschile.cl");
        usuario.setPassword("Socius.777");

        when(usuarioService.registrarUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.registrarUsuario(usuario);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(usuario, response.getBody());
    }


    @Test
    void validatePolicyPasswordSuccess() throws CustomException {

        Usuario usuario = new Usuario();
        usuario.setPassword("Socius.777");
        assertEquals(true, passwordPolicy.validatePassword(usuario));
    
    }

    @Test
    void validatePolicyPasswordError() throws CustomException {

        Usuario usuario = new Usuario();
        usuario.setPassword("socius777");
        assertEquals(false, passwordPolicy.validatePassword(usuario));
    
    }


}

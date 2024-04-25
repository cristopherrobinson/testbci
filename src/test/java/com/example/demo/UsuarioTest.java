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
import com.example.bci.entity.UsuarioEntity;
import com.example.bci.exception.CustomException;
import com.example.bci.service.UsuarioServiceImpl;
import com.example.bci.util.PasswordPolicy;
import com.intuit.karate.junit5.Karate;

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
        
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setName("Cristopher");
        usuario.setEmail("cristopher.canoles@sociuschile.cl");
        usuario.setPassword("Socius.777");

        when(usuarioService.registrarUsuario(any(UsuarioEntity.class))).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.registrarUsuario(usuario);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(usuario, response.getBody());
    }


    @Test
    void validatePolicyPasswordSuccess() throws CustomException {

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setPassword("Socius777");
        assertEquals(true, passwordPolicy.validatePassword(usuario));
    
    }

    @Test
    void validatePolicyPasswordError() throws CustomException {

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setPassword("socius777");
        assertEquals(false, passwordPolicy.validatePassword(usuario));
    
    }

    @Karate.Test
    Karate testLogin() {
        return Karate.run("authentication").relativeTo(getClass());
    }

    @Karate.Test
    Karate testUsuarios() {
        return Karate.run("password").relativeTo(getClass());
    }


}

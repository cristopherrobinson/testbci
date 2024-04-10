package com.example.bci.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bci.entities.Usuario;
import com.example.bci.exception.CustomException;

import com.example.bci.repository.UsuarioRepository;
import com.example.bci.util.PasswordProperties;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordProperties globalProperties;


    @Override
    public Usuario registrarUsuario(Usuario usuario) throws CustomException {
        
        String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern patternEmail = Pattern.compile(regexEmail);
        Matcher matcherEmail = patternEmail.matcher(usuario.getEmail());

        if (!matcherEmail.matches()) {
            throw new CustomException("El formato del email no es válido");
        }

        String regexPassword = globalProperties.getPasswordPattern();
        Pattern patternPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternPassword.matcher(usuario.getPassword());

        if (!matcherPassword.matches()) {
            throw new CustomException(globalProperties.getPasswordMessage());
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new CustomException("El email ya registrado");
        }

        usuario.setLastLogin(LocalDateTime.now());
        usuario.setIsactive(true);
        usuario.setToken(UUID.randomUUID().toString());

        return usuarioRepository.save(usuario);
    }

}

package com.example.bci.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bci.entities.Usuario;
import com.example.bci.exception.CustomException;

import com.example.bci.repository.UsuarioRepository;
import com.example.bci.validators.EmailPolicy;
import com.example.bci.validators.PasswordPolicy;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordPolicy passwordPolicy;

    @Autowired
    private EmailPolicy emailPolicy;


    @Override
    public Usuario registrarUsuario(Usuario usuario) throws CustomException {
        
        if (!emailPolicy.validateEmail(usuario)) {
            throw new CustomException(emailPolicy.getEmailMessage());
        }

        if (!passwordPolicy.validatePassword(usuario)) {
            throw new CustomException(passwordPolicy.getPasswordMessage());
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new CustomException(emailPolicy.getEmailExistMessage());
        }

        usuario.setLastLogin(LocalDateTime.now());
        usuario.setIsactive(true);
        usuario.setToken(UUID.randomUUID().toString());

        return usuarioRepository.save(usuario);
    }

}

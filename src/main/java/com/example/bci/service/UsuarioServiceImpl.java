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


    /**
    * Registra un nuevo usuario en el sistema después de realizar una serie de validaciones.
    *
    * Este método se asegura de que el correo electrónico y la contraseña del usuario cumplan con las políticas
    * establecidas y que el correo electrónico no esté ya en uso. Si todas las validaciones son exitosas,
    * se establecen algunos valores predeterminados para el nuevo usuario, como la fecha y hora del último inicio de sesión,
    * el estado activo y un token único. Finalmente, el usuario se guarda en el repositorio.
    *
    * @param usuario El objeto 'Usuario' que contiene la información del usuario a registrar.
    * @return Usuario El usuario registrado con todos sus datos, incluidos los valores predeterminados establecidos durante el registro.
    * @throws CustomException Si alguna de las validaciones falla, se lanza una excepción con un mensaje específico.
    */
    @Override
    public Usuario registrarUsuario(Usuario usuario) throws CustomException {
        
        if (!emailPolicy.validateEmail(usuario)) {
            throw new CustomException(emailPolicy.getEmailMessage().concat(": ").concat(usuario.getEmail()));
        }

        if (!passwordPolicy.validatePassword(usuario)) {
            throw new CustomException(passwordPolicy.getPasswordMessage().concat(": ").concat(usuario.getEmail().concat(" pass:")+usuario.getPassword()));
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new CustomException(emailPolicy.getEmailExistMessage().concat(": ").concat(usuario.getEmail()));
        }

        usuario.setLastLogin(LocalDateTime.now());
        usuario.setIsactive(true);
        usuario.setToken(UUID.randomUUID().toString());

        return usuarioRepository.save(usuario);
    }

}

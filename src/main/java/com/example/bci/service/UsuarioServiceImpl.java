package com.example.bci.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bci.dto.AuthRequestDto;
import com.example.bci.dto.UserRequestDto;
import com.example.bci.entity.UsuarioEntity;
import com.example.bci.exception.CustomException;

import com.example.bci.repository.UsuarioRepository;
import com.example.bci.util.EmailPolicy;
import com.example.bci.util.PasswordPolicy;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordPolicy passwordPolicy;

    @Autowired
    private EmailPolicy emailPolicy;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);


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
    public UsuarioEntity registrarUsuario(UsuarioEntity usuario) throws CustomException {
        
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
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }


    /**
    * Actualiza el último login de un usuario y guarda el token JWT.
    * 
    * Este método busca al usuario por email usando el username proporcionado en el AuthRequest.
    * Si el usuario no se encuentra, lanza una excepción CustomException.
    * Si el usuario existe, actualiza su token JWT y la fecha de último acceso,
    * luego guarda los cambios en la base de datos.
    *
    * @param authRequest contiene información de la solicitud de autenticación, incluyendo el username.
    * @param jwt el token JWT generado que será asociado con el usuario.
    * @return Usuario actualizado con la nueva información de token y último acceso.
    * @throws CustomException si el usuario no se encuentra en la base de datos.
    */
    @Override
    public UsuarioEntity lastLogin(AuthRequestDto authRequest, String jwt) throws CustomException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(authRequest.getUsername())
        .orElseThrow(() -> new CustomException("Ultimo login actualizado para el usuario con email: ".concat(authRequest.getUsername())));
        usuario.setToken(jwt);
        usuario.setLastLogin(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }


    /**
    * Busca un usuario en la base de datos por su dirección de correo electrónico.
    * 
    * Este método intenta encontrar un usuario existente en la base de datos utilizando el email proporcionado
    * en el objeto Usuario. Si no se encuentra ningún usuario con ese email, se lanza una CustomException.
    *
    * @param usuario objeto Usuario que contiene el email usado para la búsqueda.
    * @return Usuario encontrado con el email proporcionado.
    * @throws CustomException si no se encuentra ningún usuario con el email proporcionado.
    */
    @Override
    public UsuarioEntity buscarUsuario(UserRequestDto userRequest) throws CustomException {
        return usuarioRepository.findByEmail(userRequest.getEmail()).orElseThrow(() -> new CustomException("Usuario no encontrado con email: ".concat(userRequest.getEmail())));
    }





}

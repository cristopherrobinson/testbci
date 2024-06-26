package com.example.bci.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.bci.entity.UsuarioEntity;

import lombok.Getter;

@Component
@Getter
public class PasswordPolicy {

    @Value("${password.pattern}")
    private String passwordPattern;

    @Value("${password.message}")
    private String passwordMessage;

    /**
    * Valida si la contraseña del usuario cumple con el patrón establecido.
    *
    * Este método utiliza expresiones regulares para verificar si la contraseña
    * del usuario cumple con un patrón específico definido por la variable
    * 'passwordPattern'. El patrón puede incluir reglas como la longitud mínima y
    * máxima, y la presencia de caracteres especiales, dígitos, letras mayúsculas
    * y minúsculas.
    *
    * @param usuario El objeto 'Usuario' que contiene la contraseña a validar.
    * @return boolean Retorna 'true' si la contraseña cumple con el patrón, de lo
    * contrario retorna 'false'.
    */
    public boolean validatePassword(UsuarioEntity usuario) {

        Pattern patternPassword = Pattern.compile(passwordPattern);
        Matcher matcherPassword = patternPassword.matcher(usuario.getPassword());

        return matcherPassword.matches();
    }

}

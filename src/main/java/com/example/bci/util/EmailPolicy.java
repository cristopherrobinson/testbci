package com.example.bci.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.bci.entity.Usuario;

import lombok.Getter;

@Component
@Getter
public class EmailPolicy {

    @Value("${email.pattern}")
    private String emailPattern;

    @Value("${email.message}")
    private String emailMessage;

    @Value("${email.message.exist}")
    private String emailExistMessage;

    /**
     * Valida si el correo electrónico del usuario cumple con el patrón establecido.
     *
     * Este método se encarga de verificar que el correo electrónico proporcionado por el usuario
     * se ajuste a un formato específico definido por la expresión regular en 'emailPattern'. La expresión
     * regular puede configurarse para validar formatos comunes de correo electrónico, asegurando que incluyan
     * caracteres permitidos antes y después del símbolo '@', seguido de un dominio y una extensión.
     *
     * @param usuario El objeto 'Usuario' que contiene el correo electrónico a validar.
     * @return boolean Retorna 'true' si el correo electrónico cumple con el patrón establecido, de lo
     * contrario retorna 'false'.
     */
    public boolean validateEmail(Usuario usuario) {

        Pattern patternPassword = Pattern.compile(emailPattern);
        Matcher matcherPassword = patternPassword.matcher(usuario.getEmail());

        return matcherPassword.matches();
    }
}

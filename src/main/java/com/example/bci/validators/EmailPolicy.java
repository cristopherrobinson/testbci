package com.example.bci.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.bci.entities.Usuario;

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
    
    public boolean validateEmail(Usuario usuario){

        Pattern patternPassword = Pattern.compile(emailPattern);
        Matcher matcherPassword = patternPassword.matcher(usuario.getEmail());

        return matcherPassword.matches();
    }
}

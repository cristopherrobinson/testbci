package com.example.bci.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.bci.entities.Usuario;

import lombok.Getter;

@Component
@Getter
public class PasswordProperties {
    
    @Value("${password.pattern}")
    private String passwordPattern;
    
    @Value("${password.message}")
    private String passwordMessage;

    public boolean validatePassword(Usuario usuario){

        Pattern patternPassword = Pattern.compile(passwordPattern);
        Matcher matcherPassword = patternPassword.matcher(usuario.getPassword());

        return matcherPassword.matches();
    }



}

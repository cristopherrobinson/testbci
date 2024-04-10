package com.example.bci.service;


import com.example.bci.entities.Usuario;
import com.example.bci.exception.CustomException;



public interface UsuarioService {

    Usuario registrarUsuario(Usuario usuario) throws CustomException;

}

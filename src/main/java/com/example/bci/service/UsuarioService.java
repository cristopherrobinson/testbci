package com.example.bci.service;


import com.example.bci.dto.AuthRequest;
import com.example.bci.dto.UserRequest;
import com.example.bci.entity.Usuario;
import com.example.bci.exception.CustomException;



public interface UsuarioService {

    Usuario registrarUsuario(Usuario usuario) throws CustomException;

    Usuario lastLogin(AuthRequest authRequest, String jwt) throws CustomException;

    Usuario buscarUsuario(UserRequest userRequest) throws CustomException;

}

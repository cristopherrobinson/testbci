package com.example.bci.service;


import com.example.bci.dto.AuthRequestDto;
import com.example.bci.dto.UserRequestDto;
import com.example.bci.entity.UsuarioEntity;
import com.example.bci.exception.CustomException;



public interface UsuarioService {

    UsuarioEntity registrarUsuario(UsuarioEntity usuario) throws CustomException;

    UsuarioEntity lastLogin(AuthRequestDto authRequest, String jwt) throws CustomException;

    UsuarioEntity buscarUsuario(UserRequestDto userRequest) throws CustomException;

}

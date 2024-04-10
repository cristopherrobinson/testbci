package com.example.bci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bci.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}

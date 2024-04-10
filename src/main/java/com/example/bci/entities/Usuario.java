package com.example.bci.entities;

import java.time.LocalDateTime;
import java.util.List;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private List<Telefono> phones;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime modified;

    private LocalDateTime lastLogin;

    private String token;
    private boolean isactive;

    
}

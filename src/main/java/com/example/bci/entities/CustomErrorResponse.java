package com.example.bci.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResponse {

    private final String mensaje;

    public CustomErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }

}

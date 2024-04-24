package com.example.bci.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResponse {

    private final int status;
    private final String description;

    public CustomErrorResponse(int status, String description) {
        this.status = status;
        this.description = description;
    }

}

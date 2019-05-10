package ru.ifmo.web.service;

import lombok.Getter;

public class InvalidParameterException extends RuntimeException{
    @Getter
    private final String paramName;

    public InvalidParameterException(String paramName) {
        this.paramName = paramName;
    }
}

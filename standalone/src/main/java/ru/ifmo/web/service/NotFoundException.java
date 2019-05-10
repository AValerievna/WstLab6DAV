package ru.ifmo.web.service;

import lombok.Getter;

public class NotFoundException extends RuntimeException {
    @Getter
    private final Long neededId;

    public NotFoundException(Long neededId) {
        this.neededId = neededId;
    }
}

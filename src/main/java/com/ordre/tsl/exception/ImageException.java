package com.ordre.tsl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageException extends Exception {
    public ImageException(String message) {
        super(message);
    }
}

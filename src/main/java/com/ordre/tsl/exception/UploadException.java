package com.ordre.tsl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UploadException extends IOException {
    public UploadException(String message, IOException e) {
        super(message, e);
    }
}

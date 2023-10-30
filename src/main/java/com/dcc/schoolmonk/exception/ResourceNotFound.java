package com.dcc.schoolmonk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotFound extends RuntimeException{
 public ResourceNotFound(String message) {
        super(message);
    }
    
}

package com.ng0cth1nh.management.exceptionHandling;

import org.springframework.http.ResponseEntity;


public class ResponseEntityBuilder {
    public static ResponseEntity<Object> build(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}

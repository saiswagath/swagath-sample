package com.demo.intuit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ObjectExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ObjectErrorResponse> handleException(CraftDemoServicesException craftDemoServicesException){
        ObjectErrorResponse errorResponse = new ObjectErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(craftDemoServicesException.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ObjectErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ObjectErrorResponse> handleException(Exception exception){
        ObjectErrorResponse errorResponse = new ObjectErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ObjectErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}

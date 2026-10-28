package com.workintech.twitterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(TwitterException.class)
   public ResponseEntity<TwitterErrorResponse> handleTwitterException(TwitterException exception){
    TwitterErrorResponse errorResponse = new TwitterErrorResponse(
            exception.getHttpStatus().value(),
            exception.getMessage(),
            System.currentTimeMillis()
    );
    return new ResponseEntity<>(
            errorResponse,
            exception.getHttpStatus()
    );
}
@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TwitterErrorResponse> handleValidationException(MethodArgumentNotValidException exception){
    String message = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

    TwitterErrorResponse errorResponse = new TwitterErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            message,
            System.currentTimeMillis()
    );
    return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
}

}

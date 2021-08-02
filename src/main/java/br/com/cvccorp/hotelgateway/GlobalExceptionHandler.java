package br.com.cvccorp.hotelgateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<List<String>> handleException(ServiceException ex) {
        return ResponseEntity.badRequest().body(Collections.singletonList(ex.getMessage()));
    }

}

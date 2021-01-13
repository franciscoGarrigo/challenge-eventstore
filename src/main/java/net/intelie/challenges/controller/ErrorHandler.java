package net.intelie.challenges.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> unknownError(Exception e) {
        log.error("An error has occurred, message: {}", e.getMessage(), e);
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorMap(e.getMessage()));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> invalidRequestException(IllegalArgumentException e) {
        log.warn("An error has occurred, message: {}", e.getMessage(), e);
        return status(HttpStatus.BAD_REQUEST).body(createErrorMap(e.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        
        StringBuilder builder = new StringBuilder();      
        
        fieldErrors.stream().forEach(f -> builder.append(f.getDefaultMessage()).append(". "));
        
        return status(HttpStatus.BAD_REQUEST).body(createErrorMap(builder.toString()));
        
    }
    
    /**
     * Create a error map with all possible errors in the application to avoid to show a long stack trace for the requester 
     *    
     * @param message
     * @return
     */
    private static Map<String,Object> createErrorMap(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", message);
        
        return map;
    }
}

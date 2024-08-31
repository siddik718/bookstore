package co.library.users.controller;

import co.library.users.exception.DataDuplicateException;
import co.library.users.exception.NoDataFoundException;
import co.library.users.exception.NotAllowedException;
import co.library.users.model.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ApiResponse apiResponse = new ApiResponse();

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return apiResponse.errorResponse(400,"REQUEST_BODY_INVALID_FORMAT", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        HashMap<Object, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        return apiResponse.errorResponse(404,"USERNAME_NOT_FOUND", ex.getMessage(), request);
    }

    @ExceptionHandler(DataDuplicateException.class)
    public ResponseEntity<?> handleDataDuplicateException(DataDuplicateException ex, HttpServletRequest request) {
        return apiResponse.errorResponse(409,"DATA_DUPLICATE", ex.getMessage(), request);
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<?> handleNotAllowedException(NotAllowedException ex, HttpServletRequest request) {
        return apiResponse.errorResponse(400,"NOT_ALLOWED", ex.getMessage(), request);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> handleNoDataFoundException(NoDataFoundException ex, HttpServletRequest request) {
        return apiResponse.errorResponse(404,"NOT_FOUND", ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        return apiResponse.errorResponse(500,"INTERNAL_ERROR", ex.getMessage(), request);
    }
}

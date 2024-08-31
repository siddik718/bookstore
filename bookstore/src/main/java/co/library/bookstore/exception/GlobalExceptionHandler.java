package co.library.bookstore.exception;

import co.library.bookstore.model.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        System.out.println(ex.getMessage());
        return apiResponse.failed(400,ex.getMessage(),"REQUEST_BODY_NULL_OR_UNREADABLE_ERROR",request);
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
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        return apiResponse.failed(404,ex.getMessage(),"NO_RESOURCE_FOUND_ERROR",request);
    }
    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<?> handleInvalidDateException(InvalidDateException ex, HttpServletRequest request) {
        return apiResponse.failed(400,ex.getMessage(),"INVALID_DATE_FORMAT_ERROR",request);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException ex, HttpServletRequest request) {
        return apiResponse.failed(400,ex.getMessage(),"ALREADY_EXIST_ERROR",request);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        return apiResponse.failed(400,ex.getMessage(),"RUNTIME_ERROR",request);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        return apiResponse.failed(500,ex.getMessage(),"INTERNAL_SERVER_ERROR",request);
    }
}

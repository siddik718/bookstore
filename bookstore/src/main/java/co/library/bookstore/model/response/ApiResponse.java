package co.library.bookstore.model.response;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ApiResponse {
    public ResponseEntity<?> succeed(Object data, int status, String message, HttpServletRequest request){
        Response<?> body = new Response<>(
                new Date(),
                status,
                "",
                request.getServletPath(),
                message,
                data
        );
        return ResponseEntity.status(status).body(body);
    }
    public ResponseEntity<?> failed(int status, String message,String error, HttpServletRequest request){
        Response<?> body = new Response<>(
                new Date(),
                status,
                error,
                request.getServletPath(),
                message,
                null
        );
        return ResponseEntity.status(status).body(body);
    }
}

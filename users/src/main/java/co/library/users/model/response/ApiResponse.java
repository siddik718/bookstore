package co.library.users.model.response;

import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public class ApiResponse {
    public ResponseEntity<?> successResponse(Object data, int status, String message, HttpServletRequest request) {
        Response<?> res = new Response<>(
                new Date().getTime(),
                status,
                "",
                request.getServletPath(),
                message,
                data
        );
        return ResponseEntity.status(status).body(res);
    }
    public ResponseEntity<?> errorResponse(int status,String error, String message, HttpServletRequest request) {
        Response<?> res = new Response<>(
                new Date().getTime(),
                status,
                error,
                request.getServletPath(),
                message,
                null
        );
        return ResponseEntity.status(status).body(res);
    }
}



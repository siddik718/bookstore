package co.library.bookstore.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response <T> {
    private Date timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
    private T result;
}

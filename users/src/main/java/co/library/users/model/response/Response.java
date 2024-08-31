package co.library.users.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response <T> {
    private long timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
    private T data;
}

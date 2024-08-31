package co.library.bookstore.model.UserClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String email;
    private String name;
    private String address;
    private Long bookBought;
    private Long bookSold;
    private Integer userType;
}
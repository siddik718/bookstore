package co.library.bookstore.filter;

import co.library.bookstore.model.UserClient.UserResponse;
import co.library.bookstore.service.UserClient;
import org.springframework.beans.factory.annotation.Autowired;

public class FilterUtils {

    private final UserClient userClient;

    public FilterUtils(UserClient userClient) {
        this.userClient = userClient;
    }

    UserResponse verifyToken(String token) {
        return this.userClient.user(token);
    }
}

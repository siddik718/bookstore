package co.library.bookstore.service;

import co.library.bookstore.model.UserClient.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://localhost:8081/api-dev/v1-client", name = "UserClient")
public interface UserClient {
    @GetMapping("/verify-user")
    UserResponse user(@RequestHeader("Authorization") String token);
}

package co.library.users.controller;

import co.library.users.exception.NoDataFoundException;
import co.library.users.exception.NotAllowedException;
import co.library.users.model.entity.UserEntity;
import co.library.users.model.response.UserResponse;
import co.library.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${library.client.base.api}")
public class UserClientController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify-user")
    public UserResponse user(@AuthenticationPrincipal UserEntity userEntity, HttpServletRequest request)  throws NoDataFoundException, NotAllowedException {
        return this.userService.user(userEntity.getUserId());
    }

}


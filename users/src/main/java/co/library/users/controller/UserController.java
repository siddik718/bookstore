package co.library.users.controller;

import co.library.users.exception.NoDataFoundException;
import co.library.users.exception.NotAllowedException;
import co.library.users.model.entity.UserEntity;
import co.library.users.model.request.Update;
import co.library.users.model.response.ApiResponse;
import co.library.users.model.response.UserResponse;
import co.library.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${library.user.base.api}")
public class UserController {

    @Autowired
    private UserService userService;

    private final ApiResponse apiResponse = new ApiResponse();

    @GetMapping
    public ResponseEntity<?> user(@AuthenticationPrincipal UserEntity userEntity, HttpServletRequest request)  throws NoDataFoundException, NotAllowedException {
        UserResponse res = this.userService.user(userEntity.getUserId());
        return apiResponse.successResponse(res, 200, "User Data Returned Successfully",request);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserEntity userEntity, @RequestBody Update body, HttpServletRequest request)  throws NoDataFoundException, NotAllowedException{
        UserResponse res = this.userService.updateUser(userEntity.getUserId(), body);
        return apiResponse.successResponse(res, 200, "User Data Updated Successfully",request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserEntity userEntity,HttpServletRequest request)  throws NoDataFoundException, NotAllowedException{
        this.userService.logout(userEntity.getUserId());;
        return apiResponse.successResponse("Logout Success", 200, "User Data Updated Successfully",request);
    }

    @DeleteMapping
    public ResponseEntity<?> remove(@AuthenticationPrincipal UserEntity userEntity, HttpServletRequest request)  throws NoDataFoundException, NotAllowedException {
        this.userService.delete(userEntity.getUserId());
        return apiResponse.successResponse("Remove Success", 200, "User Data Deleted Successfully",request);
    }

}

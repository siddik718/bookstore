package co.library.users.controller;

import co.library.users.exception.NoDataFoundException;
import co.library.users.exception.NotAllowedException;
import co.library.users.model.entity.UserEntity;
import co.library.users.model.response.ApiResponse;
import co.library.users.model.response.UserResponse;
import co.library.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${library.admin.base.api}")
public class AdminController {

    @Autowired
    private UserService userService;

    private final ApiResponse apiResponse = new ApiResponse();

    @GetMapping("/users")
    public ResponseEntity<?> users(@AuthenticationPrincipal UserEntity userEntity, HttpServletRequest request) {
        Integer type = userEntity.getUserType();
        if (type == null || type != 2) {
            return new ResponseEntity<>("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        List<UserResponse> res = this.userService.users();
        return apiResponse.successResponse(res,200,"Data Returned",request);
    }

    @PutMapping("users/update-role/{id}")
    public ResponseEntity<?> updateUserRole(@AuthenticationPrincipal UserEntity userEntity, @PathVariable(value = "id") long Id, @RequestParam (name="role", required = false) String role,
            HttpServletRequest request) throws NoDataFoundException, NotAllowedException {
        Integer type = userEntity.getUserType();
        if (type == null || type != 2) {
            return new ResponseEntity<>("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        UserResponse response = this.userService.updateUserRole(Id,role);
        return apiResponse.successResponse(response, 200, "Data Updated", request);
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteAll(@AuthenticationPrincipal UserEntity userEntity, HttpServletRequest request) {
        Integer type = userEntity.getUserType();
        if (type == null || type != 2) {
            return new ResponseEntity<>("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        this.userService.delete();
        return apiResponse.successResponse("DATA DELETED", 200, "Data DELETED", request);
    }
}

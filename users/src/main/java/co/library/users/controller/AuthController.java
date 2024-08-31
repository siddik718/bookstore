package co.library.users.controller;


import co.library.users.exception.DataDuplicateException;
import co.library.users.model.request.Login;
import co.library.users.model.request.SignUp;
import co.library.users.model.response.ApiResponse;
import co.library.users.model.response.UserResponse;
import co.library.users.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${library.auth.base.api}")
@Validated
public class AuthController {

    @Autowired
    private AuthService service;

    private final ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUp body, HttpServletRequest request) throws DataDuplicateException {
        UserResponse res = this.service.signup(body);
        return apiResponse.successResponse(res,201,"User Data Saved Successfully",request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Login body, HttpServletRequest request) throws Exception {
        String res = this.service.login(body);
        return apiResponse.successResponse(res,200,"User Found Successfully",request);
    }

}

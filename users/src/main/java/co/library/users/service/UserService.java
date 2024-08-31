package co.library.users.service;

import co.library.users.exception.NoDataFoundException;
import co.library.users.exception.NotAllowedException;
import co.library.users.model.request.Update;
import co.library.users.model.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserResponse> users();
    UserResponse user(Long Id) throws NoDataFoundException, NotAllowedException;
    UserResponse updateUserRole(Long Id, String role)throws NoDataFoundException, NotAllowedException;
    UserResponse updateUser(Long Id,Update body) throws NoDataFoundException, NotAllowedException;
    void logout(Long id) throws NoDataFoundException, NotAllowedException;
    void delete(long id)throws NoDataFoundException, NotAllowedException;
    void delete();
}

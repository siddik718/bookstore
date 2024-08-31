package co.library.users.service;

import co.library.users.exception.DataDuplicateException;
import co.library.users.model.request.Login;
import co.library.users.model.request.SignUp;
import co.library.users.model.response.UserResponse;

public interface AuthService {
    UserResponse signup(SignUp body) throws DataDuplicateException;
    String login(Login body) throws Exception;
}

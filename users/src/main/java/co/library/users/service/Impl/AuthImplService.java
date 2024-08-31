package co.library.users.service.Impl;

import co.library.users.exception.DataDuplicateException;
import co.library.users.exception.NotAllowedException;
import co.library.users.model.entity.UserEntity;
import co.library.users.model.request.Login;
import co.library.users.model.request.SignUp;
import co.library.users.model.response.UserResponse;
import co.library.users.repository.UserRepository;
import co.library.users.service.AuthService;
import co.library.users.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthImplService implements AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Override
    public UserResponse signup(SignUp body) throws DataDuplicateException {
        UserEntity user = this.repository.findByEmail(body.getEmail());
        if (user != null) {
            throw new DataDuplicateException("User already Exist with this email");
        }
        body.setPassword(encoder.encode(body.getPassword()));
        UserEntity userToSave = this.mapper.map(body, UserEntity.class);
        userToSave.setUserType(0);
        return this.mapper.map(this.repository.save(userToSave), UserResponse.class);
    }
    @Override
    public String login(Login body) throws Exception {

        Authentication authentication = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.getEmail(),body.getPassword())
        );
        if (authentication.isAuthenticated()) {

            UserEntity user = this.repository.findByEmail(body.getEmail());

            if (user.getIsLoggedId() == 1) {
                throw new NotAllowedException("User already Logged In with this email");
            }
            user.setIsLoggedId(1);
            this.repository.save(user);
            return this.jwtService.generateToken(user);

        }
        return null;
    }
}

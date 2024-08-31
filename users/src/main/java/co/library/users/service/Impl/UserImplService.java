package co.library.users.service.Impl;

import co.library.users.exception.NoDataFoundException;
import co.library.users.exception.NotAllowedException;
import co.library.users.model.entity.UserEntity;
import co.library.users.model.request.Update;
import co.library.users.model.response.UserResponse;
import co.library.users.repository.UserRepository;
import co.library.users.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserImplService implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    public UserImplService(ModelMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<UserResponse> users() {
        List<UserEntity> users = this.repository.findAll();
        List <UserResponse> responses = new ArrayList<>();
        for (UserEntity user: users) {
            responses.add(this.mapper.map(user, UserResponse.class));
        }
        return responses;
    }

    @Override
    public UserResponse user(Long Id) throws NoDataFoundException, NotAllowedException {
        return this.mapper.map(this.findUserById(Id), UserResponse.class);
    }

    @Override
    public UserResponse updateUserRole(Long Id, String role) throws NoDataFoundException, NotAllowedException {
        UserEntity user = this.findUserById(Id);
        role = role == null ? "USER" : role;
        int roleType = Objects.equals(role, "USER") ? 0 : Objects.equals(role, "ADMIN") ? 2 : 1;
        user.setUserType(roleType);
        user.setCanSellBooks(1);
        return this.mapper.map(this.repository.save(user),UserResponse.class);
    }

    @Override
    public UserResponse updateUser(Long Id, Update body) throws NoDataFoundException, NotAllowedException {
        UserEntity user = this.findUserById(Id);
        return this.mapper.map(this.repository.save(this.prepareUserForUpdate(body, user)), UserResponse.class);
    }

    @Override
    public void logout(Long Id) throws NoDataFoundException, NotAllowedException {
        UserEntity user =  this.findUserById(Id);
        user.setIsLoggedId(0);
        this.repository.save(user);
    }

    @Override
    public void delete(long Id) throws NoDataFoundException, NotAllowedException {
        UserEntity user =  this.findUserById(Id);
        this.repository.delete(user);
    }

    @Override
    public void delete() {
        this.repository.deleteAll();
    }


    private UserEntity findUserById(Long Id) throws NoDataFoundException, NotAllowedException{
        Optional<UserEntity> optionalUser = this.repository.findById(Id);
        if (optionalUser.isEmpty()) {
            throw new NoDataFoundException("No User Found");
        }
        UserEntity user = this.mapper.map(optionalUser,UserEntity.class);
        if (user.getIsLoggedId() == null || user.getIsLoggedId() == 0) {
            throw new NotAllowedException("User Is not loggedIn");
        }
        return user;
    }

    private UserEntity prepareUserForUpdate(Update body, UserEntity user) {
        String uEmail = body.getEmail();
        String uPassword = body.getPassword();
        String uName = body.getName();
        String uAddress = body.getAddress();
        Long uBookBought = body.getBookBought();
        Long uBookSold = body.getBookSold();
        Integer uIsLoggedId = body.getIsLoggedId();
        Long uLoggingAttempt = body.getLoggingAttempt();
        if(uEmail != null && !uEmail.trim().isEmpty()){
            user.setEmail(uEmail);
        }
        if(uPassword != null && !uPassword.trim().isEmpty()){
            user.setPassword(uPassword);
        }
        if(uName != null && !uName.trim().isEmpty()){
            user.setName(uName);
        }
        if(uAddress != null && !uAddress.trim().isEmpty()){
            user.setAddress(uAddress);
        }
        if(uBookBought != null){
            user.setBookBought(uBookBought);
        }
        if(uBookSold != null){
            user.setBookSold(uBookSold);
        }
        if(uIsLoggedId != null){
            user.setIsLoggedId(uIsLoggedId);
        }
        if(uLoggingAttempt != null){
            user.setLoggingAttempt(uLoggingAttempt);
        }
        return user;
    }

}

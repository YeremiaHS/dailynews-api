package id.fazzbca.daily_news.services.user;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import id.fazzbca.daily_news.exceptions.custom.EntityFoundException;
import id.fazzbca.daily_news.models.User;
import id.fazzbca.daily_news.payloads.req.ChangePassRequest;
import id.fazzbca.daily_news.payloads.req.LoginRequest;
import id.fazzbca.daily_news.payloads.req.RegisterRequest;
import id.fazzbca.daily_news.payloads.res.ResponseHandler;
import id.fazzbca.daily_news.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> createUser(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername() == "") {
            throw new IllegalArgumentException("Username is requiered!");
        }

        if (request.getEmail() == null || request.getEmail() == "") {
            throw new IllegalArgumentException("Email is requiered!");
        }

        if (request.getPassword() == null || request.getPassword() == "") {
            throw new IllegalArgumentException("Password is requiered!");
        }
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new EntityFoundException("Username already exist");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Email already used");
        }

        User user = new User(request.getUsername(), request.getEmail(), request.getPassword());

        userRepository.save(user);

        return ResponseHandler.responseMessage(201, "berhasil menambahkan user", true);
    }

    @Override
    public ResponseEntity<?> selectUser(LoginRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Email not found");
        }

        User user = userRepository.findByEmail(request.getEmail());

        if (user.isDeleted()) {
            throw new NoSuchElementException("User does not exsit");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new NoSuchElementException("Wrong password");
        }

        return ResponseHandler.responseMessage(200, "berhasil login", true);
    }

    @Override
    public ResponseEntity<?> changePassAdminService(long id, ChangePassRequest request) {
        //find by id
        User user = userRepository.findById(id).orElseThrow(()->{
            throw new NoSuchElementException("Id is not found");
        });

        //validasi
        if (request.getEmail() == null || request.getEmail() == "") {
            throw new IllegalArgumentException("Email is requiered!");
        }

        if (request.getPassword() == null || request.getPassword() == "") {
            throw new IllegalArgumentException("Password is requiered!");
        }

        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Input correct email");
        }

        //set new password
        user.setPassword(request.getPassword());

        //save ke db
        userRepository.save(user);

        return ResponseHandler.responseData(200, "Success change password", user);
    }
    
}

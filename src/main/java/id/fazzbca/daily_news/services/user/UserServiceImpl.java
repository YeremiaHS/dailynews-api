package id.fazzbca.daily_news.services.user;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.fazzbca.daily_news.config.JwtUtil;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

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

        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return ResponseHandler.responseMessage(201, "berhasil menambahkan user", true);
    }

    public ResponseEntity<?> createUserNoHash(RegisterRequest request) {
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
        // if (!userRepository.existsByEmail(request.getEmail())) {
        //     throw new EntityFoundException("Email not found");
        // }

        // User user = userRepository.findByEmail(request.getEmail());

        // if (user.isDeleted()) {
        //     throw new NoSuchElementException("User does not exsit");
        // }

        // if (!user.getPassword().equals(request.getPassword())) {
        //     throw new NoSuchElementException("Wrong password");
        // }

        // //validate password
        // if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        //     throw new NoSuchElementException("Bad Credentials: Password doesn't match");
        // }
        
        //buat usernamepassword token
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        //autentikasi user
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //buat security context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //generate token jwt
        //token dengan 3 part: header, payload, dan signature
        String token = jwtUtil.createToken(request.getEmail());

        Map<String, Object> data = new HashMap<>();
        data.put("email", request.getEmail());
        data.put("token", token);

        //return ResponseHandler
        return ResponseHandler.responseData(200, "success login", data);
    }

    public ResponseEntity<?> selectUserBasicAuth(LoginRequest request) {
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

        //validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NoSuchElementException("Bad Credentials: Password doesn't match");
        }
        
        //generate token basic auth.
        //token basic auth => email:password => encode dengan base64

        StringBuffer baseToken = new StringBuffer();
        baseToken.append(user.getEmail());
        baseToken.append(":");
        baseToken.append(user.getPassword());

        String token = Base64.getEncoder().encodeToString(baseToken.toString().getBytes());

        Map<String, Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("token", token);

        //return ResponseHandler
        return ResponseHandler.responseData(200, "success login", true);
        // return ResponseHandler.responseMessage(200, "berhasil login", true);
    }

    public ResponseEntity<?> selectUserNoHash(LoginRequest request) {
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
    public ResponseEntity<?> changePassUserService(long id, ChangePassRequest request) {
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

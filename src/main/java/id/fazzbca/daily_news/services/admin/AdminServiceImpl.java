package id.fazzbca.daily_news.services.admin;

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
import id.fazzbca.daily_news.models.Admin;
import id.fazzbca.daily_news.payloads.req.ChangePassRequest;
import id.fazzbca.daily_news.payloads.req.LoginRequest;
import id.fazzbca.daily_news.payloads.req.RegisterRequest;
import id.fazzbca.daily_news.payloads.res.ResponseHandler;
import id.fazzbca.daily_news.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> selectAdminService(LoginRequest request) {
        // if (!adminRepository.existsByEmail(request.getEmail())) {
        //     throw new EntityFoundException("Email not found");
        // }

        // Admin admin = adminRepository.findByEmail(request.getEmail());

        // if (admin.isDeleted()) {
        //     throw new NoSuchElementException("Admin does not exsit");
        // }

        // if (!admin.getPassword().equals(request.getPassword())) {
        //     throw new NoSuchElementException("Wrong password");
        // }

        //buat usernamepasss token
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        //autentikasi user
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //security context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //generate token jwt
        String token = jwtUtil.createToken(request.getEmail());

        Map<String, Object> data = new HashMap<>();
        data.put("email", request.getEmail());
        data.put("token", token);

        //return ResponseHandler
        return ResponseHandler.responseData(200, "success login", data);
    }

    public ResponseEntity<?> selectAdminServiceNoAuth(LoginRequest request) {
        if (!adminRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Email not found");
        }

        Admin admin = adminRepository.findByEmail(request.getEmail());

        if (admin.isDeleted()) {
            throw new NoSuchElementException("Admin does not exsit");
        }

        if (!admin.getPassword().equals(request.getPassword())) {
            throw new NoSuchElementException("Wrong password");
        }

        return ResponseHandler.responseMessage(200, "login success", true);
    }

    @Override
    public ResponseEntity<?> createAdminService(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername() == "") {
            throw new IllegalArgumentException("Username is requiered!");
        }

        if (request.getEmail() == null || request.getEmail() == "") {
            throw new IllegalArgumentException("Email is requiered!");
        }

        if (request.getPassword() == null || request.getPassword() == "") {
            throw new IllegalArgumentException("Password is requiered!");
        }
        
        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new EntityFoundException("Username already exist");
        }

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Email already used");
        }

        Admin admin = new Admin(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));

        adminRepository.save(admin);

        return ResponseHandler.responseMessage(201, "Successfully added admin", true);
    }

    public ResponseEntity<?> createAdminServicenoAuth(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername() == "") {
            throw new IllegalArgumentException("Username is requiered!");
        }

        if (request.getEmail() == null || request.getEmail() == "") {
            throw new IllegalArgumentException("Email is requiered!");
        }

        if (request.getPassword() == null || request.getPassword() == "") {
            throw new IllegalArgumentException("Password is requiered!");
        }
        
        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new EntityFoundException("Username already exist");
        }

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Email already used");
        }

        Admin admin = new Admin(request.getUsername(), request.getEmail(), request.getPassword());

        adminRepository.save(admin);

        return ResponseHandler.responseMessage(201, "Successfully added admin", true);
    }

    @Override
    public ResponseEntity<?> changePassAdminService(long id, ChangePassRequest request) {
        //find admin by id
        Admin admin = adminRepository.findById(id).orElseThrow(()->{
            throw new NoSuchElementException("Id is not found");
        });

        //validasi
        if (request.getEmail() == null || request.getEmail() == "") {
            throw new IllegalArgumentException("Email is requiered!");
        }

        if (request.getPassword() == null || request.getPassword() == "") {
            throw new IllegalArgumentException("Password is requiered!");
        }

        if (!adminRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Input correct email");
        }

        //set password baru
        admin.setPassword(request.getPassword());

        //save ke db
        adminRepository.save(admin);

        return ResponseHandler.responseData(200, "Success change password", admin);
    }
    
}

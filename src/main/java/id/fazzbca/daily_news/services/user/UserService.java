package id.fazzbca.daily_news.services.user;

import org.springframework.http.ResponseEntity;

import id.fazzbca.daily_news.payloads.req.ChangePassRequest;
import id.fazzbca.daily_news.payloads.req.LoginRequest;
import id.fazzbca.daily_news.payloads.req.RegisterRequest;

public interface UserService {
    //register
    ResponseEntity<?> createUser(RegisterRequest request);

    //login
    ResponseEntity<?> selectUser(LoginRequest request);

    //forgot password
    ResponseEntity<?> forgotPassUserService(long id, ChangePassRequest request);

    //change password
    ResponseEntity<?> changePassUserService(long id, ChangePassRequest request);
}

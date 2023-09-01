package id.fazzbca.daily_news.services.admin;

import org.springframework.http.ResponseEntity;

import id.fazzbca.daily_news.payloads.req.ChangePassRequest;
import id.fazzbca.daily_news.payloads.req.LoginRequest;
import id.fazzbca.daily_news.payloads.req.RegisterRequest;

public interface AdminService {
    //regsiter
    ResponseEntity<?> createAdminService(RegisterRequest request);

    //login
    ResponseEntity<?> selectAdminService(LoginRequest request);

    //change password
    ResponseEntity<?> changePassAdminService(long id, ChangePassRequest request);
}

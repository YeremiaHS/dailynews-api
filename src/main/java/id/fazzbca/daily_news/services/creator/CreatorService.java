package id.fazzbca.daily_news.services.creator;

import org.springframework.http.ResponseEntity;

import id.fazzbca.daily_news.payloads.req.ChangePassRequest;
import id.fazzbca.daily_news.payloads.req.LoginRequest;
import id.fazzbca.daily_news.payloads.req.RegisterRequest;

public interface CreatorService {
    //regsiter
    public ResponseEntity<?> createAuthor(RegisterRequest request);

    //login
    public ResponseEntity<?> selectAuthor(LoginRequest request);

    //forgot password
    ResponseEntity<?> forgotPassCreatorService(long id, ChangePassRequest request);


    //change password
    ResponseEntity<?> changePassCreatorService(long id, ChangePassRequest request);
}

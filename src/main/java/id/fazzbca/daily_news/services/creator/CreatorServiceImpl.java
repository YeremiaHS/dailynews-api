package id.fazzbca.daily_news.services.creator;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import id.fazzbca.daily_news.exceptions.custom.EntityFoundException;
import id.fazzbca.daily_news.payloads.req.ChangePassRequest;
import id.fazzbca.daily_news.payloads.req.LoginRequest;
import id.fazzbca.daily_news.payloads.req.RegisterRequest;
import id.fazzbca.daily_news.payloads.res.ResponseHandler;
import id.fazzbca.daily_news.models.Creator;
import id.fazzbca.daily_news.repositories.CreatorRepository;

@Service
public class CreatorServiceImpl implements CreatorService {

    @Autowired
    CreatorRepository creatorRepository;

    @Override
    public ResponseEntity<?> createAuthor(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername() == "") {
            throw new IllegalArgumentException("Username is requiered!");
        }

        if (request.getEmail() == null || request.getEmail() == "") {
            throw new IllegalArgumentException("Email is requiered!");
        }

        if (request.getPassword() == null || request.getPassword() == "") {
            throw new IllegalArgumentException("Password is requiered!");
        }

        if (creatorRepository.existsByUsername(request.getUsername())) {
            throw new EntityFoundException("Username already exist");
        }

        if (creatorRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Email already used");
        }

        Creator creator = new Creator(request.getUsername(), request.getEmail(), request.getPassword());

        creatorRepository.save(creator);

        return ResponseHandler.responseMessage(201, "Successfully added creator", true);
    }

    @Override
    public ResponseEntity<?> selectAuthor(LoginRequest request) {
        if (!creatorRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Email not found");
        }

        Creator creator = creatorRepository.findByEmail(request.getEmail());

        if (creator.isDeleted()) {
            throw new NoSuchElementException("Creator does not exsit");
        }

        if (!creator.getPassword().equals(request.getPassword())) {
            throw new NoSuchElementException("Wrong password");
        }

        return ResponseHandler.responseMessage(200, "login success", true);
    }

    @Override
    public ResponseEntity<?> changePassAdminService(long id, ChangePassRequest request) {
        //find by id
        Creator creator = creatorRepository.findById(id).orElseThrow(()->{
            throw new NoSuchElementException("Id is not found");
        });

        //validasi
        if (request.getEmail() == null || request.getEmail() == "") {
            throw new IllegalArgumentException("Email is requiered!");
        }

        if (request.getPassword() == null || request.getPassword() == "") {
            throw new IllegalArgumentException("Password is requiered!");
        }

        if (!creatorRepository.existsByEmail(request.getEmail())) {
            throw new EntityFoundException("Input correct email");
        }

        //set new passowrd
        creator.setPassword(request.getPassword());

        //save ke db
        creatorRepository.save(creator);

        return ResponseHandler.responseData(200, "Success change password", creator);
    }
    
}
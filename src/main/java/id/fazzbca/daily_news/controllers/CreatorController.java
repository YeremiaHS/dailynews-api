package id.fazzbca.daily_news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.fazzbca.daily_news.payloads.req.ChangePassRequest;
import id.fazzbca.daily_news.payloads.req.LoginRequest;
import id.fazzbca.daily_news.payloads.req.RegisterRequest;
import id.fazzbca.daily_news.services.creator.CreatorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/creator")
public class CreatorController {
    
    @Autowired
    CreatorService creatorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCreator(@RequestBody @Valid RegisterRequest request){
        return creatorService.createAuthor(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCreator(@RequestBody @Valid LoginRequest request){
        return creatorService.selectAuthor(request);
    }

    @PutMapping("/change-password/{id}")
    @PreAuthorize("hasRole('CREATOR') || hasRole('ADMIN')") //hasRole('CREATOR') => endpoint yang hanya bisa diakses oleh role creator
    public ResponseEntity<?> changePassCreator(@PathVariable long id, @RequestBody ChangePassRequest request){
        return creatorService.changePassCreatorService(id, request);
    }

    @PutMapping("/forgot-password/{id}")
    public ResponseEntity<?> forgotPassCreator(@PathVariable long id, @RequestBody ChangePassRequest request){
        return creatorService.forgotPassCreatorService(id, request);
    }
}

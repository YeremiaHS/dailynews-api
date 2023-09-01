package id.fazzbca.daily_news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.fazzbca.daily_news.payloads.req.ChangePassRequest;
import id.fazzbca.daily_news.payloads.req.LoginRequest;
import id.fazzbca.daily_news.payloads.req.RegisterRequest;
import id.fazzbca.daily_news.services.admin.AdminService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid RegisterRequest request){
        return adminService.createAdminService(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody @Valid LoginRequest request){
        return adminService.selectAdminService(request);
    }

    @PutMapping("/change_password/{id}")
    public ResponseEntity<?> changePassAdmin(@PathVariable long id, @RequestBody ChangePassRequest request){
        return adminService.changePassAdminService(id, request);
    }
}

package id.fazzbca.daily_news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.fazzbca.daily_news.payloads.req.CommentRequest;
import id.fazzbca.daily_news.services.comment.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')") //hasRole('USER') => endpoint yang hanya bisa diakses oleh role user
    ResponseEntity<?> addComment(@RequestBody CommentRequest request){
        return commentService.addComment(request);
    }
}

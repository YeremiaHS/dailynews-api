package id.fazzbca.daily_news.services.comment;

import org.springframework.http.ResponseEntity;

import id.fazzbca.daily_news.payloads.req.CommentRequest;

public interface CommentService {
    //add comment
    ResponseEntity<?> addComment(CommentRequest request);
}

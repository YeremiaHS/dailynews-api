package id.fazzbca.daily_news.services.comment;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import id.fazzbca.daily_news.exceptions.custom.EntityFoundException;
import id.fazzbca.daily_news.models.Comment;
import id.fazzbca.daily_news.models.News;
import id.fazzbca.daily_news.models.User;
import id.fazzbca.daily_news.payloads.req.CommentRequest;
import id.fazzbca.daily_news.payloads.res.ResponseHandler;
import id.fazzbca.daily_news.repositories.CommentRepository;
import id.fazzbca.daily_news.repositories.NewsRepository;
import id.fazzbca.daily_news.repositories.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> addComment(CommentRequest request) {
        if (request.getNewsId() == null || request.getNewsId() == "") {
            throw new IllegalArgumentException("input news id!");
        }

        if (!newsRepository.existsById(request.getNewsId())) {
            throw new EntityFoundException("News does not exist!");
        }

        News news = newsRepository.findById(request.getNewsId()).orElseThrow(()->{
            throw new NoSuchElementException("News not found");
        });

        if (news.isDeleted()) {
            throw new EntityFoundException("News does not exist");
        }

        if (request.getCommentBy() == null || request.getCommentBy() == "") {
            throw new IllegalArgumentException("input valid username!");
        }

        if (!userRepository.existsByUsername(request.getCommentBy())) {
            throw new EntityFoundException("User does not exist!");
        }

        User user = userRepository.findByUsername(request.getCommentBy());

        if (user.isDeleted()) {
            throw new EntityFoundException("User does not exist!");
        }

        if (request.getComment() == null || request.getComment() == "") {
            throw new IllegalArgumentException("input your comment!");
        }

        Comment comment = new Comment(news, user, request.getComment());

        commentRepository.save(comment);

        return ResponseHandler.responseMessage(201, "Successfully post news", true);
    }
    
}

package id.fazzbca.daily_news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.fazzbca.daily_news.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {
    
}

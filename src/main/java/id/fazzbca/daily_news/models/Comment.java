package id.fazzbca.daily_news.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News newsId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentBy;

    @Column(length = 255)
    private String comment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private boolean isDeleted = false;

    public Comment(News newsId, User commentBy, String comment) {
        this.newsId = newsId;
        this.commentBy = commentBy;
        this.comment = comment;
    }
}

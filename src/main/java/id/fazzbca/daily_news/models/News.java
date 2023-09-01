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
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "news")
public class News {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator writtenBy;

    @Column(length = 100)
    private String title;

    @Column(length = 255)
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isDeleted = false;

    public News(Creator writtenBy, String title, String content, Category category) {
        this.writtenBy = writtenBy;
        this.title = title;
        this.content = content;
        this.category = category;
    }
}

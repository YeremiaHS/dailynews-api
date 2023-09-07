package id.fazzbca.daily_news.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "images_news")
public class Image {
    @Id
    @UuidGenerator
    private String id;
    private String sharedUrl;
    private String imageName;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    @JsonIgnore
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "news_d")
    private News news;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean isDeleted = false;

    public Image(String imageName, byte[] data, News news) {
        this.imageName = imageName;
        this.data = data;
        this.news = news;
    }
}

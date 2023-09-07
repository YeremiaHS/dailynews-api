package id.fazzbca.daily_news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.fazzbca.daily_news.models.Image;

public interface ImageNewsRepository extends JpaRepository<Image, String> {
    
}

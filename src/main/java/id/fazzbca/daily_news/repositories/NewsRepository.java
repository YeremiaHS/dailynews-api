package id.fazzbca.daily_news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.fazzbca.daily_news.models.News;

public interface NewsRepository extends JpaRepository<News, String>{
    
}

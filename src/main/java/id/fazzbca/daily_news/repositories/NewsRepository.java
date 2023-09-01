package id.fazzbca.daily_news.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.fazzbca.daily_news.models.News;
import id.fazzbca.daily_news.payloads.res.ResponseShowNews;

public interface NewsRepository extends JpaRepository<News, String>{

    @Query(value = "SELECT news.title, news.content, creator.username, category.category, news.created_at FROM `news` JOIN `creator`, `category` WHERE news.creator_id = creator.id AND news.category_id = category.id;", nativeQuery = true)
    List<ResponseShowNews> showNews();
}

package id.fazzbca.daily_news.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.fazzbca.daily_news.models.News;
import id.fazzbca.daily_news.payloads.res.ResponseShowComment;
import id.fazzbca.daily_news.payloads.res.ResponseShowNews;

public interface NewsRepository extends JpaRepository<News, String>{

    @Query(value = "SELECT news.title, news.content, creator.username, category.category, news.created_at FROM `news` JOIN `creator`, `category` WHERE news.creator_id = creator.id AND news.category_id = category.id;", nativeQuery = true)
    List<ResponseShowNews> showNews();

    @Query(value = "SELECT news.title, news.content, creator.username, category.category, news.created_at FROM `news` JOIN `creator`, `category` WHERE news.id = ?1 AND news.creator_id = creator.id AND news.category_id = category.id;", nativeQuery = true)
    ResponseShowNews readNews(String id);

    @Query(value = "SELECT news.title, news.content, creator.username, category.category, news.created_at FROM `news` JOIN `creator`, `category` WHERE news.creator_id = creator.id AND news.category_id = category.id ORDER BY news.created_at DESC;", nativeQuery = true)
    List<ResponseShowNews> showNewestNews();

    @Query(value = "SELECT news.title, comment.comment, user.username, comment.created_at FROM `comment` JOIN `user`, `news` WHERE news.id = ?1 AND comment.news_id = news.id;", nativeQuery = true)
    List<ResponseShowComment> showComments(String id);

}

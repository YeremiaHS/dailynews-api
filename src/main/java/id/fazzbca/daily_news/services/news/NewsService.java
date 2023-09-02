package id.fazzbca.daily_news.services.news;

import org.springframework.http.ResponseEntity;

import id.fazzbca.daily_news.payloads.req.NewsRequest;

public interface NewsService {
    //create news by creator
    ResponseEntity<?> addNews(NewsRequest request);

    //show all news by all level
    ResponseEntity<?> showAllNews();

    //show all news by all level sort by newest date
    ResponseEntity<?> showNewestNews();

    //read a spesific news by all level
    ResponseEntity<?> getNews(String id);

    //show comments
    ResponseEntity<?> showComment(String id);

    //edit news by admin
    //ResponseEntity<?> editNews(long id, NewsRequest request);
}

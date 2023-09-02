package id.fazzbca.daily_news.services.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import id.fazzbca.daily_news.exceptions.custom.EntityFoundException;
import id.fazzbca.daily_news.models.Category;
import id.fazzbca.daily_news.models.Creator;
import id.fazzbca.daily_news.models.News;
import id.fazzbca.daily_news.payloads.req.NewsRequest;
import id.fazzbca.daily_news.payloads.res.ResponseHandler;
import id.fazzbca.daily_news.payloads.res.ResponseShowComment;
import id.fazzbca.daily_news.payloads.res.ResponseShowNews;
import id.fazzbca.daily_news.repositories.CategoryRepository;
import id.fazzbca.daily_news.repositories.CreatorRepository;
import id.fazzbca.daily_news.repositories.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    CreatorRepository creatorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> addNews(NewsRequest request) {
        if (request.getWrittenBy() == null || request.getWrittenBy() == ""){
            throw new IllegalArgumentException("Writter is requiered!");
        }

        if (!creatorRepository.existsByUsername(request.getWrittenBy())) {
            throw new EntityFoundException("Input valid username");
        }

        Creator creator = creatorRepository.findByUsername(request.getWrittenBy());

        if (creator.isDeleted()) {
            throw new EntityFoundException("Creator invalid");
        }

        if (request.getTitle() == null || request.getTitle() == "") {
            throw new IllegalArgumentException("Title is requiered!");
        }

        if (request.getContent() == null || request.getContent() == "") {
            throw new IllegalArgumentException("Content is requiered!");
        }

        if (request.getCategory() == null || request.getCategory() == "") {
            throw new IllegalArgumentException("Category is requiered!");
        }

        if (!categoryRepository.existsByCategory(request.getCategory())) {
            throw new EntityFoundException("Input valid category");
        }

        Category category = categoryRepository.findByCategory(request.getCategory());

        if (category.isDeleted()) {
            throw new EntityFoundException("Category does not exist");
        }

        News news = new News(creator, request.getTitle(), request.getContent(), category);

        newsRepository.save(news);

        return ResponseHandler.responseMessage(201, "Successfully post news", true);

    }

    @Override
    public ResponseEntity<?> showAllNews() {
        List<ResponseShowNews> responses = newsRepository.showNews();
        return ResponseHandler.responseData(200, "success", responses);
    }

    @Override
    public ResponseEntity<?> showNewestNews() {
        List<ResponseShowNews> reponses = newsRepository.showNewestNews();
        return ResponseHandler.responseData(200, "success", reponses);
    }

    @Override
    public ResponseEntity<?> getNews(String id) {
        ResponseShowNews news = newsRepository.readNews(id);
        
        return ResponseHandler.responseData(200, "success", news);
    }

    @Override
    public ResponseEntity<?> showComment(String id) {
        List<ResponseShowComment> responses = newsRepository.showComments();
        return ResponseHandler.responseData(200, "success", responses);
    }

    // @Override
    // public ResponseEntity<?> editNews(long id, NewsRequest request) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'editNews'");
    // }
    
}

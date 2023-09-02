package id.fazzbca.daily_news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.fazzbca.daily_news.payloads.req.NewsRequest;
import id.fazzbca.daily_news.services.news.NewsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @PostMapping("/add")
    ResponseEntity<?> addNews(@RequestBody @Valid NewsRequest request){
        return newsService.addNews(request);
    }

    @GetMapping("/all")
    ResponseEntity<?> showNews(){
        return newsService.showAllNews();
    }

    @GetMapping("/read/{id}")
    ResponseEntity<?> readNews(@PathVariable String id){
        return newsService.getNews(id);
    }

    @GetMapping("/recent")
    ResponseEntity<?> showNewestNews(){
        return newsService.showNewestNews();
    }

    @GetMapping("/comment/{id}")
    ResponseEntity<?> showComments(@PathVariable String id){
        return newsService.showComment(id);
    }
}

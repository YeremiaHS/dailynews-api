package id.fazzbca.daily_news.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import id.fazzbca.daily_news.services.image.ImageNewsService;

@RestController
@RequestMapping("files")
public class NewsImageController {
    @Autowired
    ImageNewsService imageNewsService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('CREATOR') || hasRole('ADMIN')") //hasRole('CREATOR') => endpoint yang hanya bisa diakses oleh role CREATOR
    public ResponseEntity<?> storeImage(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "newsId") String newsId) throws IOException {
        return imageNewsService.storeImage(file, newsId);
    }

    @GetMapping("/news{imageId}")
    public ResponseEntity<?> loadImage(@PathVariable String imageId) {
        return imageNewsService.loadImagae(imageId);
    }
}

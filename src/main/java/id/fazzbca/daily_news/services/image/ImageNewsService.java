package id.fazzbca.daily_news.services.image;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageNewsService {
    ResponseEntity<?> storeImage(MultipartFile file, String newsId) throws IOException;

    ResponseEntity<?> loadImagae(String imageId);
}

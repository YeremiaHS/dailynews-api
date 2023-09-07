package id.fazzbca.daily_news.services.image;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.fazzbca.daily_news.models.Image;
import id.fazzbca.daily_news.models.News;
import id.fazzbca.daily_news.payloads.res.ResponseHandler;
import id.fazzbca.daily_news.repositories.ImageNewsRepository;
import id.fazzbca.daily_news.repositories.NewsRepository;

@Service
public class ImageNewsServiceImpl implements ImageNewsService {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ImageNewsRepository imageNewsRepository;

    @Override
    public ResponseEntity<?> storeImage(MultipartFile file, String newsId) throws IOException {
        //ambil nama gambar
        String imgName = StringUtils.cleanPath(file.getOriginalFilename());

        //cari news
        News news = newsRepository.findById(newsId).orElseThrow(()-> new NoSuchElementException("News is not found!"));

        //buatkan entitas iamge news
        Image image = new Image(imgName, file.getBytes(), news);
        //simpan id 
        imageNewsRepository.save(image);

        //buat share url
        String sharedUrl = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/files/news")
        .path(image.getId())
        .toUriString();

        //set sharedurl ke obj image news
        image.setSharedUrl(sharedUrl);
        imageNewsRepository.save(image);

        return ResponseHandler.responseData(201, "Success store imae", image);
    }

    @Override
    public ResponseEntity<?> loadImagae(String imageId) {
        Image image = imageNewsRepository.findById(imageId).orElseThrow(()-> new NoSuchElementException("Image is not found"));

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getImageName() + "\"").body(image.getData());
    }
    
}

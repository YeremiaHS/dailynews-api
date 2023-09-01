package id.fazzbca.daily_news.services.category;

import org.springframework.http.ResponseEntity;

import id.fazzbca.daily_news.payloads.req.CategoryRequest;

public interface CategoryService {
    //add category
    ResponseEntity<?> createCategory(CategoryRequest request);

    //read all category
    ResponseEntity<?> showCategory();

    //edit category
    ResponseEntity<?> editCategory(long id, CategoryRequest request);

    //delete category
    ResponseEntity<?> deleteCategory(long id);
}

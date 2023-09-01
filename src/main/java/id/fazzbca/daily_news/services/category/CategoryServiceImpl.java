package id.fazzbca.daily_news.services.category;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import id.fazzbca.daily_news.exceptions.custom.EntityFoundException;
import id.fazzbca.daily_news.models.Admin;
import id.fazzbca.daily_news.models.Category;
import id.fazzbca.daily_news.payloads.req.CategoryRequest;
import id.fazzbca.daily_news.payloads.res.ResponseHandler;
import id.fazzbca.daily_news.repositories.AdminRepository;
import id.fazzbca.daily_news.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AdminRepository adminRepository;

    @Override
    public ResponseEntity<?> createCategory(CategoryRequest request) {
        if (request.getCategory() == null || request.getCategory() == "") {
            throw new IllegalArgumentException("Category is requiered!");
        }

        if (categoryRepository.existsByCategory(request.getCategory())) {
            throw new EntityFoundException("Category already exist");
        }

        if (request.getCreatedBy() == null || request.getCreatedBy() == "") {
            throw new IllegalArgumentException("Admin is requiered!");
        }

        if (!adminRepository.existsByUsername(request.getCreatedBy())) {
            throw new EntityFoundException("Input correct username");
        }

        Admin admin = adminRepository.findByUsername(request.getCreatedBy());

        if (admin.isDeleted()) {
            throw new NoSuchElementException("Admin not found");
        }

        if (request.getDescription() == null || request.getDescription() == "") {
            throw new IllegalArgumentException("Description is requiered!");
        }

        Category category = new Category(request.getCategory(), admin, request.getDescription());

        categoryRepository.save(category);

        return ResponseHandler.responseMessage(201, "Successfully added category", true);

    }

    @Override
    public ResponseEntity<?> showCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showCategory'");
    }

    @Override
    public ResponseEntity<?> editCategory(long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(()->{
            throw new NoSuchElementException("Category not found");
        });

        if (request.getCategory() != "") {
            category.setCategory(request.getCategory());
        }

        if (request.getCreatedBy() != "") {
            Admin admin = adminRepository.findByUsername(request.getCreatedBy());
            category.setCreatedBy(admin);
        }

        if (request.getDescription() != "") {
            category.setDescription(request.getDescription());
        }

        categoryRepository.save(category);

        return ResponseHandler.responseData(200, "Success change password", category);
    }

    @Override
    public ResponseEntity<?> deleteCategory(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCategory'");
    }
    
}

package id.fazzbca.daily_news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.fazzbca.daily_news.payloads.req.CategoryRequest;
import id.fazzbca.daily_news.services.category.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')") //hasRole('ADMIN') => endpoint yang hanya bisa diakses oleh role admin
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryRequest request){
        return categoryService.createCategory(request);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')") //hasRole('ADMIN') => endpoint yang hanya bisa diakses oleh role admin
    public ResponseEntity<?> editCategory(@PathVariable long id, @RequestBody CategoryRequest request){
        return categoryService.editCategory(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')") //hasRole('ADMIN') => endpoint yang hanya bisa diakses oleh role admin
    public ResponseEntity<?> deleteCategory(@PathVariable long id){
        return categoryService.deleteCategory(id);
    }

    @DeleteMapping("/recycle/{id}")
    @PreAuthorize("hasRole('ADMIN')") //hasRole('ADMIN') => endpoint yang hanya bisa diakses oleh role admin
    public ResponseEntity<?> recycleCategory(@PathVariable long id){
        return categoryService.recycleCategory(id);
    }

    @GetMapping("/available")
    public ResponseEntity<?> showCategory(){
        return categoryService.showCategory();
    }
}

package id.fazzbca.daily_news.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.fazzbca.daily_news.models.Category;
import id.fazzbca.daily_news.payloads.res.ResponseShowCategory;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Boolean existsByCategory(String category);

    //List<Category> findAll();
    @Query(value = "SELECT id, category,description,creator_id FROM `category` WHERE is_deleted = false;", nativeQuery = true)
    List<ResponseShowCategory> ShowCategory();

    Optional<Category> findById(long id);
}

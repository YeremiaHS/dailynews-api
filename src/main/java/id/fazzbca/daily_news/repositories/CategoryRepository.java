package id.fazzbca.daily_news.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.fazzbca.daily_news.models.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Boolean existsByCategory(String category);

    //List<Category> findAll();

    Optional<Category> findById(long id);
}

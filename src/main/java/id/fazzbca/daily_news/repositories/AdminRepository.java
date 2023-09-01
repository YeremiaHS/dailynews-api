package id.fazzbca.daily_news.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.fazzbca.daily_news.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);

    Admin findByUsername(String username);

    Admin findByEmail(String email);

    Admin findByPassword(String password);

    Optional<Admin> findById(long id);
}

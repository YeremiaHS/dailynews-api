package id.fazzbca.daily_news.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.fazzbca.daily_news.models.User;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByPassword(String password);

    Optional<User> findById(long id);
}

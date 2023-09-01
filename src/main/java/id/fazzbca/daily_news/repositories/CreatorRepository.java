package id.fazzbca.daily_news.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.fazzbca.daily_news.models.Creator;

public interface CreatorRepository extends JpaRepository<Creator, String> {
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);

    Creator findByUsername(String username);

    Creator findByEmail(String email);

    Creator findByPassword(String password);

    Optional<Creator> findById(long id);
}

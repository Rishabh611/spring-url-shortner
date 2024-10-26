package org.SystemDesign.repository;

import org.SystemDesign.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, Long> {
    Optional<URL> findByShortUrl(String shortUrl);
    Optional<URL> findByOriginalUrl(String originalUrl);
}
 
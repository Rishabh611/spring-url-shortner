package org.SystemDesign.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;
    @Column(name = "short_url", unique = true, nullable = false, length = 10)
    private String shortUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}

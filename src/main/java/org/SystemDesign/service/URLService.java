package org.SystemDesign.service;

import org.SystemDesign.model.URL;
import org.SystemDesign.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class URLService {
    @Autowired
    private URLRepository urlRepository;

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    private static final int CODE_LENGTH = 6;

    public URL createShortUrl(String originalURL) {
        String shortUrl = generateShortURl();
        URL url = new URL();
        url.setOriginalUrl(originalURL);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());
        url.setExpiresAt(LocalDateTime.now().plusDays(7));
        return urlRepository.save(url);
    }

    public URL getOriginalURL(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new RuntimeException("URL not found"));
    }

    private String generateShortURl() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}

package org.SystemDesign.controller;

import org.SystemDesign.entity.URL;
import org.SystemDesign.repository.URLRepository;
import org.SystemDesign.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class URLController {
    @Autowired
    private URLService urlService;
    @Autowired
    private URLRepository urlRepository;

    @PostMapping("/shorten")
    public ResponseEntity<String> createShortUrl(@RequestBody String originalUrl) throws NoSuchAlgorithmException {
        Optional<URL> existingUrl = urlRepository.findByOriginalUrl(originalUrl);

        if (existingUrl.isPresent()){
            return ResponseEntity.ok(existingUrl.get().getShortUrl());
        }
        String shortUrl = urlService.shortenUrl(originalUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirectToOriginalUrl(@PathVariable String shortUrl) {
        return urlService.getOriginalUrl(shortUrl)
                .map(url -> ResponseEntity.status(302).header("Location", url).build())
                .orElse(ResponseEntity.notFound().build());
    }
}

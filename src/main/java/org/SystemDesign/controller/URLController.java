package org.SystemDesign.controller;

import org.SystemDesign.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api")
public class URLController {
    @Autowired
    private URLService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> createShortUrl(@RequestBody String originalUrl) throws NoSuchAlgorithmException {
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

package org.SystemDesign.controller;

import org.SystemDesign.model.URL;
import org.SystemDesign.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/url")
public class URLController {
    @Autowired
    private URLService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<URL> createShortUrl(@RequestBody String originalUrl) {
        URL url = urlService.createShortUrl(originalUrl);
        return new ResponseEntity<>(url, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
        URL url = urlService.getOriginalURL(shortUrl);
        System.out.println(url);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getOriginalUrl())).build();
    }
}

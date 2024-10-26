package org.SystemDesign.service;

import org.SystemDesign.entity.URL;
import org.SystemDesign.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class URLService {
    @Autowired
    private URLRepository urlRepository;

    public String shortenUrl(String originalUrl) throws NoSuchAlgorithmException {
        Optional<URL> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get().getShortUrl();
        }

        String shortUrl = generateHash(originalUrl);

        URL url = new URL();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        urlRepository.save(url);

        return shortUrl;
    }

    public Optional<String> getOriginalUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).map(URL::getOriginalUrl);
    }

    private String generateHash(String url) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = messageDigest.digest(url.getBytes());

        StringBuilder hexString = new StringBuilder();
        for(int i = 0; i<3; i++) {
            String hex = Integer.toHexString(0xff & hashBytes[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

package org.SystemDesign;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.SystemDesign.repository.URLRepository;
import org.SystemDesign.service.URLService;
import org.SystemDesign.entity.URL;

@SpringBootTest
class URLShortenerApplicationTest {

    @MockBean
    private URLRepository urlRepository;

    @Autowired
    private URLService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShortenUrl_NewUrl_GeneratesShortenedUrl() throws NoSuchAlgorithmException {
        String originalUrl = "https://www.example.com";
        String generatedHash = "cdb4d8";

        // Mock repository to simulate a new URL
        when(urlRepository.findByShortUrl(generatedHash)).thenReturn(Optional.empty());
        when(urlRepository.save(any(URL.class))).thenAnswer(invocation -> {
            URL url = invocation.getArgument(0);
            url.setId(1L); // Simulate assigned ID after save
            return url;
        });

        String shortenedUrl = urlService.shortenUrl(originalUrl);

        assertEquals(generatedHash, shortenedUrl);
        verify(urlRepository, times(1)).save(any(URL.class));
    }

    @Test
    void testShortenUrl_ExistingUrl_ReturnsSameHash() throws NoSuchAlgorithmException {
        String originalUrl = "https://www.example.com";
        String generatedHash = "cdb4d8";
        URL existingUrl = new URL();
        existingUrl.setOriginalUrl(originalUrl);
        existingUrl.setShortUrl(generatedHash);

        // Mock repository to simulate existing URL
        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(Optional.of(existingUrl));

        String shortenedUrl = urlService.shortenUrl(originalUrl);

        assertEquals(generatedHash, shortenedUrl);
//        verify(urlRepository, never()).save(any(URL.class)); // No save needed for existing URL
    }

    @Test
    void testGetOriginalUrl_ValidShortenedPath_ReturnsOriginalUrl() {
        String shortenedPath = "abc123";
        String originalUrl = "https://www.example.com";
        URL url = new URL();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortenedPath);

        // Mock repository to simulate existing shortened path
        when(urlRepository.findByShortUrl(shortenedPath)).thenReturn(Optional.of(url));

        Optional<String> result = urlService.getOriginalUrl(shortenedPath);

        assertTrue(result.isPresent());
        assertEquals(originalUrl, result.get());
    }

    @Test
    void testGetOriginalUrl_InvalidShortenedPath_ReturnsEmptyOptional() {
        String shortenedPath = "invalid";

        // Mock repository to simulate non-existing shortened path
        when(urlRepository.findByShortUrl(shortenedPath)).thenReturn(Optional.empty());

        Optional<String> result = urlService.getOriginalUrl(shortenedPath);

        assertFalse(result.isPresent());
    }
}


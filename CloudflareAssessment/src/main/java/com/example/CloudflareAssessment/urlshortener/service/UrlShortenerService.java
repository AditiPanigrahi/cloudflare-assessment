package com.example.CloudflareAssessment.urlshortener.service;

import com.example.CloudflareAssessment.data.service.UrlData;
import com.example.CloudflareAssessment.data.service.UrlDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
    public class UrlShortenerService {
    @Autowired
    private UrlDataRepository urlDataRepository;

    private final String BASE62_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String shortenUrl(String originalUrl) {
        UrlData urlData = new UrlData();
        urlData.setOriginalUrl(originalUrl);
        // Generate short URL logic
        String shortUrl = generateShortCode();
        urlData.setShortUrl(shortUrl);
        System.out.println(shortUrl);

        urlDataRepository.save(urlData);
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        UrlData urlData = urlDataRepository.findByShortUrl(shortUrl);
        System.out.println(shortUrl);
        return urlData != null ? urlData.getOriginalUrl() : null;
    }

    private String generateShortCode() {
        // Generate short code using Base62 encoding
        StringBuilder shortCode = new StringBuilder();
        long id = System.currentTimeMillis();
        while (id > 0) {
            shortCode.insert(0, BASE62_CHARACTERS.charAt((int) (id % 62)));
            id /= 62;
        }
        return shortCode.toString();
    }
}

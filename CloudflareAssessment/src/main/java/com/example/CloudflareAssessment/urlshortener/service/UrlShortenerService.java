package com.example.CloudflareAssessment.urlshortener.service;

import com.example.CloudflareAssessment.data.service.UrlData;
import com.example.CloudflareAssessment.data.service.UrlDataRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Url Shortener Service
 */
@Component
public class UrlShortenerService {
    @Autowired
    private UrlDataRepository urlDataRepository;

    //read from application property
    private Integer threadCount = 10;

    private final Counter clicksCounter;

    public UrlShortenerService(MeterRegistry meterRegistry) {
        this.clicksCounter = meterRegistry.counter("url_clicks_last_24_hours",
                "Counts the number of clicks in the last 24 hours","");
    }

    /**
     * Method to shorten a given Url
     * @param originalUrl
     * @return shortUrl String
     */
    public String shortenUrl(String originalUrl) {
        UrlData urlData = urlDataRepository.findByOriginalUrl(originalUrl);
        if(urlData != null) {
            return urlData.getShortUrl();
        } else {
            urlData = new UrlData();
        }
        urlData.setOriginalUrl(originalUrl);
        // Generate short URL logic
        String shortUrl = generateShortCode();
        urlData.setShortUrl(shortUrl);
        System.out.println(shortUrl);
        urlDataRepository.save(urlData);

        // Using base url from localhost server as hosted server for short url is not setup
        return "http://localhost:8080/api/"+shortUrl;
    }

    /**
     * Method to get original url for redirection from short url
     * @param shortUrl
     * @return original url
     */
    public String getOriginalUrl(String shortUrl) {
        UrlData urlData = urlDataRepository.findByShortUrl(shortUrl);
        clicksCounter.increment();
        return urlData != null ? urlData.getOriginalUrl() : null;
    }

    /**
     * Method to delete short Url using the original Url
     * @param originalUrl
     * @return shortUrl String
     */
    public String removeShortUrl(String originalUrl) {
        UrlData urlData = urlDataRepository.findByOriginalUrl(originalUrl);
        if (urlData != null) {
            urlDataRepository.delete(urlData);
            return urlData.getShortUrl();
        }
        return null;
    }

    private String generateShortCode() {
        // Generate short code using Base62 encoding
        StringBuilder shortCode = new StringBuilder();
        long id = System.currentTimeMillis();
        while (id > 0) {
            String BASE62_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            shortCode.insert(0, BASE62_CHARACTERS.charAt((int) (id % 62)));
            id /= 62;
        }
        return shortCode.toString();
    }
}

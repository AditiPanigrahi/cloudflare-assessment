package com.example.CloudflareAssessment.controller;

import com.example.CloudflareAssessment.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
class ShortUrlController {
    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shortenUrl")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {
        String shortenedUrl = urlShortenerService.shortenUrl(originalUrl);
        return new ResponseEntity<>(shortenedUrl, HttpStatus.OK);
    }

    @GetMapping("/{shortenedId}")
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable String shortenedId) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortenedId);
        if (originalUrl != null) {
            return new ResponseEntity<>(originalUrl, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("URL not found", HttpStatus.NOT_FOUND);
        }
    }

}

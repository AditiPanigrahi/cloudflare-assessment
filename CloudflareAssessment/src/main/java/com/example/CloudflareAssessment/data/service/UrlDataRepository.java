package com.example.CloudflareAssessment.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Repository for short URL persistence
 */
public interface UrlDataRepository extends JpaRepository<UrlData, Long>{
    UrlData findByShortUrl(String shortUrl);
    UrlData findByOriginalUrl(String originalUrl);
}

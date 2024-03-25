package com.example.CloudflareAssessment.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlDataRepository extends JpaRepository<UrlData, Long>{
    UrlData findByShortUrl(String shortUrl);
}

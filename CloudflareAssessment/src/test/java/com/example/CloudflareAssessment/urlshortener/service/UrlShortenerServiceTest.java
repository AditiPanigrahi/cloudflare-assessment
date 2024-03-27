package com.example.CloudflareAssessment.urlshortener.service;

import com.example.CloudflareAssessment.data.service.UrlData;
import com.example.CloudflareAssessment.data.service.UrlDataRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
class UrlShortenerServiceTest {

    @Autowired
    UrlShortenerService urlShortenerService;

    @Autowired
    private UrlDataRepository urlDataRepository;

    @BeforeEach
    void setUp() {
        UrlData urlData = getUrlData();
        when(urlDataRepository.findByShortUrl(any())).thenReturn(urlData);
    }

    private static UrlData getUrlData() {
        UrlData urlData = new UrlData();
        urlData.setOriginalUrl("google.com");
        urlData.setShortUrl("EIigh1f");
        return urlData;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shortenUrl() {
        String shortenedUrl = urlShortenerService.shortenUrl(getUrlData().getOriginalUrl());
        Assert.assertNotNull(shortenedUrl);
    }

    @Test
    void getOriginalUrl() {
    }
}
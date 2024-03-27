package com.example.CloudflareAssessment.urlshortener.service;

import com.example.CloudflareAssessment.data.service.UrlData;
import com.example.CloudflareAssessment.data.service.UrlDataRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
class UrlShortenerServiceTest {

    @Mock
    static Counter clicksCounter;
    @Mock
    static MeterRegistry meterRegistry;

    @TestConfiguration
    static class UrlShortenerServiceTestContextConfiguration {

        @Bean
        public UrlShortenerService urlShortenerService() {
            when(meterRegistry.counter(any(),
                    any(),any())).thenReturn(clicksCounter);
            return new UrlShortenerService(meterRegistry);
        }
    }

    @Autowired
    UrlShortenerService urlShortenerService;
    @MockBean
    UrlDataRepository urlDataRepository;

    @BeforeEach
    void setUp() {
        when(meterRegistry.counter(any(),
                any(),any())).thenReturn(clicksCounter);
     }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestShortenUrlSuccess() {
        UrlData urlData = getUrlData();

        when(urlDataRepository.findByShortUrl(any())).thenReturn(urlData);
        when(urlDataRepository.findByOriginalUrl(any())).thenReturn(null);

        String shortenedUrl = this.urlShortenerService.shortenUrl(getUrlData().getOriginalUrl());
        Assert.assertNotNull(shortenedUrl);
    }

    @Test
    void TestGetOriginalUrlSuccess() {
        UrlData urlData = getUrlData();
        when(urlDataRepository.findByShortUrl(any())).thenReturn(urlData);
        String originalUrl = this.urlShortenerService.getOriginalUrl(getUrlData().getShortUrl());
        Assert.assertNotNull(originalUrl);
        Assert.assertEquals(urlData.getOriginalUrl(), originalUrl);
    }

    @Test
    void TestGetOriginalUrlFailure() {
        UrlData urlData = getUrlData();
        when(urlDataRepository.findByShortUrl(any())).thenReturn(null);
        String originalUrl = this.urlShortenerService.getOriginalUrl(getUrlData().getShortUrl());
        Assert.assertNull(originalUrl);

    }

    private static UrlData getUrlData() {
        UrlData urlData = new UrlData();
        urlData.setOriginalUrl("google.com");
        urlData.setShortUrl("EIigh1f");
        return urlData;
    }

}
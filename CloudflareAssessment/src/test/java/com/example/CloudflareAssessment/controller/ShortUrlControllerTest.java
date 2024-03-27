package com.example.CloudflareAssessment.controller;

import com.example.CloudflareAssessment.data.service.UrlDataRepository;
import com.example.CloudflareAssessment.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.UpperCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
class ShortUrlControllerTest {

    @Autowired
    ShortUrlController shortUrlController;

    @MockBean
    UrlShortenerService urlShortenerService;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shortenUrlTestSuccess() throws Exception {
        mockMvc.perform(post("/api/shortenUrl").content("google.com"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void redirectToOriginalUrlSuccess() throws Exception {
        String shortenedUrl = "Eidhgf";
        String originalUrl = "google.com";
        when(urlShortenerService.getOriginalUrl(shortenedUrl)).thenReturn(originalUrl);

        mockMvc.perform(get("/api/"+shortenedUrl))
                .andDo(print()).andExpect(status().isFound());
    }

    @Test
    void redirectToOriginalUrlFailure() throws Exception {
        String shortenedUrl = "Eidhgf";
        String originalUrl = "google.com";

        mockMvc.perform(get("/api/"+shortenedUrl))
                .andDo(print()).andExpect(status().isNotFound());
    }
}
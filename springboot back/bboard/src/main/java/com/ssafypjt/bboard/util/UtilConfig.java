package com.ssafypjt.bboard.util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Configuration
public class UtilConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    };
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://solved.ac").build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }



}
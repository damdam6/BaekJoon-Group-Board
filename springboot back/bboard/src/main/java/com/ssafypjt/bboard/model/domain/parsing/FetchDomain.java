package com.ssafypjt.bboard.model.domain.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ssafypjt.bboard.model.dto.UserTier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class FetchDomain {
    private final WebClient webClient;

    public FetchDomain(WebClient webClient) {
        this.webClient = webClient;
    }

//    public Flux<JsonNode> fetchProblemPageData(String userName, String pageNum) {
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/api/v3/search/problem")
//                        .queryParam("query", "@" + userName)
//                        .queryParam("sort", "level")
//                        .queryParam("direction", "desc")
//                        .queryParam("page", pageNum)
//                        .build()).retrieve()
//                .bodyToFlux(JsonNode.class);
//    }

    public Flux<JsonNode> fetchOneQueryData(String pathQuery, String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(pathQuery)
                        .query(query)
                        .build()).retrieve()
                .bodyToFlux(JsonNode.class);
    }

    public Flux<UserTier> fetchOneQueryDataUserTIer(String pathQuery, String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(pathQuery)
                        .query(query)
                        .build()).retrieve()
                .bodyToFlux(UserTier.class);
    }

}

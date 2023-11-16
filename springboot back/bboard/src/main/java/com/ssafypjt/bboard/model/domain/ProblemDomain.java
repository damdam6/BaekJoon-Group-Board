package com.ssafypjt.bboard.model.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafypjt.bboard.util.UtilConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.ssafypjt.bboard.model.domain.ProblemAndAlgoObjectDomain;

@Component
public class ProblemDomain {
    private final WebClient webClient;
    private List<ProblemAndAlgoObjectDomain> tmpProblemAndAlgoList =  new ArrayList<>();
    public ProblemDomain(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<JsonNode> fetchProblemData(String userName, String pageNum) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/search/problem")
                        .queryParam("query", "@" + userName)
                        .queryParam("sort", "level")
                        .queryParam("direction", "desc")
                        .queryParam("page", pageNum)
                        .build()).retrieve()
                .bodyToFlux(JsonNode.class);
    }
    public void processUserList(List<String> userIds, List<String> parametersList) {
        Flux.fromIterable(userIds)
                .flatMap(userId ->
                        Flux.fromIterable(parametersList)
                                .flatMap(parameter -> fetchProblemData(userId, parameter))
                )
                .subscribe(
                        data -> {

                        },
                        error -> {
                            error.printStackTrace();
                        },
                        () -> {

                        }

                );
    };


    public void makeProblemAlgoObject(JsonNode aNode) {
            JsonNode itemsNode = aNode.path("items");
            if (itemsNode.isArray()) {
                for (JsonNode item : itemsNode) {

                    JsonNode tagsNode = item.path("tags");
                    if (tagsNode.isArray()) {
                        for (JsonNode tag : tagsNode) {
                            String key = tag.path("key").asText();
                            System.out.println(key);
                        }
                    }
                }
            }
        }
    }


    @Scheduled(fixedRate = 5000)
    public void schedulTask() {
        System.out.println("test");
        List<String> list = Arrays.asList("end24", "29tigerhg", "98cline", "ygj9605");
        List<String> pageN = Arrays.asList("1", "2");
        this.processUserList(list, pageN);
    }

}


package com.ssafypjt.bboard.model.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.repository.UserRepository;
import com.ssafypjt.bboard.model.service.ProblemService;
import com.ssafypjt.bboard.util.UtilConfig;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.ssafypjt.bboard.model.domain.ProblemAndAlgoObjectDomain;
import reactor.util.function.Tuples;

@Component
public class ProblemDomain {
    private final WebClient webClient;
    private ObjectMapper mapper;
    private List<ProblemAndAlgoObjectDomain> tmpProblemAndAlgoList =  new ArrayList<>();


    @Autowired
    ProblemService problemService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ProblemDomain(WebClient webClient, ObjectMapper mapper) {
        this.webClient = webClient;
        this.mapper = mapper;
    }


    public List<ProblemAndAlgoObjectDomain> proAndAlgoList = new ArrayList<>();

    //userName & pageNum 으로 Api불러옴
    public Flux<JsonNode> fetchProblemPageData(String userName, String pageNum) {
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

    public Flux<JsonNode> fetchOneQueryData(String pathQuery, String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(pathQuery)
                        .query(query)
                        .build()).retrieve()
                .bodyToFlux(JsonNode.class);
    }
    public String makeUser100query(User user){

        return "handle="+user.getUserName();
    }

    synchronized public void processUserList(List<User> users) {
        proAndAlgoList.clear();
        Flux.fromIterable(users)
                .flatMap(user -> fetchOneQueryData("/api/v3/user/top_100",makeUser100query(user))
                                        .map(data -> Tuples.of(data, user))) // User 객체와 함께 튜플 생성
                .subscribe(
                        tuple -> {
                            JsonNode data = tuple.getT1(); // 튜플에서 데이터 추출
                            User user = tuple.getT2(); // 튜플에서 User 객체 추출
                            this.makeProblemAndAlgoDomainObject(data, user);
                        },
                        error -> {
                            error.printStackTrace();
                        },
                        () -> {
                            problemService.resetProblems(proAndAlgoList);
                        }

                );
    };

    public void makeProblemAndAlgoDomainObject(JsonNode aNode, User user) {
        JsonNode arrayNode = aNode.path("items");
        if(!arrayNode.isArray()){
            return;
        }
        for(JsonNode nodeItem: arrayNode) {
                Problem problem = makeProblemObject(nodeItem, user);
                ProblemAlgorithm problemAlgorithm = makeProblemAlgorithmObject(nodeItem);
                ProblemAndAlgoObjectDomain problemAndAlgoObjectDomain = new ProblemAndAlgoObjectDomain(problem,problemAlgorithm);
                proAndAlgoList.add(problemAndAlgoObjectDomain);
        }
    }


    public Problem makeProblemObject(JsonNode nodeItem, User user) {
        Problem problem = null;
        try {
            problem = mapper.treeToValue(nodeItem, Problem.class);
            problem.setUserId(user.getUserId());
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return problem;
    }

    public ProblemAlgorithm makeProblemAlgorithmObject(JsonNode nodeItem){
        StringBuilder algorithms = new StringBuilder();
        JsonNode tagsNode = nodeItem.path("tags");
        for (JsonNode tag : tagsNode) {
            if (algorithms.length() > 0) {
                algorithms.append(" ");
            }
            algorithms.append(tag.path("key").asText());
        }

        ProblemAlgorithm problemAlgorithm = new ProblemAlgorithm();
        problemAlgorithm.setProblemNum(nodeItem.path("problemId").asInt());
        problemAlgorithm.setAlgorithm(algorithms.toString());

        return problemAlgorithm;
    }


    @Scheduled(fixedRate = 50000)
    public void schedulTask() {
        long srt = System.nanoTime();
        // 전체 유저 목록
        List<User> userList = userRepository.selectAllUser();
        processUserList(userList);
        long end = System.nanoTime();

        System.out.println((end-srt)/1_000_000);
    }

}


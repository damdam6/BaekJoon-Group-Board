package com.ssafypjt.bboard.model.domain.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import com.ssafypjt.bboard.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDomain {

    @Autowired
    private ObjectMapper mapper;

    private final WebClient webClient;

    public List<User> userList = new ArrayList<>();

    public UserDomain(WebClient webClient) {
        this.webClient = webClient;
    }

    public User makeUserObject(JsonNode aNode) {
        List<ProblemAndAlgoObjectDomain> tmpList = new ArrayList<>();
        return mapper.convertValue(aNode, User.class);
    }


}

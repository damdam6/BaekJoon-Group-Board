package com.ssafypjt.bboard.model.domain.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.enums.SACApiEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component

public class UserTierProblemDomain {
    private ObjectMapper mapper;
    private final RestTemplate restTemplate;

    public UserTierProblemDomain(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    // 동기적으로 아이템 가져오기 (restTemplate 사용)
    public List<Problem> syncMakeUserTierProblem(UserTier uSerTier, User user){
        List<Problem> problemList = new ArrayList<>();
        String url = SACApiEnum.USERTIERPROBLEM.getPath() + "?"+ SACApiEnum.USERTIERPROBLEM.getQuery(user.getUserName(), uSerTier.getPageNo());
        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class);
        for(JsonNode aNode : jsonNode.get("items")){
            try {
                problemList.add(mapper.treeToValue(aNode, Problem.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return problemList;
    }






}

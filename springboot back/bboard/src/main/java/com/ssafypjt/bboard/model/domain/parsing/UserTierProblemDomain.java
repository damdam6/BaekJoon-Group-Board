package com.ssafypjt.bboard.model.domain.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.repository.UserTierProblemRepository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

public class UserTierProblemDomain {
    private ObjectMapper mapper;

    private final WebClient webClient;

    private UserTierProblemRepository userTierProblemRepository;

    final int MAX_TIER = 30;
    final int NUMBER_OF_PAGES = 50;

    public UserTierProblemDomain(ObjectMapper mapper, WebClient webClient, UserTierProblemRepository userTierProblemRepository) {
        this.mapper = mapper;
        this.webClient = webClient;
        this.userTierProblemRepository = userTierProblemRepository;
    }

    public List<Problem> makeUserTierProblemObject(JsonNode aNode, User user, List<UserTier> userTierList){

        List<Problem> userTierProblemList = new ArrayList<>();
        int currPage = 0;
        // 맵으로 수정
        List<Problem> apiProblemList = null;
        for (int i = MAX_TIER; i >=0; i--) {
            UserTier now = userTierList.get(i);
            if (now.getPageNo() != currPage){ // 페이지를 안가져온 경우에만 API 호출
                currPage = now.getPageNo();
                // problemAndAlgoObjectDomainList = 페이지를 가져오는 API
            }
            userTierProblemList.add();
        }

        return userTierProblemList;

    }




}

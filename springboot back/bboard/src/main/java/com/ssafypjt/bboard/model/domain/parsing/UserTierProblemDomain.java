package com.ssafypjt.bboard.model.domain.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.enums.SACApiEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component

public class UserTierProblemDomain {

    public UserTierProblemDomain() {
    }

    public List<UserPageNoObjectDomain> makeUserPageNoObjectDomainList (List<User> users, Map<Integer, List<UserTier>> totalMap){
        Set<String> set = new HashSet<>();
        List<UserPageNoObjectDomain> userPageNoObjectDomainList = new ArrayList<>();
        for(User user : users){
            for(UserTier userTier : totalMap.get(user.getUserId())){
                if (set.add(userTier.getUserId() + " " + userTier.getPageNo())){
                    userPageNoObjectDomainList.add(new UserPageNoObjectDomain(user, userTier.getPageNo()));
                }
            }
        }
        return userPageNoObjectDomainList;

    }

    // 동기적으로 아이템 가져오기 (restTemplate 사용, 수정 필요)
    // problemDomain 코드 이용함

    public List<ProblemAndAlgoObjectDomain> makeTotalProblemAndAlgoList(Map<User, Map<Integer, List<ProblemAndAlgoObjectDomain>>> memoMap, Map<Integer, List<UserTier>> userTierMap){

        List<ProblemAndAlgoObjectDomain> totalProblemAndAlgoList = new ArrayList<>();
        for (User user: memoMap.keySet()) {
            List<UserTier> userTierList = userTierMap.get(user.getUserId()); // 유저당 userTier 데이터 저장된 맵
            int prevPage = 0;
            List<ProblemAndAlgoObjectDomain> problemListByPage = null;
            for (UserTier userTier : userTierList) {
                if(prevPage != userTier.getPageNo()){ // 새로 요청해야할 때만 요청하여 갱신
                    // 해당 유저의 페이지당 문제 정보 동기적으로 가져오기
                    problemListByPage = memoMap.get(user).get(userTier.getPageNo());
                    prevPage = userTier.getPageNo();
                }
                if (userTier.getProblemCount() != 0) {
                    ProblemAndAlgoObjectDomain problemAndAlgo = problemListByPage.get(userTier.getPageIdx());
                    problemAndAlgo.getProblem().setUserId(user.getUserId());
                    totalProblemAndAlgoList.add(problemAndAlgo);
                }
            }
        }
        return totalProblemAndAlgoList;
    }


}

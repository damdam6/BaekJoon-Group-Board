package com.ssafypjt.bboard.model.domain.solvedacAPI;

import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import org.springframework.stereotype.Component;

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

    public List<UserPageNoObjectDomain> makeUserPageNoObjectDomainList(User user, List<UserTier> userTiers){
        Set<String> set = new HashSet<>();
        List<UserPageNoObjectDomain> userPageNoObjectDomainList = new ArrayList<>();
            for(UserTier userTier : userTiers) {
                if (set.add(userTier.getUserId() + " " + userTier.getPageNo())) {
                    userPageNoObjectDomainList.add(new UserPageNoObjectDomain(user, userTier.getPageNo()));
                }
            }
        return userPageNoObjectDomainList;
    }

    // problemDomain 코드 이용함
    public List<ProblemAndAlgoObjectDomain> makeTotalProblemAndAlgoList(Map<User, Map<Integer, List<ProblemAndAlgoObjectDomain>>> memoMap, Map<Integer, List<UserTier>> userTierMap){

        List<ProblemAndAlgoObjectDomain> totalProblemAndAlgoList = new ArrayList<>();
        for (User user: memoMap.keySet()) {
            List<UserTier> userTierList = userTierMap.get(user.getUserId()); // 유저당 userTier 데이터 저장된 맵
            int prevPage = 0;
            List<ProblemAndAlgoObjectDomain> problemListByPage = null;
            for (UserTier userTier : userTierList) {
                if(prevPage != userTier.getPageNo()){
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

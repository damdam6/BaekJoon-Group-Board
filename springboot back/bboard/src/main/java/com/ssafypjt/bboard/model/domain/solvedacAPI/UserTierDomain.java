package com.ssafypjt.bboard.model.domain.solvedacAPI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.UserTier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class UserTierDomain {


    final int MAX_TIER = 30;
    final int NUMBER_OF_PAGES = 50;

    public List<UserTier> makeUserTierObject(List<UserTier> userTierList){

        // User 1개 당
        // 0 ~ 30 까지 총 31개의 Tier가 존재 가능한 것으로 보임
        int[] problemPrefixSum = new int[MAX_TIER+2];
        for (int i = MAX_TIER; i >= 0; i--) {
            UserTier now = userTierList.get(i);
            problemPrefixSum[i] = now.getProblemCount() +  problemPrefixSum[i+1];
            if (problemPrefixSum[i+1] == 0) continue;
            now.setPageNo(problemPrefixSum[i+1] / NUMBER_OF_PAGES + 1);
            now.setPageIdx(problemPrefixSum[i+1] % NUMBER_OF_PAGES == 0 ? 0 : problemPrefixSum[i+1] % NUMBER_OF_PAGES);
        }
        return userTierList;
    }

}

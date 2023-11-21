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

    private ObjectMapper mapper;

    final int MAX_TIER = 30;
    final int NUMBER_OF_PAGES = 50;

    @Autowired
    public UserTierDomain(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<UserTier> makeUserTierObject(List<UserTier> userTierList, Integer userId){

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


    // JsonNode를 List<T>로 변환하는 메서드
    private <T> List<T> convertJsonNodeToList(JsonNode jsonNode, Class<T> valueType) {
        List<T> resultList = new ArrayList<>();
        if (jsonNode.isArray()) {
            Iterator<JsonNode> elements = jsonNode.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                T convertedValue = mapper.convertValue(element, valueType);
                resultList.add(convertedValue);
            }
        }
        return resultList;
    }

}

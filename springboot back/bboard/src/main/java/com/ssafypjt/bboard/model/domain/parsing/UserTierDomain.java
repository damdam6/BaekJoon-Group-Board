package com.ssafypjt.bboard.model.domain.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTierDomain {

    @Autowired
    private ObjectMapper mapper;

    public UserTierDomain(ObjectMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
    }

//    public Problem makeUserTierObject(JsonNode nodeItem, User user) {
//        UserTier userTier = null;
//        try {
//            userTier = mapper.treeToValue(nodeItem, UserTier.class);
//            problem.setUserId(user.getUserId());
//        } catch (Exception e ) {
//            e.printStackTrace();
//        }
//        return problem;
//    }
}

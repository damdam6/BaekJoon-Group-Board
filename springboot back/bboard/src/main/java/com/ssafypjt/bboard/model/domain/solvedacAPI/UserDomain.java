package com.ssafypjt.bboard.model.domain.solvedacAPI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDomain {

    private ObjectMapper mapper;

    public List<User> userList = new ArrayList<>();

    @Autowired
    public UserDomain(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public User makeUserObject(JsonNode aNode) {
        List<ProblemAndAlgoObjectDomain> tmpList = new ArrayList<>();
        User user = mapper.convertValue(aNode, User.class);
        if (user.getImgUrl() == null) user.setImgUrl("");
        return user;
    }

}

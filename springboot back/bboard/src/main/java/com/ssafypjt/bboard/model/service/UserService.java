package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;

import java.util.List;

public interface UserService {

    // 유저 가져오기
    public User getUser(int id);

    public User getUserByName(String userName);

    // 유저 전부 가져오기
    public List<User> getAllUser();

    // 유저 등록
    // 유저 이름 확인해서 같은 유저 이름이 있으면 등록 안되게
    public int addUser(User user);

    public List<Integer> getGroupIdByUser(int userId);

    // 유저의 티어당 푼 문제수 정보
    public List<UserTier> getUserTier(int userId);

}

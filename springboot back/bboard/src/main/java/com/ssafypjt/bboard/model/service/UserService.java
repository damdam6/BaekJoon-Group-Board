package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.dto.User;

import java.util.List;

public interface UserService {

    // 유저 가져오기
    public User getUser(int id);

    // 유저 등록
    // 유저 이름 확인해서 같은 유저 이름이 있으면 등록 안되게
    public int addUser(User user);

    // 유저 DB에서 삭제 (생각해보니까 유저를 무한정 등록할 수도 있어서 필요한 기능인데 여기서 비번 확인이 필요한데..?)
    // 일단 배제
    public int removeUser(int userId);

    // 유저가 등록된 그룹들의 id를 반환
    public List<Integer> getGroupId(int userId);

    // 유저의 티어 문제를 카운트..?
    public int getUserTier(int userId);

}

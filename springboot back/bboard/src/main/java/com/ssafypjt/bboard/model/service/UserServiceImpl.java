package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.repository.TierProblemRepository;
import com.ssafypjt.bboard.model.repository.UserGroupRepository;
import com.ssafypjt.bboard.model.repository.UserRepository;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private UserGroupRepository userGroupRepository;
    private TierProblemRepository tierProblemRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository, TierProblemRepository tierProblemRepository) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.tierProblemRepository = tierProblemRepository;
    }

    @Override
    public User getUser(int userId) {
        return userRepository.selectUser(userId);
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.selectUserByName(userName);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.selectAllUser();
    }

    @Override
    public int addUser(User user) {
        if (userRepository.selectUserByName(user.getUserName()) == null){
            return userRepository.insertUser(user);
        }
        return 0;
    }

    @Override
    public List<Integer> getGroupIdByUser(int userId) {
        return userGroupRepository.selectGroupId(userId);
    }

    // 유저 티어 정보 로직 구성 필요
    @Override
    public List<UserTier> getUserTier(int userId) {
        return tierProblemRepository.selectUserTiers(userId);
    }


}

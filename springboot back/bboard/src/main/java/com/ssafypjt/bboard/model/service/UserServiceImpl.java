package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.repository.UserRepository;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(int id) {

        return userRepository.selectUser(id);
    }

    @Override
    public int addUser(User user) {
        if (userRepository.selectUserByName(user.getUserName()) == null){
            return userRepository.insertUser(user);
        }
        return 0;
    }

    @Override
    public int removeUser(int userId) {
        return 0;
    }

    @Override
    public List<Integer> getGroupId(int userId) {
        return null;
    }

    @Override
    public int getUserTier(int userId) {
        return 0;
    }
}

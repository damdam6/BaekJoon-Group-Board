package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.dto.Group;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.repository.GroupRepository;
import com.ssafypjt.bboard.model.repository.UserGroupRepository;
import com.ssafypjt.bboard.model.repository.UserRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private UserGroupRepository userGroupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, UserGroupRepository userGroupRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public Group getGroup(int groupId) {
        return groupRepository.selectGroup(groupId);
    }

    @Override
    public List<Group> getGroups(int userId) {
        List<Group> list = new ArrayList<>();
        for (Integer groupId : userGroupRepository.selectGroupId(userId)) {
            list.add(groupRepository.selectGroup(groupId));
        }
        return list;
    }

    @Override
    public int makeGroup(Group group) {
        return groupRepository.insertGroup(group);
    }

    @Override
    public int removeGroup(int groupId) {
        userGroupRepository.removeAllUserGroup(groupId);
        return groupRepository.deleteGroup(groupId);
    }

    public List<User> getUsers(int groupId){
        List<User> userList = new ArrayList<>();
        for(int userId : userGroupRepository.selectUserId(groupId)){
            userList.add(userRepository.selectUser(userId));
        }
        return userList;
    }

    @Override
    public int addUser(Group group, int userId) { // 유저 등록 & 유저-그룹 관계 등록
        int groupId = group.getId();
        if (groupId == 0) groupId = groupRepository.selectGroupByName(group.getGroupName());
        return userGroupRepository.insertUserGroup(userId, groupId);
    }

    @Override
    public int removeUser(int groupId, int userId) {
        return userGroupRepository.removeUserGroup(userId, groupId);
    }

    @Override
    public boolean adminValid(int id, String password) {
        return groupRepository.selectGroupByPassword(id, password) != null;
    }
}

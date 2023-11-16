package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.dto.Group;
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
        if (groupRepository.selectGroup(group.getId()) == null)
            return groupRepository.insertGroup(group);
        return -1;
    }

    @Override
    public int removeGroup(int groupId) {
        if (groupRepository.selectGroup(groupId) != null)
            return groupRepository.deleteGroup(groupId);
        return -1;
    }

    @Override
    public int addUser(int groupId, int userId) {
        if (userGroupRepository.selectUserId(userId) == null)
            return userGroupRepository.insertUserGroup(userId, groupId);
        return 0;
    }

    @Override
    public int removeUser(int groupId, int userId) {
        if (userGroupRepository.selectUserId(userId) != null)
            return userGroupRepository.removeUserGroup(userId, groupId);
        return 0;
    }

    @Override
    public boolean adminValid(int id, String password) {
        return groupRepository.selectGroupByPassword(id, password) != null;
    }
}

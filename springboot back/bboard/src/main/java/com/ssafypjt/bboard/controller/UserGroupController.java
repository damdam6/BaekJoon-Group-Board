package com.ssafypjt.bboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.Group;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.service.GroupService;
import com.ssafypjt.bboard.model.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-user")
public class UserGroupController {

    private UserService userService;
    private GroupService groupService;
    private ObjectMapper objectMapper = new ObjectMapper(); // 이거 나중에 util에서 bean 등록해서 사용할 것

    @Autowired
    private UserGroupController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    // 유저 입력 페이지

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        User user = userService.getUser(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userList = userService.getAllUser();
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user){
        int result = userService.addUser(user);
        // 유저가 실제 solved.ac 에 존재하는지 확인하는 로직 구현 필요

        switch (result){
            case 0 :
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            case -1 :
                return new ResponseEntity<Void>(HttpStatus.IM_USED);
            default:
                return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }

    // 그룹 선택 페이지
    @GetMapping("/group/{userId}") // 로그인된 유저의 그룹 정보 가져오기
    public ResponseEntity<?> getGroups(@PathVariable int userId){
        List<Group> list = groupService.getGroups(userId);
        if (list == null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Group>>(list, HttpStatus.OK);
    }

    @PostMapping("/group") // 그룹 등록
    public ResponseEntity<?> addGroup(@RequestBody Map<String, Object> requestMap){
        Group group = objectMapper.convertValue(requestMap.get("group"), Group.class);
        User user = objectMapper.convertValue(requestMap.get("user"), User.class);

        int result = groupService.makeGroup(group);
        if (result == 0) return new ResponseEntity<Void>(HttpStatus.IM_USED);
        groupService.addUser(group, user.getUserId());
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }

    @PostMapping("/group/leave") // 그룹 탈퇴
    public ResponseEntity<?> leaveGroup(@RequestBody Map<String, Object> requestMap){
        Group group = objectMapper.convertValue(requestMap.get("group"), Group.class);
        User user = objectMapper.convertValue(requestMap.get("user"), User.class);
        int result = groupService.removeUser(group.getId(), user.getUserId());
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }


    // 관리자 페이지 기능
    @PostMapping("/group/admin") // 관리자 권한 확인 (비밀번호로 확인)
    public ResponseEntity<Boolean> adminLogin(@RequestBody Group group){
        if (groupService.adminValid(group.getId(), group.getPassword())){
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/group/{groupId}") // 그룹 삭제
    public ResponseEntity<?> deleteGroup(@PathVariable int groupId){
        int result = groupService.removeGroup(groupId);
        if (result == 0) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Integer>(groupId, HttpStatus.OK);
    }

    @GetMapping("/group/get-user/{groupId}")
    public ResponseEntity<?> getUsers(@PathVariable int groupId){
        List<User> list = groupService.getUsers(groupId);
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }


    @PostMapping("/group/add-user")
    public ResponseEntity<?> addUser(@RequestBody Map<String, Object> requestMap){
        Group group = objectMapper.convertValue(requestMap.get("group"), Group.class);
        User user = objectMapper.convertValue(requestMap.get("user"), User.class);
        int result = groupService.addUser(group, user.getUserId());

        if (result == 0) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/group/remove-user")
    public ResponseEntity<?> removeUser(@RequestBody Map<String, Object> requestMap){
        Group group = objectMapper.convertValue(requestMap.get("groupId"), Group.class);
        User user = objectMapper.convertValue(requestMap.get("userId"), User.class);
        int result = groupService.removeUser(group.getId(), user.getUserId());

        if (result == 0) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}

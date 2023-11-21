package com.ssafypjt.bboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafypjt.bboard.model.domain.groupinfo.GroupDomain;
import com.ssafypjt.bboard.model.domain.groupinfo.UserAndGroupObjectDomain;
import com.ssafypjt.bboard.model.dto.*;
import com.ssafypjt.bboard.model.service.GroupService;
import com.ssafypjt.bboard.model.service.ProblemService;
import com.ssafypjt.bboard.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/main")
public class MainController {

    private final UserService userService;
    private final ProblemService problemService;
    private final GroupService groupService;
    private final ObjectMapper mapper;
    private final GroupDomain groupDomain;

    @Autowired
    public MainController(UserService userService, ProblemService problemService, GroupService groupService, ObjectMapper mapper, GroupDomain groupDomain){
        this.userService = userService;
        this.problemService = problemService;
        this.groupService = groupService;
        this.mapper = mapper;
        this.groupDomain = groupDomain;
    }

    // 그룹 page에서 Main page 진입시 그룹 정보 반환
    @PostMapping("/group")
    @Transactional
    public ResponseEntity<ObjectNode> getGroupInfo(@RequestBody Map<String, Object> requestMap){ // ObjectNode (JSON DATA)로 전송
        Group group = mapper.convertValue(requestMap.get("group"), Group.class);
        User user = mapper.convertValue(requestMap.get("user"), User.class);
        UserAndGroupObjectDomain userAndGroup = new UserAndGroupObjectDomain(user, group);
        List<User> userList = groupDomain.getUsers(userAndGroup); // 그룹 해당 유저 정보
        List<Problem> problemList = groupDomain.getProblems(userAndGroup, userList); // 그룹별 top 100개 문제 정보
        List<Problem> userTierProblemList = groupDomain.getUserTierProblems(userAndGroup, userList); // 그룹별 로그인된 유저에 해당하는 userTierProblem 정보
        List<RecomProblem> recomProblemList = groupDomain.getRecomProblems(userAndGroup); // 그룹별 recomProblem 정보
        List<ProblemAlgorithm> algorithmList = groupDomain.getAlgorithms(problemList, recomProblemList); // 그룹의 모든 문제의 알고리즘 정보

        ObjectNode responseJson = JsonNodeFactory.instance.objectNode();
        responseJson.set("users", mapper.valueToTree(userList));
        responseJson.set("problems", mapper.valueToTree(problemList));
        responseJson.set("userTierProblems", mapper.valueToTree((userTierProblemList)));
        responseJson.set("recomProblems", mapper.valueToTree(recomProblemList));
        responseJson.set("algorithms", mapper.valueToTree(algorithmList));
 
        return new ResponseEntity<ObjectNode>(responseJson, HttpStatus.OK);
    }






}

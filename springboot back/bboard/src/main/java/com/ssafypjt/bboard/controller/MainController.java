package com.ssafypjt.bboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafypjt.bboard.model.dto.*;
import com.ssafypjt.bboard.model.service.GroupService;
import com.ssafypjt.bboard.model.service.ProblemService;
import com.ssafypjt.bboard.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@Api(tags = {"개쩔고 멋진 죄다 짬뽕해놓은 좋지 않은 컨트롤러"})
@RequestMapping("/api/main")
public class MainController {

    private UserService userService;
    private ProblemService problemService;
    private GroupService groupService;
    private ObjectMapper mapper;
    @Autowired
    public MainController(UserService userService, ProblemService problemService, GroupService groupService, ObjectMapper mapper){
        this.userService = userService;
        this.problemService = problemService;
        this.groupService = groupService;
        this.mapper = mapper;
    }

    // 추가 구현 필요
    // 유저 정보 받아와서 그 유저에 해당하는 값만 가져오기
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ObjectNode> getGroupInfo(@PathVariable int groupId){ // ObjectNode (JSON DATA)로 전송
        List<User> userList = groupService.getUsers(groupId); // 그룹 해당 유저 정보
        List<Problem> top100ProblemList = problemService.getGroupProblems(groupId); // 그룹별 top 100개 문제 정보
        // 여기 로그인된 유저에 해당하는 값만 가져오도록 수정
        List<Problem> userTierProblemList = problemService.getGroupUserTierProblems(groupId); // 그룹별 userTierProblem 정보
        List<RecomProblem> recomProblemList = problemService.getGroupRecomProblems(groupId); // 그룹별 recomProblem 정보
        // 알고리즘 필요한거만 뽑아보자..
        List<ProblemAlgorithm> algorithmList = problemService.getAllAlgorithm(); // 모든 알고리즘 (나눠서 주려니 로직이 복잡하네 일단 보류)

        ObjectNode responseJson = JsonNodeFactory.instance.objectNode();
        responseJson.set("users", mapper.valueToTree(userList));
        responseJson.set("problems", mapper.valueToTree(top100ProblemList));
        responseJson.set("userTierProblems", mapper.valueToTree((userTierProblemList)));
        responseJson.set("recomProblems", mapper.valueToTree(recomProblemList));
        responseJson.set("algorithms", mapper.valueToTree(algorithmList));
 
        return new ResponseEntity<ObjectNode>(responseJson, HttpStatus.OK);
    }






}

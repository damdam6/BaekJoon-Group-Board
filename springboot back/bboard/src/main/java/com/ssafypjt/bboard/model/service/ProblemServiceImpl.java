package com.ssafypjt.bboard.model.service;

import ch.qos.logback.core.subst.Node;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafypjt.bboard.model.domain.ProblemAndAlgoObjectDomain;
import com.ssafypjt.bboard.model.domain.ProblemDomain;
import com.ssafypjt.bboard.model.dto.*;
import com.ssafypjt.bboard.model.repository.GroupRepository;
import com.ssafypjt.bboard.model.repository.ProblemAlgorithmRepository;
import com.ssafypjt.bboard.model.repository.ProblemRepository;
import com.ssafypjt.bboard.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;

@Service
public class ProblemServiceImpl implements ProblemService {

    private ProblemRepository problemRepository;
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    private ProblemAlgorithmRepository problemAlgorithmRepository;
    private ObjectMapper objectMapper = new ObjectMapper(); // API 가져올 때 필요한 object Mapper 공통으로 사용

    private ProblemDomain problemDomain;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        //this.restTemplate = restTemplate;
    }

    @Override
    public List<Problem> getAllProblems() {
        return null;
    }

    @Override
    public Problem getProblem(int id) {
        return null;
    }

    @Override
    public List<Problem> getTierProblems(int tier, int groupId) {
        return null;
    }

    @Override
    public List<UserTier> addUserTiers(List<User> userList) {
        return null;
    }

    @Override
    public UserTier getUserTier(User user) {
        return null;
    }

    @Override
    public List<UserTierProblem> getUserTierProblems(User user) {
        return null;
    }

    @Override
    public int addRecomProblem(RecomProblem recomProblem) {
        return 0;
    }

    @Override
    public RecomProblem getRecomProblem(int userId, int groupId) {
        return null;
    }

    @Override
    public List<RecomProblem> getGroupRecomProblems(int groupId) {
        return null;
    }

    @Override
    public List<RecomProblem> getAllRecomProblems() {
        return null;
    }


    @Override
    public List<String> getProblemAlgorithm(int problemNum) {
        return null;
    }
    @Scheduled(fixedRate = 50000)
    public void schedulTask() {
        System.out.println("test");
        List<String> tmp =  Arrays.asList("end24", "29tigerhg", "98cline", "ygj9605");
        List<User> list = new ArrayList<>();
        for(int i = 0; i<4;i++){
            User user = new User();
            user.setUserId(i);
            user.setUserName(tmp.get(i));
            list.add(user);
        }

        List<String> pageN = Arrays.asList("1", "2");
        resetProblems();
    }


    // 미완성
    @Override
    public void resetProblems() {
        // 기존 테이블 삭제
        problemRepository.deleteAll();
        problemRepository.deleteAll();

        // 전체 유저 목록
        List<User> userList = userRepository.selectAllUser();
        System.out.println("test");
        problemDomain.processUserList(userList);

        Collections.sort(problemDomain.proAndAlgoList, Collections.reverseOrder());

        for(int i=0;i < 100;i++){
            ProblemAndAlgoObjectDomain proAndAlgo = problemDomain.proAndAlgoList.get(i);
            System.out.println(proAndAlgo.toString());
            problemRepository.insertProblem(proAndAlgo.getProblem());
            problemAlgorithmRepository.insertAlgorithm(proAndAlgo.getProblemAlgorithm());
        }
    }

}

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

    //private ProblemDomain problemDomain;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, UserRepository userRepository, GroupRepository groupRepository, ProblemAlgorithmRepository problemAlgorithmRepository) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        //this.problemDomain = problemDomain;
        this.problemAlgorithmRepository = problemAlgorithmRepository;
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



    // 미완성
    @Override
    public void resetProblems(List<ProblemAndAlgoObjectDomain> list) {
        // 기존 테이블 삭제
        problemRepository.deleteAll();
        problemRepository.deleteAll();



        for(int i=0;i < 100;i++){
            ProblemAndAlgoObjectDomain proAndAlgo = list.get(i);
//            System.out.println(proAndAlgo.toString());
            problemAlgorithmRepository.insertAlgorithm(proAndAlgo.getProblemAlgorithm());
            problemRepository.insertProblem(proAndAlgo.getProblem());
        }
    }

}

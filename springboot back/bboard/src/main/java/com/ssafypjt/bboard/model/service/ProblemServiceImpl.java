package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.domain.parsing.FetchDomain;
import com.ssafypjt.bboard.model.domain.parsing.ProblemAndAlgoObjectDomain;
import com.ssafypjt.bboard.model.domain.parsing.ProblemDomain;
import com.ssafypjt.bboard.model.dto.*;
import com.ssafypjt.bboard.model.repository.GroupRepository;
import com.ssafypjt.bboard.model.repository.ProblemAlgorithmRepository;
import com.ssafypjt.bboard.model.repository.ProblemRepository;
import com.ssafypjt.bboard.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;

@Service
public class ProblemServiceImpl implements ProblemService {

    private ProblemRepository problemRepository;
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    private ProblemAlgorithmRepository problemAlgorithmRepository;

    private ProblemDomain problemDomain;
    private FetchDomain fetchDomain;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, UserRepository userRepository, GroupRepository groupRepository, ProblemAlgorithmRepository problemAlgorithmRepository, ProblemDomain problemDomain, FetchDomain fetchDomain) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.problemAlgorithmRepository = problemAlgorithmRepository;
        this.problemDomain = problemDomain;
        this.fetchDomain = fetchDomain;
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
    public List<Problem> getUserTierProblems(User user) {
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


}

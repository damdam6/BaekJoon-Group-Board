package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.domain.ReloadDomain;
import com.ssafypjt.bboard.model.domain.parsing.FetchDomain;
import com.ssafypjt.bboard.model.domain.parsing.ProblemDomain;
import com.ssafypjt.bboard.model.dto.*;
import com.ssafypjt.bboard.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProblemServiceImpl implements ProblemService {

    private ProblemRepository problemRepository;

    private ProblemAlgorithmRepository problemAlgorithmRepository;

    private RecomProblemRepository recomProblemRepository;
    private TierProblemRepository tierProblemRepository;
    private UserTierProblemRepository userTierProblemRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, ProblemAlgorithmRepository problemAlgorithmRepository, RecomProblemRepository recomProblemRepository, TierProblemRepository tierProblemRepository, UserTierProblemRepository userTierProblemRepository) {
        this.problemRepository = problemRepository;
        this.problemAlgorithmRepository = problemAlgorithmRepository;
        this.recomProblemRepository = recomProblemRepository;
        this.tierProblemRepository = tierProblemRepository;
        this.userTierProblemRepository = userTierProblemRepository;
    }

    @Override
    public List<Problem> getAllProblems() {
        return problemRepository.selectAllProblems();
    }

    @Override
    public Problem getProblem(int id) {
        return problemRepository.selectProblem(id);
    }

    @Override
    public List<UserTier> getAllUserTiers() {
        return tierProblemRepository.selectAllUserTiers();
    }

    @Override
    public List<UserTier> getUserTiers(User user) {
        return tierProblemRepository.selectUserTiers(user.getUserId());
    }


    @Override
    public List<Problem> getUserTierProblems(int userId) {
        return userTierProblemRepository.selectTierProblems(userId);
    }

    @Override
    public int addRecomProblem(RecomProblem recomProblem) {
        return recomProblemRepository.insertRecomProblem(recomProblem);
    }

    @Override
    public RecomProblem getRecomProblem(int userId, int groupId) {
        return recomProblemRepository.selectRecomProblem(userId, groupId);
    }

    @Override
    public List<RecomProblem> getGroupRecomProblems(int groupId) {
        return recomProblemRepository.selectGroupRecomProblems(groupId);
    }

    @Override
    public List<RecomProblem> getAllRecomProblems() {
        return recomProblemRepository.selectAllRecomProblems();
    }

    @Override
    public List<String> getProblemAlgorithm(int problemNum) {
        ProblemAlgorithm problemAlgorithm = problemAlgorithmRepository.selectAlgorithm(problemNum);
        if (problemAlgorithm == null) return null;
        return Arrays.asList(problemAlgorithm.getAlgorithm().split(" "));
    }



}

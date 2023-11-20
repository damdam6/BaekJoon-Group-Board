package com.ssafypjt.bboard.model.service;

import com.ssafypjt.bboard.model.dto.*;
import com.ssafypjt.bboard.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProblemServiceImpl implements ProblemService {

    private ProblemRepository problemRepository;
    private GroupService groupService;

    private ProblemAlgorithmRepository problemAlgorithmRepository;

    private RecomProblemRepository recomProblemRepository;
    private TierProblemRepository tierProblemRepository;
    private UserTierProblemRepository userTierProblemRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, GroupService groupService, ProblemAlgorithmRepository problemAlgorithmRepository, RecomProblemRepository recomProblemRepository, TierProblemRepository tierProblemRepository, UserTierProblemRepository userTierProblemRepository) {
        this.problemRepository = problemRepository;
        this.groupService = groupService;
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
    public int addRecomProblem(RecomProblem recomProblem) { // 그룹별로 10개가 초과되면 id가 빠른 순 (등록이 빠른 순) 으로 삭제된다.
        if (recomProblemRepository.selectGroupRecomProblems(recomProblem.getGroupId()).size() >= 10){
            recomProblemRepository.deleteFirstRecomProblem();
        }
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
    public List<ProblemAlgorithm> getAllAlgorithm() {
        return problemAlgorithmRepository.selectAllAlgorithm();
    }

    @Override
    public ProblemAlgorithm getProblemAlgorithm(int problemNum) {
        return problemAlgorithmRepository.selectAlgorithm(problemNum);
    }

    @Override
    public List<Problem> getGroupProblems(int groupId) {
        List<User> userList = groupService.getUsers(groupId);
        List<Problem> problemList = problemRepository.selectGroupProblem(userList);
        List<Problem> returnList = new ArrayList<>();

        // 100개 선정 로직
        Set<Integer> set = new HashSet<>();
        int idx = 0;
        while (set.size() <= 100) {
            if (idx >= problemList.size()) break;
            Problem problem = problemList.get(idx++);
            if (set.add(problem.getProblemNum())) {
                if (set.size() > 100) break;
            }
            returnList.add(problem);
        }

        return returnList;
    }


    @Override
    public List<Problem> getGroupUserTierProblems(int groupId) {
        List<User> userList = groupService.getUsers(groupId);
        return userTierProblemRepository.selectGroupTierProblem(userList);
    }


}

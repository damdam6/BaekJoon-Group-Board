package com.ssafypjt.bboard.model.domain.groupinfo;

import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import com.ssafypjt.bboard.model.dto.RecomProblem;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
public class GroupDomain {

    private final GroupRepository groupRepository;
    private final ProblemAlgorithmRepository problemAlgorithmRepository;
    private final ProblemRepository problemRepository;
    private final RecomProblemRepository recomProblemRepository;
    private final TierProblemRepository tierProblemRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final UserTierProblemRepository userTierProblemRepository;

    @Autowired
    public GroupDomain(GroupRepository groupRepository, ProblemAlgorithmRepository problemAlgorithmRepository, ProblemRepository problemRepository, RecomProblemRepository recomProblemRepository, TierProblemRepository tierProblemRepository, UserGroupRepository userGroupRepository, UserRepository userRepository, UserTierProblemRepository userTierProblemRepository) {
        this.groupRepository = groupRepository;
        this.problemAlgorithmRepository = problemAlgorithmRepository;
        this.problemRepository = problemRepository;
        this.recomProblemRepository = recomProblemRepository;
        this.tierProblemRepository = tierProblemRepository;
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
        this.userTierProblemRepository = userTierProblemRepository;
    }

    public List<User> getUsers(UserAndGroupObjectDomain userAndGroup){
        List<User> userList = new ArrayList<>();
        for(int userId : userGroupRepository.selectUserId(userAndGroup.getGroup().getId())){
            userList.add(userRepository.selectUser(userId));
        }
        return userList;
    }

    public List<Problem> getProblems(UserAndGroupObjectDomain userAndGroup, List<User> userList){
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

    // 로그인된 유저와 관련된 문제만 선정
    // 로그인된 유저 레벨과 5차이나는 문제 가져오기
    // 다 주고 프론트에서 랜덤으로 추천해주는 것도 좋을듯?!
    public List<Problem> getUserTierProblems(UserAndGroupObjectDomain userAndGroup, List<User> userList){
        List<Problem> returnList = new ArrayList<>();
        for (Problem problem: userTierProblemRepository.selectGroupTierProblem(userList)) {
            if (Math.abs(problem.getTier() - userAndGroup.getUser().getTier()) <= 5) returnList.add(problem);
        }
        return returnList;
    }

    public List<RecomProblem> getRecomProblems(UserAndGroupObjectDomain userAndGroup){
        return recomProblemRepository.selectGroupRecomProblems(userAndGroup.getGroup().getId());
    }

    // 그룹의 모든 문제의 알고리즘 정보 선정
    // user_group과 problem_algorithm 테이블을 연동하기 위해서는 join을 3번해야해서 모든 알고리즘을 가져오고 이진 탐색을 사용하였다.
    public List<ProblemAlgorithm> getAlgorithms(List<Problem> top100problemList, List<RecomProblem> recomProblemList){
        List<ProblemAlgorithm> algorithms = problemAlgorithmRepository.selectAllAlgorithm(); // ASC
        int[] algoProblemNum = new int[algorithms.size()];
        for (int i = 0; i< algorithms.size(); i++){
            algoProblemNum[i] = algorithms.get(i).getProblemNum();
        }

        // BinarySearch
        List<ProblemAlgorithm> returnList = new ArrayList<>();
        for (Problem problem : top100problemList){
            returnList.add(algorithms.get(Arrays.binarySearch(algoProblemNum, problem.getProblemNum())));
        }
        for (RecomProblem recomProblem : recomProblemList){
            returnList.add(algorithms.get(Arrays.binarySearch(algoProblemNum, recomProblem.getProblemNum())));
        }

        return returnList;
    }



}

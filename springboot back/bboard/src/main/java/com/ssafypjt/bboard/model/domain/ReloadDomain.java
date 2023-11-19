package com.ssafypjt.bboard.model.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.domain.parsing.*;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.enums.SACApiEnum;
import com.ssafypjt.bboard.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;

@Component
public class ReloadDomain {

    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final ProblemAlgorithmRepository problemAlgorithmRepository;
    private final ProblemDomain problemDomain;
    private final UserDomain userDomain;
    private final FetchDomain fetchDomain;
    private final UserTierDomain userTierDomain;
    private final UserTierProblemRepository userTierProblemRepository;
    private final TierProblemRepository tierProblemRepository;
    private final UserTierProblemDomain userTierProblemDomain;

    @Autowired
    public ReloadDomain(ProblemRepository problemRepository, UserRepository userRepository, ProblemAlgorithmRepository problemAlgorithmRepository, ProblemDomain problemDomain, UserDomain userDomain, FetchDomain fetchDomain, UserTierDomain userTierDomain, UserTierProblemRepository userTierProblemRepository, TierProblemRepository tierProblemRepository, UserTierProblemDomain userTierProblemDomain) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.problemAlgorithmRepository = problemAlgorithmRepository;
        this.problemDomain = problemDomain;
        this.userDomain = userDomain;
        this.fetchDomain = fetchDomain;
        this.userTierDomain = userTierDomain;
        this.userTierProblemRepository = userTierProblemRepository;
        this.tierProblemRepository = tierProblemRepository;
        this.userTierProblemDomain = userTierProblemDomain;
    }


    @Scheduled(fixedRate = 1000000)
    public void schedulTask() {
        //유저 목록을 사용한 상위 문제 100개 가져오기
        List<User> users = userRepository.selectAllUser();
        processProblem(users);
        //유저 정보 모두 받아오기
        processUser(users);
        //유저의 티어별 문제 갯수 받아오기
        processUserTier(users);

    }

    public void processProblem(List<User> users) {
        synchronized (problemDomain.proAndAlgoList) {
            problemDomain.proAndAlgoList.clear();
            Flux.fromIterable(users)
                    .delayElements(Duration.ofMillis(1))
                    .flatMap(user ->
                            fetchDomain.fetchOneQueryData(
                                            SACApiEnum.PROBLEMANDALGO.getPath(),
                                            SACApiEnum.PROBLEMANDALGO.getQuery(user.getUserName())
                                    )
                                    .doOnNext(data ->
                                            problemDomain.makeProblemAndAlgoDomainObject(data, user, "problemAndAlgo")
                                    )
                    ).then()
                    .subscribe(
                            null, // onNext 처리는 필요 없음
                            Throwable::printStackTrace, // 에러 처리
                            () -> {
                                this.resetProblems(problemDomain.proAndAlgoList);
                            } // 완료 처리
                    );
        }
    }

    // 프라블럼 리셋
    public void resetProblems(List<ProblemAndAlgoObjectDomain> list) {
        // 기존 테이블 삭제
        problemRepository.deleteAll();
        problemAlgorithmRepository.deleteAll();

        int idx = 0;
        Set<Integer> set = new HashSet<>();
        while (set.size() < 100) {
            if (idx >= list.size()) break;
            ProblemAndAlgoObjectDomain proAndAlgo = list.get(idx++);
            try {
                if (set.add(proAndAlgo.getProblem().getProblemNum()) && set.size() > 100) break;
                problemAlgorithmRepository.insertAlgorithm(proAndAlgo.getProblemAlgorithm());
                problemRepository.insertProblem(proAndAlgo.getProblem());
            } catch (DuplicateKeyException e) {
            }
        }
        System.out.println("problem good");
    }


    public void processUser(List<User> users) {
        synchronized (userDomain.userList) {
            userDomain.userList.clear();
            Flux.fromIterable(users)
                    .delayElements(Duration.ofMillis(1))
                    .flatMap(user ->
                            fetchDomain.fetchOneQueryData(
                                            SACApiEnum.USER.getPath(),
                                            SACApiEnum.USER.getQuery(user.getUserName())
                                    )
                                    .doOnNext(data -> {
                                                User newUser = userDomain.makeUserObject(data);
                                                updateUsers(newUser);
                                            }

                                    )
                    )
                    .subscribe(
                            null, // onNext 처리는 필요 없음
                            Throwable::printStackTrace, // 에러 처리// 완료 처리
                            () -> System.out.println("user good")
                    );
        }
    }

    public void updateUsers(User user) {
        userRepository.updateUser(user);
    }

    public void processUserTier(List<User> users) {
        Map<String, List<UserTier>> totalMap = new HashMap<>();
        // 수정
        synchronized (totalMap) {
            Flux.fromIterable(users)
                    .delayElements(Duration.ofMillis(1))
                    .flatMap(user ->
                            fetchDomain.fetchOneQueryData(
                                            SACApiEnum.TIER.getPath(),
                                            SACApiEnum.TIER.getQuery(user.getUserName())
                                    )
                                    .doOnNext(data -> {
                                                totalMap.putIfAbsent(user.getUserName(), userTierDomain.makeUserTierObject(data, user));
                                            }
                                    )
                    ).then()
                    .subscribe(
                            null, // onNext 처리는 필요 없음
                            Throwable::printStackTrace, // 에러 처리
                            () -> {
                                resetUserTier(totalMap);
                                processUserTierProblem(users, totalMap);
                            } // 완료 처리
                    );
        }
    }

    public void resetUserTier(Map<String, List<UserTier>> totalMap) {
        tierProblemRepository.deleteAll();
        for (List<UserTier> userTierList : totalMap.values()) {
            for (UserTier userTier : userTierList) {
                tierProblemRepository.insertUserTier(userTier);
            }
        }
    }

    // 동기적으로 작동하는 코드
    public void processUserTierProblem(List<User> users, Map<String, List<UserTier>> totalMap) {
        List<Problem> totalProblemList = new ArrayList<>();
        for (User user: users) {
            List<UserTier> userTierList = totalMap.get(user.getUserName());
            int prevPage = 0;
            List<Problem> problemListByPage = null;
            for (UserTier userTier:userTierList) {
                if(prevPage != userTier.getPageNo()){ // 새로 요청해야할 때만 요청하여 갱신
                    // 해당 유저의 페이지당 문제 정보 동기적으로 가져오기
                    problemListByPage = userTierProblemDomain.syncMakeUserTierProblem(userTier, user);
                    prevPage = userTier.getPageNo();
                }
                totalProblemList.add(problemListByPage.get(userTier.getPageIdx()));
            }
        }
        resetUserTierProblems(totalProblemList);

    }
    public void resetUserTierProblems(List<Problem> totalProblemList) {
        userTierProblemRepository.deleteAll();
        for (Problem problem:totalProblemList) {
            userTierProblemRepository.insertTierProblem(problem);
        }
    }

}

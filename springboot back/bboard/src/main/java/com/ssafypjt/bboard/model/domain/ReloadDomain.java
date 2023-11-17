package com.ssafypjt.bboard.model.domain;

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
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;

@Component
public class ReloadDomain {

    private ProblemRepository problemRepository;
    private UserRepository userRepository;
    private ProblemAlgorithmRepository problemAlgorithmRepository;
    private ProblemDomain problemDomain;
    private UserDomain userDomain;
    private FetchDomain fetchDomain;
    private UserTierDomain userTierDomain;
    private UserTierProblemRepository userTierProblemRepository;
    private TierProblemRepository tierProblemRepository;
    private UserTierProblemDomain userTierProblemDomain;

    private final int MAX_TIER = 30;

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

    @Autowired


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
                                insertUserTier(totalMap);
                                processUserTierProblem(users, totalMap);
                            } // 완료 처리
                    );
        }
    }

    public void insertUserTier(Map<String, List<UserTier>> totalMap) {
        for (List<UserTier> userTierList : totalMap.values()) {
            for (UserTier userTier : userTierList) {
                tierProblemRepository.insertUserTier(userTier);
            }
        }
    }

    // 여기는 동기화해서 짜는 로직이 필요할 듯, UserTierProblem 관련 코드 미완성
    public void processUserTierProblem(List<User> users, Map<String, List<UserTier>> totalMap) {
        // 수정

        List<Problem> totalProblemList = new ArrayList<>();

        synchronized (totalProblemList) {
            Flux.fromIterable(users)
                    .delayElements(Duration.ofMillis(1))
                    .flatMap(user ->
                            fetchDomain.fetchOneQueryData(
                                            SACApiEnum.USERTIERPROBLEM.getPath(),
                                            SACApiEnum.USERTIERPROBLEM.getQuery(user.getUserName())
                                    )
                                    .doOnNext(data -> {
                                        totalProblemList.addAll(userTierProblemDomain.makeUserTierProblemObject(data, user, totalMap.get(user.getUserName())));
                                            }

                                    )
                    ).then()
                    .subscribe(
                            null, // onNext 처리는 필요 없음
                            Throwable::printStackTrace, // 에러 처리
                            () -> {
                                resetUserTierProblems(totalProblemList);
                            } // 완료 처리
                    );
        }

    }


    public void resetUserTierProblems(List<Problem> totalProblemList) {
        userTierProblemRepository.deleteAll();

    }
}

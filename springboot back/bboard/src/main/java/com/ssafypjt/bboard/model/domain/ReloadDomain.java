package com.ssafypjt.bboard.model.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafypjt.bboard.model.domain.solvedacAPI.*;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.enums.SACApiEnum;
import com.ssafypjt.bboard.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    private final RecomProblemRepository recomProblemRepository;

    @Autowired
    public ReloadDomain(ProblemRepository problemRepository, UserRepository userRepository, ProblemAlgorithmRepository problemAlgorithmRepository, ProblemDomain problemDomain, UserDomain userDomain, FetchDomain fetchDomain, UserTierDomain userTierDomain, UserTierProblemRepository userTierProblemRepository, TierProblemRepository tierProblemRepository, UserTierProblemDomain userTierProblemDomain, RecomProblemRepository recomProblemRepository) {
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
        this.recomProblemRepository = recomProblemRepository;
    }

    @Scheduled(fixedRate = 1000000)
    @Transactional
    public void schedulTask() {
        //유저 정보 업데이트
        List<User> users = userRepository.selectAllUser();
        processUser(users);

        //유저 목록을 사용한 상위 문제 100개 가져오기
        users = userRepository.selectAllUser();
        processProblem(users);

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
                                resetProblems(problemDomain.proAndAlgoList);
                            } // 완료 처리
                    );
        }
    }

    // 프라블럼 리셋
    public void resetProblems(List<ProblemAndAlgoObjectDomain> list) {
        // 기존 테이블 삭제
        problemRepository.deleteAll();
        userTierProblemRepository.deleteAll(); // 티어별 문제도 삭제해야 알고리즘 삭제 가능
        problemAlgorithmRepository.deleteAll();
        Collections.sort(list);
        problemAlgorithmRepository.insertAlgorithms(list);
        problemRepository.insertProblems(list);

        System.out.println("problem good");
    }


    // 유저 정보 업데이트
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
        Map<Integer, List<UserTier>> totalMap = new HashMap<>();
        for (User user : users) {
            totalMap.put(user.getUserId(), new ArrayList<>());
        }
        // 수정
        synchronized (totalMap) {
            Flux.fromIterable(users)
                    .delayElements(Duration.ofMillis(1))
                    .flatMap(user ->
                            fetchDomain.fetchOneQueryDataUserTIer(
                                            SACApiEnum.TIER.getPath(),
                                            SACApiEnum.TIER.getQuery(user.getUserName())
                                    )
                                    .doOnNext(data -> {
                                                data.setUserId(user.getUserId());
                                            }
                                    )
                    )
                    .subscribe(
                            data -> {
                                totalMap.get(data.getUserId()).add(data);
                            },
                            Throwable::printStackTrace, // 에러 처리
                            () -> {
                                for (Integer userId : totalMap.keySet()) {
                                    List<UserTier> userTierList = totalMap.get(userId);
                                    userTierDomain.makeUserTierObject(userTierList, userId);
                                }
                                resetUserTier(totalMap);
                                System.out.println("tier good");
                                processUserTierProblem(users, totalMap);
                            } // 완료 처리
                    );
        }
    }

    public void resetUserTier(Map<Integer, List<UserTier>> totalMap) {
        tierProblemRepository.deleteAll();
        for (List<UserTier> userTierList : totalMap.values()) {
            tierProblemRepository.insertUserTiers(userTierList);
        }
    }

    // 동기적으로 작동하는 코드 > 비동기로 바꿔야
    // problemDomain 코드 재시용
    public void processUserTierProblem(List<User> users, Map<Integer, List<UserTier>> totalMap) {
        Long cur = System.currentTimeMillis();
        List<UserPageNoObjectDomain> userPageNoObjectDomainList = userTierProblemDomain.makeUserPageNoObjectDomainList(users, totalMap);
        Map<User, Map<Integer, List<ProblemAndAlgoObjectDomain>>> memoMap = new HashMap<>();
        Integer a = 0;

        Flux.fromIterable(userPageNoObjectDomainList)
                .delayElements(Duration.ofMillis(1))
                .flatMap(userPageNoObjectDomain ->
                    fetchDomain.fetchOneQueryData(
                                    SACApiEnum.USERTIERPROBLEM.getPath(),
                                    SACApiEnum.USERTIERPROBLEM.getQuery(userPageNoObjectDomain.getUser().getUserName(), userPageNoObjectDomain.getPageNo())
                            )
                            .doOnNext(data -> {
                                        User user = userPageNoObjectDomain.getUser();
                                        int pageNo = userPageNoObjectDomain.getPageNo();
                                        List<ProblemAndAlgoObjectDomain> list = problemDomain.makeProblemAndAlgoDomainList(data.path("items"), userPageNoObjectDomain.getUser());
                                        synchronized (memoMap) {
                                            memoMap.putIfAbsent(user, new HashMap<>());
                                            memoMap.get(user).putIfAbsent(pageNo, list);
                                        }
                                    }
                            )

                )
                .subscribe(
                        (data) -> {test(data);}, // onNext 처리는 필요 없음
                        Throwable::printStackTrace, // 에러 처리
                        () -> {
                            List<ProblemAndAlgoObjectDomain> totalProblemAndAlgoList = userTierProblemDomain.makeTotalProblemAndAlgoList(memoMap, totalMap);
                            Long cur2 = System.currentTimeMillis();
                            System.out.println(cur2 - cur);
                            resetUserTierProblems(totalProblemAndAlgoList);
                            System.out.println("tier problem good");
                        } // 완료 처리
                );
    }

    public void test(JsonNode a){
//        System.out.println(a);
    }



    public void resetUserTierProblems(List<ProblemAndAlgoObjectDomain> list) {
        userTierProblemRepository.deleteAll();
        problemAlgorithmRepository.insertAlgorithms(list);
        userTierProblemRepository.insertTierProblems(list);
    }

}

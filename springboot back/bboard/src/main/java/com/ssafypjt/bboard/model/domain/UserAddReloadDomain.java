package com.ssafypjt.bboard.model.domain;

import com.ssafypjt.bboard.model.domain.solvedacAPI.*;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.enums.SACApiEnum;
import com.ssafypjt.bboard.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

@Component
public class UserAddReloadDomain {

    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final ProblemAlgorithmRepository problemAlgorithmRepository;
    private final ProblemDomain problemDomain;
    private final UserDomain userDomain;
    private final ReloadDomain reloadDomain;
    private final FetchDomain fetchDomain;
    private final UserTierDomain userTierDomain;
    private final UserTierProblemRepository userTierProblemRepository;
    private final TierProblemRepository tierProblemRepository;
    private final UserTierProblemDomain userTierProblemDomain;
    private final RecomProblemRepository recomProblemRepository;

    @Autowired
    public UserAddReloadDomain(ProblemRepository problemRepository, UserRepository userRepository, ProblemAlgorithmRepository problemAlgorithmRepository, ProblemDomain problemDomain, UserDomain userDomain, ReloadDomain reloadDomain, FetchDomain fetchDomain, UserTierDomain userTierDomain, UserTierProblemRepository userTierProblemRepository, TierProblemRepository tierProblemRepository, UserTierProblemDomain userTierProblemDomain, RecomProblemRepository recomProblemRepository) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.problemAlgorithmRepository = problemAlgorithmRepository;
        this.problemDomain = problemDomain;
        this.userDomain = userDomain;
        this.reloadDomain = reloadDomain;
        this.fetchDomain = fetchDomain;
        this.userTierDomain = userTierDomain;
        this.userTierProblemRepository = userTierProblemRepository;
        this.tierProblemRepository = tierProblemRepository;
        this.userTierProblemDomain = userTierProblemDomain;
        this.recomProblemRepository = recomProblemRepository;
    }


    public void userAddTask(String userName) {
        processUser(userName);

    }

    @Transactional
    private void processUser(String userName) {
        Map<String, User> map = new HashMap<>();
        var mono = Mono.defer(() ->
                fetchDomain.fetchOneQueryDataMono(
                                SACApiEnum.USER.getPath(),
                                SACApiEnum.USER.getQuery(userName)
                        )
                        .doOnNext(data -> {
                            map.put("user", userDomain.makeUserObject(data));
                        })
        );

        mono.subscribe(
                user -> {
                    System.out.println(map.get("user"));
                }, Throwable::printStackTrace,
                () -> {
                    User user = map.get("user");
                    if (user != null) {
                        insertUser(user);
                        User newUser = userRepository.selectUserByName(userName);
                        System.out.println("ADD USER good");
                        processProblem(newUser);
                        processUserTier(newUser);
                    }
                }
        );

    }

    private void insertUser(User user) {
        userRepository.insertUser(user);
    }

    private void processProblem(User user) {
        List<ProblemAndAlgoObjectDomain> list = new ArrayList<>();
        var mono = Mono.defer(() ->
                fetchDomain.fetchOneQueryDataMono(
                                SACApiEnum.PROBLEMANDALGO.getPath(),
                                SACApiEnum.PROBLEMANDALGO.getQuery(user.getUserName())
                        )
                        .doOnNext(data ->
                                list.addAll(problemDomain.makeProblemAndAlgoDomainObjectMono(data, user))
                        ));

        mono.subscribe(
                null, // onNext 처리는 필요 없음
                Throwable::printStackTrace, // 에러 처리
                () -> {
                    resetProblems(list);
                } // 완료 처리
        );
    }

    private void resetProblems(List<ProblemAndAlgoObjectDomain> list) {
        Collections.sort(list);
        System.out.println(list.size());
        System.out.println(list);
        problemAlgorithmRepository.insertAlgorithms(list);
        problemRepository.insertProblems(list);

        System.out.println("ADD problem good");
    }

    private void processUserTier(User user) {
        Map<Integer, List<UserTier>> totalMap = new HashMap<>();
        totalMap.put(user.getUserId(), new ArrayList<>());

        var mono = Flux.defer(() ->
                        fetchDomain.fetchOneQueryDataUserTier(
                                        SACApiEnum.TIER.getPath(),
                                        SACApiEnum.TIER.getQuery(user.getUserName())
                                )
                                .doOnNext(data -> {
                                            data.setUserId(user.getUserId());
                                        }
                                )
                );

        mono.subscribe(
                data -> {
                    totalMap.get(data.getUserId()).add(data);
                }, Throwable::printStackTrace,
                () -> {
                    userTierDomain.makeUserTierObject(totalMap.get(user.getUserId()), user.getUserId());
                    resetUserTier(totalMap.get(user.getUserId()));
                    System.out.println("ADD tier good");
                    processUserTierProblem(user, totalMap);
                }
        );

    }

    private void resetUserTier(List<UserTier> list) {
        tierProblemRepository.insertUserTiers(list);
    }

    private void processUserTierProblem(User user, Map<Integer, List<UserTier>> totalMap) {
        Long cur = System.currentTimeMillis();
        List<UserPageNoObjectDomain> userPageNoObjectDomainList = userTierProblemDomain.makeUserPageNoObjectDomainList(user, totalMap.get(user.getUserId()));
        Map<User, Map<Integer, List<ProblemAndAlgoObjectDomain>>> memoMap = new HashMap<>();

        Flux.fromIterable(userPageNoObjectDomainList)
                .delayElements(Duration.ofMillis(1))
                .flatMap(userPageNoObjectDomain ->
                        fetchDomain.fetchOneQueryData(
                                        SACApiEnum.USERTIERPROBLEM.getPath(),
                                        SACApiEnum.USERTIERPROBLEM.getQuery(userPageNoObjectDomain.getUser().getUserName(), userPageNoObjectDomain.getPageNo())
                                )
                                .doOnNext(data -> {
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
                        null, // onNext 처리는 필요 없음
                        Throwable::printStackTrace, // 에러 처리
                        () -> {
                            List<ProblemAndAlgoObjectDomain> totalProblemAndAlgoList = userTierProblemDomain.makeTotalProblemAndAlgoList(memoMap, totalMap);
                            Long cur2 = System.currentTimeMillis();
                            System.out.println(cur2 - cur);
                            resetUserTierProblems(totalProblemAndAlgoList);
                            System.out.println("ADD tier problem good");
                        } // 완료 처리
                );

    }

    private void resetUserTierProblems(List<ProblemAndAlgoObjectDomain> list) {
        problemAlgorithmRepository.insertAlgorithms(list);
        userTierProblemRepository.insertTierProblems(list);
    }


}

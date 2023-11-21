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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public void userAddTask(User user) {
        //유저 정보 추가, 유저 1명만
        // 유저가 성공적으로 등록되어야만
        if (processUser(user)) {
            //유저 목록을 사용한 상위 문제 100개 가져오기
            List<User> users = userRepository.selectAllUser();
            reloadDomain.processProblem(users); // 코드 재사용
            //유저의 티어별 문제 갯수 받아오기
            processUserTier(user);
        }
    }


    private boolean processUser(User user) {
        Map<String, User> map = new HashMap<>();
        Mono.fromCallable(() ->
                        fetchDomain.fetchOneQueryData(
                                        SACApiEnum.USER.getPath(),
                                        SACApiEnum.USER.getQuery(user.getUserName())
                                )
                                .doOnNext(data -> {
                                            map.putIfAbsent("user", userDomain.makeUserObject(data));
                                        }
                                )
                )
                .subscribe(
                        null, // onNext 처리는 필요 없음
                        Throwable::printStackTrace, // 에러 처리
                        () -> {
                            if (map.get("user") != null) {
                                insertUser(map.get("user"));
                                System.out.println("ADD USER good");
                            }
                        }
                );
        return map.get("user") != null;
    }

    private void insertUser(User user) {
        userRepository.insertUser(user);
    }

    private void processUserTier(User user) {
        Map<Integer, List<UserTier>> totalMap = new HashMap<>();
        totalMap.put(user.getUserId(), new ArrayList<>());
        Mono.fromCallable(() ->
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
                        null,
                        Throwable::printStackTrace, // 에러 처리
                        () -> {
                            userTierDomain.makeUserTierObject(totalMap.get(user.getUserId()), user.getUserId());
                            resetUserTier(totalMap.get(user.getUserId()));
                            System.out.println("tier good");
                            processUserTierProblem(user, totalMap);
                        } // 완료 처리
                );

    }

    private void resetUserTier(List<UserTier> list){
        tierProblemRepository.insertUserTiers(list);
    }

    private void processUserTierProblem(User user, Map<Integer, List<UserTier>> totalMap){
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
                            System.out.println("tier problem good");
                        } // 완료 처리
                );
    }

    private void resetUserTierProblems(List<ProblemAndAlgoObjectDomain> list){
        problemAlgorithmRepository.insertAlgorithms(list);
        userTierProblemRepository.insertTierProblems(list);
    }


}

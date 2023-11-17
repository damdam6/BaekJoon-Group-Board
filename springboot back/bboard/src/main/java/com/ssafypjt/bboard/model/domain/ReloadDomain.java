package com.ssafypjt.bboard.model.domain;

import com.ssafypjt.bboard.model.domain.parsing.FetchDomain;
import com.ssafypjt.bboard.model.domain.parsing.ProblemAndAlgoObjectDomain;
import com.ssafypjt.bboard.model.domain.parsing.ProblemDomain;
import com.ssafypjt.bboard.model.domain.parsing.UserDomain;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.enums.SACApiEnum;
import com.ssafypjt.bboard.model.repository.GroupRepository;
import com.ssafypjt.bboard.model.repository.ProblemAlgorithmRepository;
import com.ssafypjt.bboard.model.repository.ProblemRepository;
import com.ssafypjt.bboard.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ReloadDomain {

    private ProblemRepository problemRepository;
    private UserRepository userRepository;
    private ProblemAlgorithmRepository problemAlgorithmRepository;
    private ProblemDomain problemDomain;
    private UserDomain userDomain;
    private FetchDomain fetchDomain;

    public ReloadDomain(ProblemRepository problemRepository, UserRepository userRepository, ProblemAlgorithmRepository problemAlgorithmRepository, ProblemDomain problemDomain, UserDomain userDomain, FetchDomain fetchDomain) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.problemAlgorithmRepository = problemAlgorithmRepository;
        this.problemDomain = problemDomain;
        this.userDomain = userDomain;
        this.fetchDomain = fetchDomain;
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
                )
                .subscribe(
                        null, // onNext 처리는 필요 없음
                        Throwable::printStackTrace, // 에러 처리
                        () -> {
                            this.resetProblems(problemDomain.proAndAlgoList);
                        } // 완료 처리
                );
    }

    // 프라블럼 리셋
    public void resetProblems(List<ProblemAndAlgoObjectDomain> list) {
        // 기존 테이블 삭제
        problemRepository.deleteAll();
        problemAlgorithmRepository.deleteAll();

        int idx = 0;
        Set<Integer> set = new HashSet<>();
        while(set.size() < 100){
            if(idx >= list.size())break;
            ProblemAndAlgoObjectDomain proAndAlgo = list.get(idx++);
            try{
                if (set.add(proAndAlgo.getProblem().getProblemNum()) && set.size() > 100) break;
                problemAlgorithmRepository.insertAlgorithm(proAndAlgo.getProblemAlgorithm());
                problemRepository.insertProblem(proAndAlgo.getProblem());
            }catch (DuplicateKeyException e){
            }
        }
        System.out.println("problem good");
    }


    public void processUser(List<User> users){
        userDomain.userList.clear();
        Flux.fromIterable(users)
                .delayElements(Duration.ofMillis(1))
                .flatMap(user ->
                        fetchDomain.fetchOneQueryData(
                                        SACApiEnum.USER.getPath(),
                                        SACApiEnum.USER.getQuery(user.getUserName())
                                )
                                .doOnNext(data ->
                                        userDomain.makeUserObject(data)
                                )
                )
                .subscribe(
                        null, // onNext 처리는 필요 없음
                        Throwable::printStackTrace, // 에러 처리
                        () -> {
                            this.updateUsers(userDomain.userList);
                        } // 완료 처리
                );
    };

    public void updateUsers(List<User> userList){
        for (User user: userList) {
            userRepository.updateUser(user);
        }
        System.out.println("user good");
    }

    public void processUserTier(List<User> users){



    };
}

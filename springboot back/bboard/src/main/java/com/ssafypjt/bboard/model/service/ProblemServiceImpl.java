package com.ssafypjt.bboard.model.service;

import ch.qos.logback.core.subst.Node;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafypjt.bboard.model.domain.FetchDomain;
import com.ssafypjt.bboard.model.domain.ProblemAndAlgoObjectDomain;
import com.ssafypjt.bboard.model.domain.ProblemDomain;
import com.ssafypjt.bboard.model.dto.*;
import com.ssafypjt.bboard.model.repository.GroupRepository;
import com.ssafypjt.bboard.model.repository.ProblemAlgorithmRepository;
import com.ssafypjt.bboard.model.repository.ProblemRepository;
import com.ssafypjt.bboard.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
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


    @Scheduled(fixedRate = 1000000)
    public void schedulTask() {
        //유저 목록을 사용한 상위 문제 100개 가져오기
        List<User> userList = userRepository.selectAllUser();
        processProblemList(userList);

        //유저 정보 모두 받아오기


        //유저의 티어별 문제 갯수 받아오기


    }


    public void processProblemList(List<User> users) {
        problemDomain.proAndAlgoList.clear();
        Flux.fromIterable(users)
                .delayElements(Duration.ofMillis(1))
                .flatMap(user ->
                        fetchDomain.fetchOneQueryData(
                                "/api/v3/user/top_100",
                                        problemDomain.makeUser100query(user)
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
    @Override
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
        System.out.println("good");
    }

}

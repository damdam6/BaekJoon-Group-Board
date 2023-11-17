package com.ssafypjt.bboard.model.domain.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import com.ssafypjt.bboard.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProblemDomain {

    @Autowired
    private ObjectMapper mapper;


    public List<ProblemAndAlgoObjectDomain> proAndAlgoList = new ArrayList<>();
    public Map<Integer,List<ProblemAndAlgoObjectDomain>> proAndAlgoMap = new HashMap<>();

    //userName & pageNum 으로 Api불러옴


    private void addToList(List<ProblemAndAlgoObjectDomain> tmpList){
        proAndAlgoList.addAll(tmpList);
    }
    private void addToMap(int userId,List<ProblemAndAlgoObjectDomain> problemAndAlgoObjectDomain){
        proAndAlgoMap.put(userId, problemAndAlgoObjectDomain);
    }

    private void addCase(String enumName, int userId, List<ProblemAndAlgoObjectDomain> tmpList){
        switch (enumName){
            case "tierProblem":
                addToMap(userId, tmpList);
                break;
            case "problemAndAlgo":
                addToList(tmpList);

        }
    }


    //합쳐서 list 만드는 과정임 ->
    public void makeProblemAndAlgoDomainObject(JsonNode aNode, User user, String enumName) {
        JsonNode arrayNode = aNode.path("items");
        if(!arrayNode.isArray()){
            return;
        }
        List<ProblemAndAlgoObjectDomain> tmpList = new ArrayList<>();
        for(JsonNode nodeItem: arrayNode) {
                Problem problem = makeProblemObject(nodeItem, user);
                ProblemAlgorithm problemAlgorithm = makeProblemAlgorithmObject(nodeItem);
                ProblemAndAlgoObjectDomain problemAndAlgoObjectDomain = new ProblemAndAlgoObjectDomain(problem,problemAlgorithm);
                tmpList.add(problemAndAlgoObjectDomain);
        }
        addCase(enumName, user.getUserId(), tmpList);

    }


    //각각 problem & algorithm
    public Problem makeProblemObject(JsonNode nodeItem, User user) {
        Problem problem = null;
        try {
            problem = mapper.treeToValue(nodeItem, Problem.class);
            problem.setUserId(user.getUserId());
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return problem;
    }

    public ProblemAlgorithm makeProblemAlgorithmObject(JsonNode nodeItem){
        StringBuilder algorithms = new StringBuilder();
        JsonNode tagsNode = nodeItem.path("tags");
        for (JsonNode tag : tagsNode) {
            if (algorithms.length() > 0) {
                algorithms.append(" ");
            }
            algorithms.append(tag.path("key").asText());
        }

        ProblemAlgorithm problemAlgorithm = new ProblemAlgorithm();
        problemAlgorithm.setProblemNum(nodeItem.path("problemId").asInt());
        problemAlgorithm.setAlgorithm(algorithms.toString());

        return problemAlgorithm;
    }


}


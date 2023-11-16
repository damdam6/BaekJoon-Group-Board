package com.ssafypjt.bboard.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.RecomProblem;

import java.util.List;

public interface ProblemService {

    // 총 문제 리스트 가져오기
    public List<Problem> getAllProblems();

    // 문제 가져오기
    public Problem getProblem(int id);

    // 특정 그룹에서 특정 티어의 문제 선정하여 가져오기, 일단 모든 리스트를 반환하는데 랜덤으로 1개만 반환하도록 해도 됨
    public List<Problem> getTierProblems(int tier, int groupId);

    // 그룹에 사용자가 직접 추천한 문제 선정하여 가져오기, 일단 모든 리스트를 반환하는데 랜덤으로 1개만 반환하도록 해도 됨
    public List<RecomProblem> getRecomProblems(int groupId);

    // 한 문제에 연관된 알고리즘을 가져오기, String Parsing 필요
    public List<String> getProblemAlgorithm(int problemNum);

    // 15분마다 문제 갱신 (여기에 해야하나?)
    public int resetProblems();



}

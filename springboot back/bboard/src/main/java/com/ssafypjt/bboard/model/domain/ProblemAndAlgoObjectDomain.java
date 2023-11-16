package com.ssafypjt.bboard.model.domain;

import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;

public class ProblemAndAlgoObjectDomain {

Problem problem;
ProblemAlgorithm problemAlgorithm;

ProblemAndAlgoObjectDomain(Problem problem, ProblemAlgorithm problemAlgorithm){
    this.problem = problem;
    this.problemAlgorithm = problemAlgorithm;
}

    public Problem getProblem() {
        return problem;
    }

    public ProblemAlgorithm getProblemAlgorithm() {
        return problemAlgorithm;
    }
}

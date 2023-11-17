package com.ssafypjt.bboard.model.domain.parsing;

import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import lombok.Data;

@Data
public class ProblemAndAlgoObjectDomain implements Comparable<ProblemAndAlgoObjectDomain>{

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
    @Override
    public int compareTo(ProblemAndAlgoObjectDomain o) {
        if(this.problem.getTier() > o.problem.getTier()){
            return 1;
        }else if(this.problem.getTier() < o.problem.getTier()){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return problem.toString()+"\n"+problemAlgorithm.toString();
    }
}

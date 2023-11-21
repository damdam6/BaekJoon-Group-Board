package com.ssafypjt.bboard.model.domain.solvedacAPI;

import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import lombok.Data;

@Data
public class ProblemAndAlgoObjectDomain implements Comparable<ProblemAndAlgoObjectDomain> {

    private Problem problem;
    private ProblemAlgorithm problemAlgorithm;

    ProblemAndAlgoObjectDomain() {

    }

    public ProblemAndAlgoObjectDomain(Problem problem, ProblemAlgorithm problemAlgorithm) {
        this.problem = problem;
        this.problemAlgorithm = problemAlgorithm;
    }

    @Override
    public int compareTo(ProblemAndAlgoObjectDomain o) {
        if (this.problem.getTier() > o.problem.getTier()) {
            return 1;
        } else if (this.problem.getTier() < o.problem.getTier()) {
            return -1;
        }
        return 0;
    }

}

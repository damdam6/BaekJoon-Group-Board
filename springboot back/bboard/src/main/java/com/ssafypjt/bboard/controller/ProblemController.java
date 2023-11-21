package com.ssafypjt.bboard.controller;

import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.RecomProblem;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.dto.UserTier;
import com.ssafypjt.bboard.model.service.ProblemService;
import com.ssafypjt.bboard.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {

    private ProblemService problemService;
    @Autowired
    public ProblemController(ProblemService problemService){
        this.problemService = problemService;
    }

    @GetMapping("")
    public ResponseEntity<?> getProblems(){
        List<Problem> problemList = problemService.getAllProblems();
        if (problemList == null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Problem>>(problemList, HttpStatus.OK);
    }

    @GetMapping("/tier")
    public ResponseEntity<?> getTiers(){
        List<UserTier> userTierList = problemService.getAllUserTiers();
        if (userTierList == null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<UserTier>>(userTierList, HttpStatus.OK);
    }

    @GetMapping("/tier-problem/{userId}")
    public ResponseEntity<?> getTierProblems(@PathVariable int userId){

        List<Problem> problemList = problemService.getUserTierProblems(userId);
        if (problemList == null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Problem>>(problemList, HttpStatus.OK);
    }

    @PostMapping("/recomproblem")
    public ResponseEntity<?> addRecomProblem(@RequestBody RecomProblem recomProblem){
        int result = problemService.addRecomProblem(recomProblem);
        if (result == 0)
            return new ResponseEntity<Integer>(result,HttpStatus.NO_CONTENT);
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }


}

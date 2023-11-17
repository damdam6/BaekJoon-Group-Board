package com.ssafypjt.bboard.controller;

import com.ssafypjt.bboard.model.dto.RecomProblem;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.service.ProblemService;
import com.ssafypjt.bboard.model.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-problem")
public class ProblemController {

    private ProblemService problemService;
    @Autowired
    public ProblemController(ProblemService problemService){
        this.problemService = problemService;
    }

    @GetMapping("/problem/reset")
    public ResponseEntity<?> reset(){
        System.out.println("에잇");
        //int result = problemService.resetProblems();
        return new ResponseEntity<Integer>(0, HttpStatus.OK);
    }

    @GetMapping("/problem/recomproblem/{groupId}")
    public ResponseEntity<?> getGroupRecomProblems(@PathVariable int groupId){
        List<RecomProblem> recomProblemList = problemService.getGroupRecomProblems(groupId);
        if (recomProblemList == null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<RecomProblem>>(recomProblemList, HttpStatus.OK);
    }

    @PostMapping("/problem/recomproblem")
    public ResponseEntity<?> addRecomProblem(@RequestBody RecomProblem recomProblem){
        int result = problemService.addRecomProblem(recomProblem);
        if (result == 0)
            return new ResponseEntity<Integer>(result,HttpStatus.NO_CONTENT);
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }



    

}

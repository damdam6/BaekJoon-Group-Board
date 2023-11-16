package com.ssafypjt.bboard.controller;

import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.service.ProblemService;
import com.ssafypjt.bboard.model.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        int result = problemService.resetProblems();
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }
    

}

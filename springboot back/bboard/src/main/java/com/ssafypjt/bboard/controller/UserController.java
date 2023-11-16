package com.ssafypjt.bboard.controller;

import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable int id){
        User user = userService.getUser(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> add(@RequestBody User user){
        System.out.println(user);
        int result = userService.addUser(user);

        switch (result){
            case 0 :
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            case -1 :
                return new ResponseEntity<Void>(HttpStatus.IM_USED);
            default:
                return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }






}

package com.ssafypjt.bboard.model.dto;


import lombok.*;

@Data
public class User {

    private int userId;
    private String userName;
    private int tier;
    private int solvedRank;
    private String imgUrl;
}

package com.ssafypjt.bboard.model.dto;

import lombok.Data;

@Data
public class UserTierProblem {
    private int id;
    private int userId;
    private int tier;
    private int problemNum;
    private String title;

}

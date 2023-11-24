package com.ssafypjt.bboard.model.dto;

import lombok.*;

@Data
public class RecomProblem{
    private int id;
    private int userId;
    private int groupId;
    private int problemNum;
    private int tier;
    private int title;
}

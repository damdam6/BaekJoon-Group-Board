package com.ssafypjt.bboard.model.dto;

import lombok.Data;

@Data
public class UserTier {
    private int id;
    private int userId;
    private int tier;
    private int problemCount;
    private int pageNo;
}

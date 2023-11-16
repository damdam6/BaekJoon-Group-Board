package com.ssafypjt.bboard.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Group {
    private int id;
    private String groupName;
    private String password;
}

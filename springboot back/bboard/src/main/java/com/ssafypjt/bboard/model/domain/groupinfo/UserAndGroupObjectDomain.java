package com.ssafypjt.bboard.model.domain.groupinfo;

import com.ssafypjt.bboard.model.dto.Group;
import com.ssafypjt.bboard.model.dto.User;
import lombok.Data;

@Data
public class UserAndGroupObjectDomain {

    private User user;
    private Group group;

    public UserAndGroupObjectDomain(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    public UserAndGroupObjectDomain() {
    }
}

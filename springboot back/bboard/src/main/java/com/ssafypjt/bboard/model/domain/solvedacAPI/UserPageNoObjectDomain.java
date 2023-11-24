package com.ssafypjt.bboard.model.domain.solvedacAPI;

import com.ssafypjt.bboard.model.dto.User;
import lombok.Data;

@Data
public class UserPageNoObjectDomain {


    private User user;
    private int pageNo;

    public UserPageNoObjectDomain() {
    }

    public UserPageNoObjectDomain(User user, int pageNo) {
        this.user = user;
        this.pageNo = pageNo;
    }


}

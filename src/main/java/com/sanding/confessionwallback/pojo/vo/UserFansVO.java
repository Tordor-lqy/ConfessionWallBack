package com.sanding.confessionwallback.pojo.vo;

import com.sanding.confessionwallback.pojo.entity.User;
import lombok.Data;

@Data
public class UserFansVO {

    private Long userId;
    private String userName;
    private String userAvatar;

    public UserFansVO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userAvatar = user.getUserAvatar();
    }
}

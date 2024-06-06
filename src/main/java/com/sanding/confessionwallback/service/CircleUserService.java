package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.CircleUserDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.User;

import java.util.List;

public interface CircleUserService {
    List<User> selectUsersId(Long id);

    void insert(CircleUserDTO circleUserDTO);

    void delectUserInCircle(Long circleUserId);

    void updateUserRole(CircleUserDTO circleUserDTO);

    Circle getCircleById(Long circleUserId);
}

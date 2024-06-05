package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.InsertUserInCircleDTO;
import com.sanding.confessionwallback.pojo.dto.UpdateUserRoleDTO;
import com.sanding.confessionwallback.pojo.entity.CircleUser;
import com.sanding.confessionwallback.pojo.entity.User;

import java.util.List;

public interface CircleUserService {
    List<User> selectUsersId(Long id);

    CircleUser insert(InsertUserInCircleDTO insertUserInCircleDTO);

    void delectUserInCircle(Long circleUserId);

    void updateUserRole(UpdateUserRoleDTO updateUserRole);
}

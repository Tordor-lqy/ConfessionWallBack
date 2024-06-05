package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.entity.User;

import java.util.List;

public interface CircleUserService {
    List<User> selectUsersId(Long id);
}

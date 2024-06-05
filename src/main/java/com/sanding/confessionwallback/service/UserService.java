package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.UserLoginDTO;
import com.sanding.confessionwallback.pojo.entity.User;

public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}

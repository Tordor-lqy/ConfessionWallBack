package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.UserLoginDTO;
import com.sanding.confessionwallback.pojo.vo.UserLoginVO;

public interface UserService {
    UserLoginVO wxLogin(UserLoginDTO userLoginDTO);
}

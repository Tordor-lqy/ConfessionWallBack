package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.UserLoginDTO;

public interface WeChatService {


    /**
     * 获取openid
     * @param userLoginDTO
     * @return
     */
    String wxLogin(UserLoginDTO userLoginDTO);

    /**
     * 获取手机号
     * @param userLoginDTO
     * @return
     */
    String getPhone(UserLoginDTO userLoginDTO);
}

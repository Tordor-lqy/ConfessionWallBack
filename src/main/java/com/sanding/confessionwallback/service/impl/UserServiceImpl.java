package com.sanding.confessionwallback.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanding.confessionwallback.common.constant.MessageConstant;
import com.sanding.confessionwallback.common.enumeration.AdminAndUserStatus;
import com.sanding.confessionwallback.common.exception.LoginFailedException;
import com.sanding.confessionwallback.common.properties.WeChatProperties;
import com.sanding.confessionwallback.common.utils.HttpClientUtil;
import com.sanding.confessionwallback.mapper.UserMapper;
import com.sanding.confessionwallback.pojo.dto.UserLoginDTO;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService{

    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";


    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;


    /**
     * 微信登录接口实现
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO){
        String openid = getOpenid(userLoginDTO.getCode());

        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getOpenid, openid)
        );
        if(user == null) {
            //未查询到该用户(新用户)
            user = User.builder()
                    .openid(openid)
                    .userRegisterTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }else {
            if(user.getUserStatus() == AdminAndUserStatus.UNOCCUPIED.getOrdinal())
        }

        return user;
    }

    private String getOpenid(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid =  jsonObject.getString("openid");
        return openid;
    }
}

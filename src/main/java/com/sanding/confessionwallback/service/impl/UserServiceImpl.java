package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanding.confessionwallback.common.constant.MessageConstant;
import com.sanding.confessionwallback.common.exception.LoginFailedException;
import com.sanding.confessionwallback.mapper.UserMapper;
import com.sanding.confessionwallback.pojo.dto.UserLoginDTO;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.pojo.vo.UserLoginVO;
import com.sanding.confessionwallback.service.UserService;
import com.sanding.confessionwallback.service.WeChatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private WeChatService weChatService;
    @Autowired
    private UserMapper userMapper;
    static String DEFAULT_NICKNAME_PREFIX = "圈友";
    @Override
    public UserLoginVO wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO);
        if(openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getOpenid, openid)
        );

        String phone = getPhone(userLoginDTO);

        user = user != null ? user : User.builder()
                .openid(openid)
                .userIp(null)
                .userName(DEFAULT_NICKNAME_PREFIX + UUID.randomUUID().toString().replace("-", ""))
                .userRegisterTime(LocalDateTime.now())
                .build();
        if (!phone.equals(user.getUserPhone())) {
            user.setUserPhone(phone);
            if(user.getUserId() == null) {
                userMapper.insert(user);
            }else {
                userMapper.updateById(user);
            }
        }
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user, userLoginVO);
        return userLoginVO;
    }

    private String getOpenid (UserLoginDTO userLoginDTO) {
        return weChatService.wxLogin(userLoginDTO);
    }

    private String getPhone (UserLoginDTO userLoginDTO) {
        return weChatService.getPhone(userLoginDTO);
    }
}

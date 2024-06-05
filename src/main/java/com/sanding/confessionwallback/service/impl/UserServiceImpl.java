package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanding.confessionwallback.common.constant.MessageConstant;
import com.sanding.confessionwallback.common.enumeration.AdminAndUserStatus;
import com.sanding.confessionwallback.common.exception.LoginFailedException;
import com.sanding.confessionwallback.common.utils.UserThreadLocal;
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
        //判断是否为新用户
        user = user != null ? user : User.builder()
                .openid(openid)
                .userIp(null)
                .userName(DEFAULT_NICKNAME_PREFIX + UUID.randomUUID().toString().replace("-", ""))
                .userRegisterTime(LocalDateTime.now())
                .userPhone(phone)
                .userStatus(AdminAndUserStatus.OCCUPIED.getOrdinal())
                .build();
        //判断手机号是否变化

        if(AdminAndUserStatus.UNOCCUPIED.getOrdinal().equals(user.getUserStatus())) {
            //用户被禁用
            throw new LoginFailedException(MessageConstant.ACCOUNT_UNOCCUPIED);
        }

        if (!phone.equals(user.getUserPhone())) {
            user.setUserPhone(phone);
            if(user.getUserId() == null) {
                userMapper.insert(user);
            }else {
                userMapper.updateById(user);
            }
        }

        //线程绑定(userId)
        UserThreadLocal.set(user.getUserId());

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

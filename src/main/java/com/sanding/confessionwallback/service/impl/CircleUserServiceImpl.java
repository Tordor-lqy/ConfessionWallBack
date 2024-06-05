package com.sanding.confessionwallback.service.impl;

import com.sanding.confessionwallback.mapper.CircleUserMapper;
import com.sanding.confessionwallback.mapper.UserMapper;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.service.CircleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircleUserServiceImpl implements CircleUserService {
    @Autowired
    private CircleUserMapper circleUserMapper;
    @Autowired
    private UserMapper userMapper;
    //查看某圈子下所有用户
    @Override
    public List<User> selectUsersId(Long id) {
        //根据圈子id查找与之关联的用户id
        List<Long> usersId = circleUserMapper.selectUsersId(id);
        //根据用户id查找用户
        List<User> userlist=userMapper.selectBatchIds(usersId);
        return userlist;
    }
}

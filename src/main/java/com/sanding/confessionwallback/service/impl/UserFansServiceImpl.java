package com.sanding.confessionwallback.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.UserFansMapper;
import com.sanding.confessionwallback.mapper.UserMapper;
import com.sanding.confessionwallback.pojo.dto.UserFansDTO;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.pojo.entity.UserFans;
import com.sanding.confessionwallback.pojo.vo.UserFansVO;
import com.sanding.confessionwallback.pojo.vo.UserVO;
import com.sanding.confessionwallback.service.UserFansService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFansServiceImpl implements UserFansService {

    @Autowired
    private UserFansMapper userFansMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public PageResult getFansList(UserFansDTO userFansDTO) {
        Page<UserFans> page = new Page<>(userFansDTO.getP(), userFansDTO.getS());
        // 查询粉丝列表
        Page<UserFans> userFansList = userFansMapper.selectPage(page, new LambdaQueryWrapper<UserFans>()
                .eq(UserFans::getUserId, userFansDTO.getUserId()));

        if (userFansList.getRecords().isEmpty()) {
            return new PageResult(0, new ArrayList<>());
        }
        List<Long> fansIdList = userFansList.getRecords().stream().map(UserFans::getFansId).collect(Collectors.toList());
        // 获取粉丝信息
        List<User> userList = userMapper.selectList(
                new LambdaQueryWrapper<User>()
                        .in(User::getUserId, fansIdList)
        );
        List<UserFansVO> userFansVOList = new ArrayList<>();
        for (User user : userList){
            userFansVOList.add(new UserFansVO(user));
        }
        return new PageResult(userFansList.getTotal(), userFansVOList);
    }

    @Override
    public PageResult getFollowList(UserFansDTO userFansDTO) {
        Page<UserFans> page = new Page<>(userFansDTO.getP(), userFansDTO.getS());
        // 查询粉丝列表
        Page<UserFans> userFansList = userFansMapper.selectPage(page, new LambdaQueryWrapper<UserFans>()
                .eq(UserFans::getFansId, userFansDTO.getUserId()));

        if (userFansList.getRecords().isEmpty()) {
            return new PageResult(0, new ArrayList<>());
        }
        List<Long> fansIdList = userFansList.getRecords().stream().map(UserFans::getUserId).collect(Collectors.toList());
        // 获取被关注者信息
        List<User> userList = userMapper.selectList(
                new LambdaQueryWrapper<User>()
                        .in(User::getUserId, fansIdList)
        );
        List<UserFansVO> userFansVOList = new ArrayList<>();
        for (User user : userList){
            userFansVOList.add(new UserFansVO(user));
        }
        return new PageResult(userFansList.getTotal(), userFansVOList);

    }

    @Override
    public UserVO getUserInfo(UserFansDTO userFansDTO) {
        User user = userMapper.selectById(userFansDTO.getUserId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user , userVO);
        return userVO;
    }
}

package com.sanding.confessionwallback.service.impl;

import com.sanding.confessionwallback.mapper.CircleMapper;
import com.sanding.confessionwallback.mapper.CircleUserMapper;
import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.service.CircleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CircleServiceImpl implements CircleService {
    @Autowired
    private CircleMapper circleMapper;
    @Autowired
    private CircleUserMapper circleUserMapper;

    //新增圈子
    @Override
    public Circle insertCircle(InsertCircleDTO insertCircleDTO) {
        Circle circle=new Circle();
        BeanUtils.copyProperties(insertCircleDTO,circle);
        circle.setCircleCreateTime(LocalDateTime.now());
        circle.setCircleUpdateTime(LocalDateTime.now());
        circleMapper.insert(circle);
        return circle;
    }

}

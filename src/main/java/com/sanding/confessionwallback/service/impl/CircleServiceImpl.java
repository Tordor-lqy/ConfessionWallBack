package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sanding.confessionwallback.mapper.CircleMapper;
import com.sanding.confessionwallback.mapper.CircleUserMapper;
import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.CircleUser;
import com.sanding.confessionwallback.service.CircleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    //更新圈子中的用户数量和更新时间
    @Override
    public void updateUserCount(Circle circle,boolean flag) {
        Integer count=circle.getCircleUserCount();
        circle.setCircleUserCount(flag?count+1:count-1);
        circle.setCircleUpdateTime(LocalDateTime.now());
        /**update circle表 set circleUserCount=用户数量 and circleUpdateTime=更新时间 where circleId=圈子id
         * */
        LambdaUpdateWrapper<Circle> wrapper=new LambdaUpdateWrapper<Circle>()
                .set(Circle::getCircleUserCount,circle.getCircleUserCount())
                        .set(Circle::getCircleUpdateTime,circle.getCircleUpdateTime())
                                .eq(Circle::getCircleId,circle.getCircleId());
        circleMapper.update(wrapper);
    }

    //根据圈子id找到圈子
    @Override
    public Circle getCircleById(Long circleId) {
        Circle circle = circleMapper.selectById(circleId);
        return circle;
    }


}

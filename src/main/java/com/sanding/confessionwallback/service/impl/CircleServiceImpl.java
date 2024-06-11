package com.sanding.confessionwallback.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.exception.PermissionAuthenticationException;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.CircleMapper;
import com.sanding.confessionwallback.mapper.CircleUserMapper;
import com.sanding.confessionwallback.pojo.dto.CircleDTO;
import com.sanding.confessionwallback.pojo.dto.CirclePageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.CircleUserDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.CircleUser;
import com.sanding.confessionwallback.service.CircleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public  class CircleServiceImpl implements CircleService {

    @Autowired
    private CircleMapper circleMapper;
    @Autowired
    private CircleUserMapper circleUserMapper;
    /**
     * 查看圈子
     * @param circlePageQueryDTO
     * @return
     */
    @Override
    public PageResult getPage(CirclePageQueryDTO circlePageQueryDTO) {

        // 创建分页对象
        Page<Circle> page = new Page<>(circlePageQueryDTO.getP(), circlePageQueryDTO.getS());

        // 创建查询条件
        LambdaQueryWrapper<Circle> wrapper = new LambdaQueryWrapper<>();
        if (circlePageQueryDTO.getCircleName() != null && !circlePageQueryDTO.getCircleName().isEmpty()) {
            wrapper.like(Circle::getCircleName, circlePageQueryDTO.getCircleName());
        }
        if(circlePageQueryDTO.getIsDelete() != null && circlePageQueryDTO.getIsDelete() != -1){
            wrapper.eq(Circle::getIsDelete,circlePageQueryDTO.getIsDelete());
        }
        if(circlePageQueryDTO.getCircleStatus() != null && circlePageQueryDTO.getCircleStatus() != -1){
            wrapper.eq(Circle::getCircleStatus , circlePageQueryDTO.getCircleStatus());
        }
        // 执行分页查询
        Page<Circle> resultPage = circleMapper.selectPage(page, wrapper);

        // 返回结果
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());

    }
    /**
     * 更新圈子信息
     * @param circleDTO
     * @return
     */
    @Override
    public void userUpdate(CircleDTO circleDTO) {

        //通过操作者id查是否为管理员
        Long userId = BaseContext.getCurrentId();

        LambdaQueryWrapper<CircleUser> queryWrapper = new LambdaQueryWrapper<CircleUser>();
        queryWrapper.select(CircleUser::getCircleUserRole)  // 只查询 state 字段
                .eq(CircleUser::getUserId, userId)
                .eq(CircleUser::getCircleId, circleDTO.getCircleId());// 通过 userId 和CricleId
        CircleUser ci = circleUserMapper.selectOne(queryWrapper);

        //若是就可以修改
        if (ci.getCircleUserRole() == CircleUser.SUPER_MANAGER||ci.getCircleUserRole() == CircleUser.MANAGER ) {
            Circle circle = Circle.builder().circleUpdateTime(LocalDateTime.now()).build();
            BeanUtils.copyProperties(circleDTO, circle);
            circleMapper.updateDong(circle);

        } else {
            throw new PermissionAuthenticationException("权限不足");
        }
    }

    @Override
    public int adminUpdate(CircleDTO circleDTO) {
        Circle circle = new Circle();
        BeanUtils.copyProperties(circleDTO, circle);
        circle.setCircleUpdateTime(LocalDateTime.now());
        return circleMapper.updateById(circle);
    }

    /**
     * 添加圈子管理
     * @param circleUserDTO
     * @return
     */
    @Override
    public void updateManager(CircleUserDTO circleUserDTO) {
        //是否为此圈子的超级管理
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<CircleUser> queryWrapper = new LambdaQueryWrapper<CircleUser>();
        queryWrapper.select(CircleUser::getCircleUserRole)  // 只查询 state 字段
                .eq(CircleUser::getUserId, userId)
                .eq(CircleUser::getCircleId, circleUserDTO.getCircleId());// 通过 userId 和CricleId
        CircleUser ci = circleUserMapper.selectOne(queryWrapper);

        //更改用户角色
        if (ci.getCircleUserRole() == CircleUser.SUPER_MANAGER) {
            CircleUser circleUser = new CircleUser();
            //若普通用户则1-0=1，变管理，若是管理则1-1=0，变普通用户
            circleUserDTO.setCircleUserRole(1 - circleUserDTO.getCircleUserRole());
            BeanUtils.copyProperties(circleUserDTO, circleUser);
            //查询条件
            LambdaQueryWrapper<CircleUser> queryWrapper1 = new LambdaQueryWrapper<CircleUser>()
                    .eq(CircleUser::getUserId, circleUserDTO.getUserId());
            circleUserMapper.update(circleUser, queryWrapper1);
        } else {
            throw new PermissionAuthenticationException("权限不足");
        }

    }
    /**
     * 用户更新圈子角色
     * @param circleUserDTO
     * @return
     */
    @Override
    public void updateRole(CircleUserDTO circleUserDTO) {
        //是否为此圈子的超级管理
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<CircleUser> queryWrapper = new LambdaQueryWrapper<CircleUser>();
        queryWrapper.select(CircleUser::getCircleUserRole)  // 只查询 state 字段
                .eq(CircleUser::getUserId, userId)
                .eq(CircleUser::getCircleId, circleUserDTO.getCircleId());// 通过 userId 和CricleId
        CircleUser ci = circleUserMapper.selectOne(queryWrapper);

        //更改用户角色·
        if (ci.getCircleUserRole() == CircleUser.SUPER_MANAGER) {
            CircleUser circleUser = new CircleUser();

            BeanUtils.copyProperties(circleUserDTO, circleUser);
            //查询条件
            LambdaQueryWrapper<CircleUser> queryWrapper1 = new LambdaQueryWrapper<CircleUser>()
                    .eq(CircleUser::getUserId, circleUserDTO.getUserId());
            circleUserMapper.update(circleUser, queryWrapper1);
        } else {
            throw new PermissionAuthenticationException("权限不足");
        }
    }
    /**
     * 用户加入圈子
     * @param
     * @return
     */
    @Override
    public void enterCircle(Long circleId) {
        Long userId = BaseContext.getCurrentId();
        //用户进圈
        CircleUser circleUser = new CircleUser();
        circleUser.setJoinTime(LocalDateTime.now());
        circleUser.setUserId(userId);
        circleUser.setCircleUserRole(CircleUser.COM_USER);
        circleUser.setCircleId(circleId);
        log.info("{}", circleUser);
        circleUserMapper.insert(circleUser);
        //圈子人数加一

        LambdaUpdateWrapper<Circle> updateWrapper = new LambdaUpdateWrapper<Circle>();
        updateWrapper.setSql("circle_user_count = circle_user_count + 1")
                .eq(Circle::getCircleId, circleUser.getCircleId());

        circleMapper.update( updateWrapper);
    }

    //新增圈子
    @Override
    public void insertCircle(CircleDTO circleDTO) {
        Circle circle=new Circle();
        BeanUtils.copyProperties(circleDTO,circle);
        circle.setCircleCreateTime(LocalDateTime.now());
        circle.setCircleUpdateTime(LocalDateTime.now());
        circle.setCircleStatus(0);
        circle.setIsDelete(0);
        circle.setCirclePostCount(0);
        circle.setCircleUserCount(0);
        circleMapper.insert(circle);
    }

    /**
     * 查询已加入的圈子
     * @param circlePageQueryDTO
     * @return
     */
    @Override
    public PageResult getJoinedCircle(CirclePageQueryDTO circlePageQueryDTO) {
        Long userId = BaseContext.getCurrentId();
        // 查询用户加入的圈子ID列表
        List<Long> joinedCircleIds = circleUserMapper.selectList(
                new LambdaQueryWrapper<CircleUser>()
                        .eq(CircleUser::getUserId, userId)
        ).stream().map(CircleUser::getCircleId).collect(Collectors.toList());

        if (joinedCircleIds.isEmpty()) {
            // 如果用户没有加入任何圈子，返回空的分页结果
            return new PageResult(0, Collections.emptyList());
        }

        // 创建分页对象
        Page<Circle> page = new Page<>(circlePageQueryDTO.getP(), circlePageQueryDTO.getS());

        // 创建查询条件
        LambdaQueryWrapper<Circle> wrapper = new LambdaQueryWrapper<Circle>()
                .in(Circle::getCircleId, joinedCircleIds)
                .like(circlePageQueryDTO.getCircleName() != null, Circle::getCircleName, circlePageQueryDTO.getCircleName());

        // 执行分页查询
        Page<Circle> resultPage = circleMapper.selectPage(page, wrapper);

        // 返回结果
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());


    }

    //删除圈子
    @Override
    public void deleteCircle(CircleDTO circleDTO) {
        //根据圈子id删除
        circleMapper.deleteById(circleDTO.getCircleId());
        //将用户和圈子的关系也删除
        LambdaQueryWrapper<CircleUser> wrapper=new LambdaQueryWrapper<CircleUser>()
                .eq(CircleUser::getCircleId,circleDTO.getCircleId());
        circleUserMapper.delete(wrapper);
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

    /**
     * 根据CircleId获取圈子信息
     * @param circleId
     * @return
     */
    @Override
    public Circle getCircleById(Long circleId) {
        Circle circle = circleMapper.selectById(circleId);
        return circle;
    }




}

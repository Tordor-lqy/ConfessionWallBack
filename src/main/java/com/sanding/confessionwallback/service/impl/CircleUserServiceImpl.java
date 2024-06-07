package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.CircleMapper;
import com.sanding.confessionwallback.mapper.CircleUserMapper;
import com.sanding.confessionwallback.mapper.UserMapper;
import com.sanding.confessionwallback.pojo.dto.CirclePageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.CircleUserDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.CircleUser;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.service.CircleUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CircleUserServiceImpl implements CircleUserService {
    @Autowired
    private CircleUserMapper circleUserMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CircleMapper circleMapper;

    //增加圈子中的用户
    @Override
    public void insert(CircleUserDTO circleUserDTO) {
        CircleUser circleUser=new CircleUser();
        BeanUtils.copyProperties(circleUserDTO,circleUser);
        circleUser.setJoinTime(LocalDateTime.now());
        //圈中角色为  普通成员
        circleUser.setCircleUserRole(CircleUser.COM_USER);
        /**Insert * values circleUser
         * */
        circleUserMapper.insert(circleUser);
    }

    //根据用户圈中id删除用
    @Override
    public void delectUserInCircle(Long circleUserId) {
        circleUserMapper.deleteById(circleUserId);
    }

    //更改某用户在某圈子的角色
    @Override
    public void updateUserRole(CircleUserDTO circleUserDTO) {
        /**update circleUser表 set userRole=圈中角色 where circleId=圈子id and circle_user_id=圈子用户id
         * */
        LambdaUpdateWrapper<CircleUser> wrapper=new LambdaUpdateWrapper<CircleUser>()
                .set(CircleUser::getCircleUserRole,circleUserDTO.getCircleUserRole())
                .eq(CircleUser::getCircleId,circleUserDTO.getCircleId())
                .eq(CircleUser::getCircleUserId,circleUserDTO.getCircleUserId());
        circleUserMapper.update(wrapper);
    }

    //根据圈中用户id找到圈子
    @Override
    public Circle getCircleById(Long circleUserId) {
        //先找圈子id
        CircleUser circleUser = circleUserMapper.selectById(circleUserId);
        //根据圈子id再找圈子
        Circle circle = circleMapper.selectById(circleUser.getCircleId());
        return circle;
    }

    /**
     * 用户退圈
     * @param circleId
     */
    @Override
    public void deleteUser(Long circleId) {
        Long userId = BaseContext.getCurrentId();
        //删除用户圈子信息
        LambdaQueryWrapper<CircleUser> wrapper=new LambdaQueryWrapper<CircleUser>()
                .eq(CircleUser::getCircleId,circleId)
                .eq(CircleUser::getUserId,userId);
        circleUserMapper.delete(wrapper);
    //更改圈子人数信息
        // 先查出圈子对象
        LambdaQueryWrapper<Circle> wr = new LambdaQueryWrapper<Circle>()
                .select(Circle::getCircleUserCount)
                .eq(Circle::getCircleId, circleId);

// 查询结果
        Circle circle = circleMapper.selectOne(wr);

// 人数
        Long circleUserCount = circle != null ? circle.getCircleUserCount() : 0L;


        Integer  count= Integer.parseInt(circleUserCount.toString())-1;
        //更改赋值
        LambdaUpdateWrapper<Circle> wrapper1=new LambdaUpdateWrapper<Circle>()
                .set(Circle::getCircleUserCount,count )
                .eq(Circle::getCircleId,circleId);
        circleMapper.update(wrapper1);
    }

    /**
     * 获取用户在圈子的身份
     * @param circleId
     * @return
     */
    @Override
    public Integer getRole(Long circleId) {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<CircleUser> wrapper=new LambdaQueryWrapper<CircleUser>()
                .select(CircleUser::getCircleUserRole)
                .eq(CircleUser::getCircleId,circleId)
                .eq(CircleUser::getUserId,userId);
        CircleUser circleUser = circleUserMapper.selectOne(wrapper);

        return circleUser.getCircleUserRole();
    }

    @Override
    public PageResult getUserPage(CirclePageQueryDTO circlePageQueryDTO) {
        //根据圈子id查找与之关联的用户id
        /**select userId from circleuser表 where circleId=id
         * */
        LambdaQueryWrapper<CircleUser> wrapper1=new LambdaQueryWrapper<CircleUser>()
                .select(CircleUser::getUserId)
                .eq(CircleUser::getCircleId,circlePageQueryDTO.getCircleId());
        List<Long> usersId =circleUserMapper.selectObjs(wrapper1);
        //根据用户id查找用户
        // 创建分页对象
        Page<User> page = new Page<>(circlePageQueryDTO.getP(), circlePageQueryDTO.getS());
        // 创建查询条件
        LambdaQueryWrapper<User> wrapper2=new LambdaQueryWrapper<User>()
                .in(User::getUserId,usersId);
        // 执行分页查询
        Page<User> resultPage = userMapper.selectPage(page, wrapper2);
        // 返回结果
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());

    }

}

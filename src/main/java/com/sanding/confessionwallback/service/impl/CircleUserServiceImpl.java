package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sanding.confessionwallback.mapper.CircleMapper;
import com.sanding.confessionwallback.mapper.CircleUserMapper;
import com.sanding.confessionwallback.mapper.UserMapper;
import com.sanding.confessionwallback.pojo.dto.InsertUserInCircleDTO;
import com.sanding.confessionwallback.pojo.dto.UpdateUserRoleDTO;
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
    //查看某圈子下所有用户
    @Override
    public List<User> selectUsersId(Long circleId) {
        //根据圈子id查找与之关联的用户id
        /**select userId from circleuser表 where circleId=id
         * */
        LambdaQueryWrapper<CircleUser> wrapper=new LambdaQueryWrapper<CircleUser>()
                .select(CircleUser::getUserId)
                        .eq(CircleUser::getCircleId,circleId);
        List<Long> usersId =circleUserMapper.selectObjs(wrapper);
        //根据用户id查找用户
        /**select * from user表 where userId in (usersId)
         * */
        List<User> userlist=userMapper.selectBatchIds(usersId);
        return userlist;
    }

    //增加圈子中的用户
    @Override
    public CircleUser insert(InsertUserInCircleDTO insertUserInCircleDTO) {
        CircleUser circleUser=new CircleUser();
        BeanUtils.copyProperties(insertUserInCircleDTO,circleUser);
        circleUser.setJoinTime(LocalDateTime.now());
        //圈中角色为  普通成员
        circleUser.setCircleUserRole(CircleUser.COM_USER);
        /**Insert * values circleUser
         * */
        circleUserMapper.insert(circleUser);
        return circleUser;
    }

    //根据用户圈中id删除用户
    @Override
    public void delectUserInCircle(Long circleUserId) {
        circleUserMapper.deleteById(circleUserId);
    }

    //更改某用户在某圈子的角色
    @Override
    public void updateUserRole(UpdateUserRoleDTO updateUserRole) {
        /**update circleUser表 set userRole=圈中角色 where circleId=圈子id and circle_user_id=圈子用户id
         * */
        LambdaUpdateWrapper<CircleUser> wrapper=new LambdaUpdateWrapper<CircleUser>()
                .set(CircleUser::getCircleUserRole,updateUserRole.getCircleUserRole())
                        .eq(CircleUser::getCircleId,updateUserRole.getCircleId())
                                .eq(CircleUser::getCircleUserId,updateUserRole.getCircleUserId());
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



}

package com.sanding.confessionwallback.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanding.confessionwallback.common.constant.MessageConstant;
import com.sanding.confessionwallback.common.enumeration.AdminAndUserStatus;
import com.sanding.confessionwallback.common.exception.LoginFailedException;
import com.sanding.confessionwallback.common.utils.UserThreadLocal;
import com.sanding.confessionwallback.mapper.AdminMapper;
import com.sanding.confessionwallback.mapper.CircleMapper;
import com.sanding.confessionwallback.pojo.dto.AdminLoginDTO;
import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
import com.sanding.confessionwallback.pojo.entity.Admin;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private CircleMapper circleMapper;

    @Override
    public Admin login(AdminLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getAdminName();
        String password = employeeLoginDTO.getAdminPassword();

        //根据用户名查询数据库中的数据
        Admin admin = adminMapper.selectOne(
                new LambdaQueryWrapper<Admin>()
                        .eq(Admin::getAdminName, username)
        );
        //用户不存在
        if(admin == null) {
            throw new LoginFailedException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //密码md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(admin.getAdminPassword())) {
            //密码错误
            throw new LoginFailedException(MessageConstant.PASSWORD_ERROR);
        }
        if (admin.getAdminStatus().equals(AdminAndUserStatus.UNOCCUPIED.getOrdinal())) {
            //账号被锁定
            throw new LoginFailedException(MessageConstant.ACCOUNT_LOCKED);
        }
        //绑定线程
        UserThreadLocal.set(admin.getAdminId());

        return admin;
    }
}

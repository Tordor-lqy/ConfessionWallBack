package com.sanding.confessionwallback.controller.admin;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.sanding.confessionwallback.common.constant.JwtClaimsConstant;
import com.sanding.confessionwallback.common.constant.MessageConstant;
import com.sanding.confessionwallback.common.exception.LoginFailedException;
import com.sanding.confessionwallback.common.properties.JwtProperties;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.common.utils.JWTUtils;
import com.sanding.confessionwallback.pojo.dto.AdminLoginDTO;
import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
import com.sanding.confessionwallback.pojo.entity.Admin;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.vo.AdminLoginVO;
import com.sanding.confessionwallback.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Api("管理员相关接口")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param adminLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "管理端登录")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        log.info("员工登录：{}", adminLoginDTO);
        Admin admin = adminService.login(adminLoginDTO);
        if(admin == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, admin.getAdminId());
        String token = JWTUtils.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .adminId(admin.getAdminId())
                .adminName(admin.getAdminName())
                .token(token)
                .build();

        return Result.success(adminLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     *增加新的圈子
     */
    @PostMapping()
    @ApiOperation("增加圈子")
    public Result<String> insertCircle(@RequestBody InsertCircleDTO insertCircleDTO){
        log.info("新增圈子：{}",insertCircleDTO);
        adminService.insertCircle(insertCircleDTO);
        return Result.success("success");
    }

    /**
     * 查看某圈子下所有用户
     * */



}

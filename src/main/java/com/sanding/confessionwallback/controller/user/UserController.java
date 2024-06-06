package com.sanding.confessionwallback.controller.user;

import com.sanding.confessionwallback.common.properties.JwtProperties;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.UserLoginDTO;
import com.sanding.confessionwallback.pojo.vo.UserLoginVO;
import com.sanding.confessionwallback.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Api("C端用户相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired

    private JwtProperties jwtProperties;
    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("登录用户{}",userLoginDTO);
        UserLoginVO userLoginVO = userService.wxLogin(userLoginDTO);
        log.info("登陆成功{}", userLoginVO);
        return Result.success(userLoginVO);
    }
}

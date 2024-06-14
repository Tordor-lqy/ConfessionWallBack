package com.sanding.confessionwallback.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanding.confessionwallback.common.constant.JwtClaimsConstant;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.properties.JwtProperties;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.common.utils.JWTUtils;
import com.sanding.confessionwallback.mapper.UserMapper;
import com.sanding.confessionwallback.pojo.dto.UserLoginDTO;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.pojo.vo.UserLoginVO;
import com.sanding.confessionwallback.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api("C端用户相关接口")
@Slf4j
public class UserController {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserService userService;
    //TODO test 模拟用户登录
    @Autowired
    private UserMapper userMapper;
    private static final String OPENID = "abcdefg";

    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("登录用户{}",userLoginDTO);
        //UserLoginVO userLoginVO = userService.wxLogin(userLoginDTO);
        //Map<String,Object> claims = new HashMap<>();
        //claims.put(JwtClaimsConstant.USER_ID, userLoginVO.getUserId());
        //claims.put(JwtClaimsConstant.NAME, userLoginVO.getUserName());
        //userLoginVO.setToken(
        //        JWTUtils.createJWT(
        //                jwtProperties.getUserSecretKey(),
        //                jwtProperties.getUserTtl(),
        //                claims
        //        )
        //);
        //TODO test 模拟用户登录
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUserOpenid, OPENID)
        ), userLoginVO);
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userLoginVO.getUserId());
        claims.put(JwtClaimsConstant.NAME, userLoginVO.getUserName());
        userLoginVO.setToken(
                JWTUtils.createJWT(
                        jwtProperties.getUserSecretKey(),
                        jwtProperties.getUserTtl(),
                        claims
                )
        );
        //在controller层赋值userid 测试用后期删除
        BaseContext.setCurrentId(userLoginVO.getUserId());
        log.info("登陆成功{}", BaseContext.getCurrentId());
        log.info("登陆成功{}", userLoginVO);
        return Result.success(userLoginVO);
    }
    /**
     * 临时token
     */
    //TODO 测试用记得删了最后
    @GetMapping("/login")
    public Result<String> token(){

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, 1);
        String token = JWTUtils.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        return Result.success(token);
    }
}

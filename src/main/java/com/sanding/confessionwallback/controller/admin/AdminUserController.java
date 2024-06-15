package com.sanding.confessionwallback.controller.admin;

import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.UserFansDTO;
import com.sanding.confessionwallback.service.UserFansService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin/user")
public class AdminUserController {

    @Autowired
    private UserFansService userFansService;


    // 根据用户ID查看粉丝列表
    @GetMapping("/fans")
    public Result getFansList(UserFansDTO userFansDTO) {
        return Result.success(userFansService.getFansList(userFansDTO));
    }


    // 根据用户ID查看关注列表
    @GetMapping("/follow")
    public Result getFollowList(UserFansDTO userFansDTO) {
        return Result.success(userFansService.getFollowList(userFansDTO));
    }

    @GetMapping
    public Result getUserInfo(UserFansDTO userFansDTO) {
        return Result.success(userFansService.getUserInfo(userFansDTO));
    }
}

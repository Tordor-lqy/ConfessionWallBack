package com.sanding.confessionwallback.controller.user;


import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.UserFansDTO;
import com.sanding.confessionwallback.service.UserFansService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserUserController {

    @Autowired
    private UserFansService userFansService;


    @GetMapping("/fans")
    public Result getFans(UserFansDTO userFansDTO) {
        userFansDTO.setUserId(BaseContext.getCurrentId());
        return Result.success(userFansService.getFansList(userFansDTO));
    }

    @GetMapping("/follow")
    public Result getFollows(UserFansDTO userFansDTO) {
        userFansDTO.setUserId(BaseContext.getCurrentId());
        return Result.success(userFansService.getFollowList(userFansDTO));
    }
    @GetMapping("/info/{userId}")
    public Result getUserInfo(@PathVariable Long userId) {
        return Result.success(userFansService.getUserInfo(userId));
    }


    @PostMapping("/follow/{userId}")
    public Result follow(@PathVariable Long userId) {
        userFansService.follow(userId);
        return Result.success();
    }

    @DeleteMapping("/follow/{userId}")
    public Result unfollow(@PathVariable Long userId) {
        userFansService.unfollow(userId);
        return Result.success();
    }

}

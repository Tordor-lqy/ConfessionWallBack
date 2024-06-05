package com.sanding.confessionwallback.controller.admin;

import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
import com.sanding.confessionwallback.pojo.dto.InsertUserInCircleDTO;
import com.sanding.confessionwallback.pojo.dto.UpdateUserRoleDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.CircleUser;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.service.CircleService;
import com.sanding.confessionwallback.service.CircleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/circle")
@Api("圈子操作相关接口")
@Slf4j
public class AdminCircleController {
    @Autowired
    private CircleService circleService;
    @Autowired
    private CircleUserService circleUserService;

    /**
     *增加新的圈子
     */
    @PostMapping("/incircle")
    @ApiOperation("增加圈子")
    public Result<Circle> insertCircle(@RequestBody InsertCircleDTO insertCircleDTO){
        log.info("新增圈子：{}",insertCircleDTO);
        Circle circle = circleService.insertCircle(insertCircleDTO);
        return Result.success(circle);
    }

    /**
     * 查看某圈子下所有用户
     * */
    @GetMapping("/users/{id}")
    @ApiOperation("查看圈子用户")
    public Result<List<User>> selectAllUser(@ApiParam("圈子circleId") @PathVariable Long id){
        log.info("查看圈子：{} 所有用户",id);
        List<User> list=circleUserService.selectUsersId(id);
        return Result.success(list);
    }
    /**
     * 在圈子中增加用户
     * */
    @PostMapping("/{circleId}/{userId}")
    @ApiOperation("在圈子中增加用户")
    public Result<CircleUser> insertUserInCircle(InsertUserInCircleDTO insertUserInCircleDTO){
        log.info("根据圈子：{circleId} 增加用户{userId}");
        //在圈子中增加用户
        CircleUser circleUser=circleUserService.insert(insertUserInCircleDTO);
        //更新圈子中的用户数量 true增加  false减少
        log.info("更新用户数量");
        //1.找到圈子
        Circle circle = circleService.getCircleById(insertUserInCircleDTO.getCircleId());
        //2.更新用户数量
        circleService.updateUserCount(circle, true);
        return Result.success(circleUser);
    }
    /**
     * 在某圈子中删除某用户
     * */
    @ApiOperation("删除圈中用户")
    @DeleteMapping("{circleUserId}")
    public Result<String> delectUserInCircle(@PathVariable Long circleUserId){
        log.info("根据用户圈中id: {} 删除用户",circleUserId);
        circleUserService.delectUserInCircle(circleUserId);
        log.info("更新用户数量");
        //1.找到圈子
        Circle circle = circleService.getCircleById(circleUserId);
        //2.更新用户数量 true增加  false减少
        circleService.updateUserCount(circle,false);
        return Result.success("success");
    }
    /**
     * 更改某用户在某圈子的角色
     * */
    @PostMapping("/updateRole")
    @ApiOperation("更改某用户在某圈子的角色")
    public Result<String> updateUserRole(@RequestBody UpdateUserRoleDTO updateUserRole){
        log.info("更改某用户在某圈子的角色");
        circleUserService.updateUserRole(updateUserRole);
        return Result.success("success");
    }



    // 6月6日任务
    // todo 删除圈子
    // todo 获取圈子(简介)信息
    // todo 修改圈子信息
    // todo 添加圈子内的用户
    // todo 删除圈子内的某个用户
    // todo 更该圈子内某个用户的角色(权限)

}

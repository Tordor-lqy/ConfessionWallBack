package com.sanding.confessionwallback.controller.admin;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.CircleDTO;
import com.sanding.confessionwallback.pojo.dto.CirclePageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.CircleUserDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
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

    /**管理员查看圈子
     * */
    @GetMapping
    public Result<PageResult> page(CirclePageQueryDTO circlePageQueryDTO) {
        log.info("分页查询:{}", circlePageQueryDTO);
        PageResult pageResult = circleService.getPage(circlePageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     *增加新的圈子
     */
    @PostMapping
    @ApiOperation("增加圈子")
    public Result insertCircle(@RequestBody CircleDTO circleDTO){
        log.info("新增圈子：{}",circleDTO);
        circleService.insertCircle(circleDTO);
        return Result.success();
    }

    /**
     * 查看某圈子下所有用户
     * */
    @GetMapping("/users/{circleId}")
    @ApiOperation("查看圈子用户")
    public Result<List<User>> selectAllUser(@ApiParam("圈子circleId") @PathVariable Long circleId){
        log.info("查看圈子：{} 所有用户",circleId);
        List<User> list=circleUserService.selectUsersId(circleId);
        return Result.success(list);
    }
    /**
     * 在圈子中增加用户
     * */
    @PostMapping("/addUser")
    @ApiOperation("在圈子中增加用户")
    public Result insertUserInCircle(@RequestBody CircleUserDTO circleUserDTO){
        log.info("根据圈子：{circleId} 增加用户{userId}");
        //在圈子中增加用户
        circleUserService.insert(circleUserDTO);
        //更新圈子中的用户数量 true增加  false减少
        log.info("更新用户数量");
        //1.找到圈子
        Circle circle = circleService.getCircleById(circleUserDTO.getCircleId());
        //2.更新圈子中的用户数量和更新时间
        circleService.updateUserCount(circle, true);
        return Result.success();
    }
    /**
     * 在某圈子中删除某用户
     * */
    @ApiOperation("删除圈中用户")
    @DeleteMapping("{circleUserId}")
    public Result delectUserInCircle(@PathVariable Long circleUserId){
        //1.找到圈子，再删除用户，再更改圈中用户数量和更新时间
        log.info("找到用户所在圈子");
        Circle circle = circleUserService.getCircleById(circleUserId);
        log.info("根据用户圈中id: {} 删除用户",circleUserId);
        circleUserService.delectUserInCircle(circleUserId);
        log.info("更新用户数量");
        //2.更新用户数量 true增加  false减少
        circleService.updateUserCount(circle,false);
        return Result.success();
    }
    /**
     * 更改某用户在某圈子的角色
     * */
    @PutMapping
    @ApiOperation("更改某用户在某圈子的角色")
    public Result updateUserRole(@RequestBody CircleUserDTO circleUserDTO){
        log.info("更改某用户在某圈子的角色");
        circleUserService.updateUserRole(circleUserDTO);
        return Result.success();
    }


    // 6月6日任务
    // todo 删除圈子
    // todo 获取圈子(简介)信息
    // todo 修改圈子信息
    // todo 添加圈子内的用户
    // todo 删除圈子内的某个用户
    // todo 更该圈子内某个用户的角色(权限)

}

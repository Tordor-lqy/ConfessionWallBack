package com.sanding.confessionwallback.controller.circle;

import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
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
@RequestMapping("/api/circle")
@Api("圈子操作相关接口")
@Slf4j
public class CircleController {
    @Autowired
    private CircleService circleService;
    @Autowired
    private CircleUserService circleUserService;

    /**
     *增加新的圈子
     */
    @PostMapping()
    @ApiOperation("增加圈子")
    public Result<Circle> insertCircle(@RequestBody InsertCircleDTO insertCircleDTO){
        log.info("新增圈子：{}",insertCircleDTO);
        Circle circle = circleService.insertCircle(insertCircleDTO);
        return Result.success(circle);
    }

    /**
     * 查看某圈子下所有用户
     * */
    @GetMapping("/{id}")
    @ApiOperation("查看圈子用户")
    public Result<List<User>> selectAllUser(@ApiParam("圈子id") @PathVariable Long id){
        log.info("查看圈子：{} 所有用户",id);
        List<User> list=circleUserService.selectUsersId(id);
        return Result.success(list);
    }


}

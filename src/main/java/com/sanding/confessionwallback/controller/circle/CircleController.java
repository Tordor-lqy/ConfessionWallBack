package com.sanding.confessionwallback.controller.circle;

import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.service.CircleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/circle")
@Api("圈子操作相关接口")
@Slf4j
public class CircleController {
    @Autowired
    private CircleService circleService;

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
}

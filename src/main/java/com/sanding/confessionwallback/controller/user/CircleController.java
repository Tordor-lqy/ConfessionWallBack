package com.sanding.confessionwallback.controller.user;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.CircleDTO;
import com.sanding.confessionwallback.pojo.dto.CirclePageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.CircleUserDTO;
import com.sanding.confessionwallback.service.CircleService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user/circle")
public class CircleController {
    @Autowired
    private CircleService circleService;

    /**
     * 查看圈子
     * @param circlePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(CirclePageQueryDTO circlePageQueryDTO) {
        log.info("分页查询:{}", circlePageQueryDTO);
        PageResult pageResult = circleService.getPage(circlePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 更新圈子信息
     * @param circleDTO
     * @return
     */
    @PutMapping("/updateCircle")
    public Result update(@RequestBody CircleDTO circleDTO) {
        log.info("更新圈子：{}", circleDTO);
        circleService.update(circleDTO);
        return Result.success();
    }

    /**
     * 添加圈子管理
     * @param circleUserDTO
     * @return
     */
    @PutMapping("updateManager")
    public Result updateManager(@RequestBody CircleUserDTO circleUserDTO) {
        log.info("添加圈子管理：{}", circleUserDTO);
        circleService.updateManager(circleUserDTO);
        return Result.success();
    }

    /**
     * 用户更新圈子角色
     * @param circleUserDTO
     * @return
     */
    @PutMapping("/updateRole")
    public Result updateRole(@RequestBody CircleUserDTO circleUserDTO) {
        log.info("更新圈子角色：{}", circleUserDTO);
        circleService.updateRole(circleUserDTO);
        return Result.success();
    }

    /**
     * 用户加入圈子
     * @param circleUserDTO
     * @return
     */
    @PostMapping
    public Result enterCircle(@RequestBody CircleUserDTO circleUserDTO) {
        log.info("加入圈子：{}", circleUserDTO);
        circleService.enterCircle(circleUserDTO);
        return Result.success();
    }


}

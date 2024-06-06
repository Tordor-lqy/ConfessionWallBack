package com.sanding.confessionwallback.controller.user;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.CircleDTO;
import com.sanding.confessionwallback.pojo.dto.CirclePageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.CircleUserDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.service.CircleService;
import com.sanding.confessionwallback.service.CircleUserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user/circle")
public class UserCircleController {
    @Autowired
    private CircleService circleService;
    @Autowired
    private CircleUserService circleUserService;
    /**
     * 查看圈子
     * @param circlePageQueryDTO
     * @return
     */
    @GetMapping
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
    @PutMapping
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
    @PutMapping("/updateManager")
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
     * @param
     * @return
     */
    @PostMapping
    public Result enterCircle(Long circleId) {
        log.info("加入圈子的圈子id：{}", circleId);
        circleService.enterCircle(circleId);
        return Result.success();
    }
    /**
     * 查看某个圈子信息
     */
    @GetMapping("/{circleId}")
    public Result<Circle> getCircleByCircleId(@PathVariable Long circleId) {
        log.info("查看某个圈子信息：{}id为", circleId);
        Circle circle=circleService.getCircleById(circleId);
        return Result.success(circle);
    }

    /**
     * 用户退出圈子
     * @param circleId
     * @return
     */
    @DeleteMapping
    public Result deleteUser(Long circleId) {
        log.info("退出圈子的id：{}",circleId);
        circleUserService.deleteUser(circleId);

        return Result.success();
    }

    /**
     * 查询已加入的圈子信息
     * @param circlePageQueryDTO
     * @return
     */
    @GetMapping("/joined")
    public Result<PageResult>  getJoinedCircle(CirclePageQueryDTO circlePageQueryDTO) {
        log.info("查看已经加入的圈子:{}",circlePageQueryDTO);
      PageResult pageResult=  circleService.getJoinedCircle(circlePageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     *  查询在某圈子中的身份,返回数字
     */
    @GetMapping("/role")
    public Result<Integer> getRole(Long circleId) {
        log.info("查询在某圈子中的身份:{},圈子id",circleId);
        Integer role=circleUserService.getRole(circleId);
        return Result.success(role);
    }


}

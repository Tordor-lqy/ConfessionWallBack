package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.CircleDTO;
import com.sanding.confessionwallback.pojo.dto.CirclePageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.CircleUserDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;

public interface CircleService {
    /**
     * 查看圈子
     * @param circlePageQueryDTO
     * @return
     */
    PageResult getPage(CirclePageQueryDTO circlePageQueryDTO);
    /**
     * 更新圈子信息
     * @param circleDTO
     * @return
     */
    void update(CircleDTO circleDTO);
    /**
     * 添加圈子管理
     * @param circleUserDTO
     * @return
     */
    void updateManager(CircleUserDTO circleUserDTO);
    /**
     * 用户更新圈子角色
     * @param circleUserDTO
     * @return
     */
    void updateRole(CircleUserDTO circleUserDTO);
    /**
     * 用户加入圈子
     * @param
     * @return
     */
    void enterCircle(Long circleId);

    void updateUserCount(Circle circle, boolean b);

    Circle getCircleById(Long circleId);

    void insertCircle(CircleDTO circleDTO);

    /**
     * 查看某个圈子信息
     * @param circleId
     * @return
     */
    Circle getCircleByCircleId(Long circleId);
}

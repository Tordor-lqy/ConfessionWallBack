package com.sanding.confessionwallback.controller.user;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.CircleDTO;
import com.sanding.confessionwallback.pojo.dto.CirclePageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.CircleUserDTO;
import com.sanding.confessionwallback.pojo.dto.GroupPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.Group;
import com.sanding.confessionwallback.service.CircleService;
import com.sanding.confessionwallback.service.CircleUserService;
import com.sanding.confessionwallback.service.GroupService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user/group")
public class UserGroupController {
    @Autowired
    private GroupService groupService;


    /**
     * 根据圈子id查询所有分组
     */
    @GetMapping
    @ApiOperation("根据圈子id查询所有分组")
    public PageResult pageGroup(GroupPageQueryDTO groupPageQueryDTO) {
        log.info("分页条件查询所有分组{}", groupPageQueryDTO);
        groupPageQueryDTO.setIsDeleted(Group.NOT_DELETE);
        return groupService.list(groupPageQueryDTO);
    }

}

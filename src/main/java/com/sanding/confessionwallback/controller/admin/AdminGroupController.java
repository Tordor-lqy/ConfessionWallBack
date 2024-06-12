package com.sanding.confessionwallback.controller.admin;


import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.GroupPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.GroupSaveDTO;
import com.sanding.confessionwallback.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/group")
@Api("管理端分组操作相关接口")
@Slf4j
public class AdminGroupController {

	@Autowired
	private GroupService groupService;

	/**
	 * 条件分页查询所有分组
	 * @return
	 */
	@GetMapping
	@ApiOperation("条件分页查询所有分组")
	public PageResult getAllGroup( GroupPageQueryDTO groupPageQueryDTO) {
		log.info("分页条件查询所有分组{}", groupPageQueryDTO);
		PageResult pageResult = groupService.list(groupPageQueryDTO);
		return pageResult;
	}

	@PostMapping
	@ApiOperation("新增分组")
	private Result saveGroup(@RequestBody GroupSaveDTO groupSaveDTO) {
		log.info("新增分组{}", groupSaveDTO);
		groupService.saveGroup(groupSaveDTO);
		return Result.success();
	}

}

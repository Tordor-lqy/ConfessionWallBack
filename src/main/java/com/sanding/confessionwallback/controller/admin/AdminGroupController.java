package com.sanding.confessionwallback.controller.admin;


import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.GroupDTO;
import com.sanding.confessionwallback.pojo.dto.GroupPageQueryDTO;
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
	 */
	@GetMapping
	@ApiOperation("条件分页查询所有分组")
	public PageResult page(GroupPageQueryDTO groupPageQueryDTO) {
		log.info("分页条件查询所有分组{}", groupPageQueryDTO);
		return groupService.list(groupPageQueryDTO);
	}


	/**
	 * 新增分组
	 */
	@PostMapping
	@ApiOperation("新增分组")
	public Result saveGroup(@RequestBody GroupDTO groupDTO) {
		log.info("新增分组{}", groupDTO.toString());
		groupService.saveGroup(groupDTO);
		return Result.success();
	}

	/**
	 * 修改分组
	 */
	@PutMapping
	@ApiOperation("修改分组")
	public Result updateGroup(@RequestBody GroupDTO groupDTO) {
		log.info("修改分组{}", groupDTO);
		groupService.updateGroup(groupDTO);
		return Result.success();
	}

	@DeleteMapping("/{id}")
	@ApiOperation("根据删除分组")
	public Result deleteGroup(@PathVariable Long id) {
		log.info("删除分组{}", id);
		groupService.deleteById(id);
		return Result.success();
	}

	@DeleteMapping
	@ApiOperation("根据删除分组")
	public Result deleteGroup(@RequestParam List<Long> ids) {
		log.info("删除分组{}", ids);
		groupService.BatchDeleteById(ids);
		return Result.success();
	}



}

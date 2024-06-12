package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.constant.MessageConstant;
import com.sanding.confessionwallback.common.exception.BaseException;
import com.sanding.confessionwallback.common.exception.SaveFailException;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.GroupMapper;
import com.sanding.confessionwallback.pojo.dto.GroupDTO;
import com.sanding.confessionwallback.pojo.dto.GroupPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Group;
import com.sanding.confessionwallback.pojo.entity.Topic;
import com.sanding.confessionwallback.service.GroupService;
import com.sanding.confessionwallback.service.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupMapper groupMapper;

	@Autowired
	private TopicService topicService;

	/**
	 * 条件查询所有分组
	 */

	@Override
	public PageResult list(GroupPageQueryDTO groupPageQueryDTO) {
		Page<Group> page = new Page<>(groupPageQueryDTO.getP(), groupPageQueryDTO.getS());
		QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
		if (groupPageQueryDTO.getGroupId() != null) {
			queryWrapper.eq("group_id", groupPageQueryDTO.getGroupId());
		}

		if (groupPageQueryDTO.getGroupName() != null && !groupPageQueryDTO.getGroupName().isEmpty()) {
			queryWrapper.like("group_name", groupPageQueryDTO.getGroupName());
		}

		if (groupPageQueryDTO.getCircleId() != null) {
			queryWrapper.eq("circle_id", groupPageQueryDTO.getCircleId());
		}

		if (groupPageQueryDTO.getIsDeleted() != null) {
			queryWrapper.eq("is_deleted", groupPageQueryDTO.getIsDeleted());
		}
		Page<Group> list = groupMapper.selectPage(page, queryWrapper);
		return new PageResult(list.getTotal(), list.getRecords());
	}

	/**
	 * 新增分组
	 */
	@Override
	public void saveGroup(GroupDTO groupDTO) {
		Group group = BeanToGroup(groupDTO);
		if (group.getGroupName() == null || group.getGroupName().isEmpty() ||
		group.getCircleId() == null) {
			throw new SaveFailException(MessageConstant.SAVE_FAILED);
		}
		//先查询当前圈子下是否存在这个分组
		QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("group_name", group.getGroupName())
				.eq("circle_id", group.getCircleId());

		//存在分组已存在异常抛异常
		Group group1 = groupMapper.selectOne(queryWrapper);
		if (group1 != null) {
			throw new SaveFailException(MessageConstant.GROUP_ALREADY_EXISTS);
		}
		//不存在插入
		groupMapper.insert(group);
	}

	/**
	 * 修改分组
	 */
	@Override
	public void updateGroup(GroupDTO groupDTO) {
		Group group = BeanToGroup(groupDTO);
		if (group.getGroupName() == null || group.getGroupName().isEmpty() ||
				group.getGroupId() == null) {
			throw new SaveFailException(MessageConstant.UPDATE_FAILED);
		}
		groupMapper.updateById(group);
	}

	/**
	 * 根据分组id删除分组
	 */
	@Override
	public void deleteById(Long groupId) {
		deleteCheck(groupId);
		Group group = new Group();
		group.setGroupId(groupId);
		groupMapper.deleteById(group);
	}

	/**
	 * 批量删除分组
	 */
	@Override
	public void BatchDeleteById(List<Long> ids) {
		ids.forEach(this::deleteCheck);
		groupMapper.deleteBatchIds(ids);
	}

	private Group BeanToGroup(GroupDTO groupDTO) {
		Group group = new Group();
		BeanUtils.copyProperties(groupDTO, group);
		return group;
	}

	private void deleteCheck(Long groupId) {
		Group group = groupMapper.selectById(groupId);
		if (group == null) {
			//分组不存在删除失败
			throw new BaseException(groupId+"分组不存在!");
		}
		//先检测该分组下是否有话题
		List<Topic> list = topicService.selectByGroupId(groupId);
		if(list != null && !list.isEmpty()){
			throw new BaseException("该分组下存在话题");
		}
	}
}

package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.constant.MessageConstant;
import com.sanding.confessionwallback.common.exception.BaseException;
import com.sanding.confessionwallback.common.exception.GroupException;
import com.sanding.confessionwallback.common.exception.SaveFailException;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.CircleMapper;
import com.sanding.confessionwallback.mapper.GroupMapper;
import com.sanding.confessionwallback.pojo.dto.GroupDTO;
import com.sanding.confessionwallback.pojo.dto.GroupPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.Group;
import com.sanding.confessionwallback.pojo.entity.Topic;
import com.sanding.confessionwallback.service.GroupService;
import com.sanding.confessionwallback.service.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CircleMapper circleMapper;

    /**
     * 条件查询所有分组
     */

    @Override
    public PageResult list(GroupPageQueryDTO groupPageQueryDTO) {
        Page<Group> page = new Page<>(groupPageQueryDTO.getP(), groupPageQueryDTO.getS());
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        //根据圈子名称查询圈子Id
        if (groupPageQueryDTO.getCircleName() != null && !groupPageQueryDTO.getCircleName().isEmpty()) {
            LambdaQueryWrapper<Circle> wrapper1 = new LambdaQueryWrapper<Circle>();
            List<Long> circleIdList = circleMapper.selectList(wrapper1.like(Circle::getCircleName, groupPageQueryDTO.getCircleName())).stream().map(Circle::getCircleId).collect(Collectors.toList());
            if (circleIdList.isEmpty()) {
                return new PageResult(0, new ArrayList<>());
            }
            queryWrapper.in(Group::getCircleId, circleIdList);
        }
        if (groupPageQueryDTO.getGroupId() != null) {
            queryWrapper.eq(Group::getGroupId, groupPageQueryDTO.getGroupId());
        }
        if (groupPageQueryDTO.getGroupName() != null && !groupPageQueryDTO.getGroupName().isEmpty()) {
            queryWrapper.like(Group::getGroupName, groupPageQueryDTO.getGroupName());
        }
        if (groupPageQueryDTO.getCircleId() != null) {
            queryWrapper.eq(Group::getCircleId, groupPageQueryDTO.getCircleId());
        }
        if (groupPageQueryDTO.getIsDeleted() != null) {
            queryWrapper.eq(Group::getIsDeleted, groupPageQueryDTO.getIsDeleted());
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
            throw new SaveFailException(Group.SAVE_FAILED);
        }
        //先查询当前圈子下是否存在这个分组
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getGroupName, group.getGroupName())
                .eq(Group::getCircleId, group.getCircleId());

        //存在分组已存在异常抛异常
        if (groupMapper.selectOne(queryWrapper) != null) {
            throw new SaveFailException(Group.ALREADY_EXISTS);
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
            throw new GroupException(MessageConstant.UPDATE_FAILED);
        }
        Group group1 = groupMapper.selectById(group.getGroupId());
        if (group1 == null) {
            throw new GroupException(Group.NOT_FOUND);
        }
        //若该分组已经删除则无法修改
        if (group1.getIsDeleted().equals(Group.DELETE)) {
            throw new GroupException(Group.ALREADY_DELETE);
        }
        groupMapper.updateById(group);
    }

    /**
     * 批量删除分组
     */
    @Override
    public void BatchDeleteById(List<Long> ids) {
        Group group = new Group();
        group.setIsDeleted(Group.DELETE);
        ids.forEach(this::deleteCheck);
        groupMapper.update(
                group, new LambdaQueryWrapper<Group>()
                        .in(Group::getGroupId, ids)
        );
    }

    private Group BeanToGroup(GroupDTO groupDTO) {
        Group group = new Group();
        BeanUtils.copyProperties(groupDTO, group);
        return group;
    }

    private void deleteCheck(Long groupId) {
        //先检测该分组下是否有话题
        List<Topic> list = topicService.selectByGroupId(groupId);
        if (list != null && !list.isEmpty()) {
            throw new BaseException("该分组下存在话题");
        }
    }
}

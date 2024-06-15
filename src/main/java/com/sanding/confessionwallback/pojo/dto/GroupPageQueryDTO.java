package com.sanding.confessionwallback.pojo.dto;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.sanding.confessionwallback.pojo.entity.Group;
import lombok.Data;

@Data
public class GroupPageQueryDTO {

	public static final String CIRCLE_ID = "circle_id";
	//分组id
	private Long groupId;
	//分组名称
	private String groupName;
	//圈子id
	private Long circleId;
	//圈子名称
	private String circleName;
	// 分组是否删除
	private Integer isDeleted;
	//页数
	private Integer p;
	//每页条数
	private Integer s;
}

package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

@Data
public class GroupPageQueryDTO {

	//分组id
	private Long groupId;
	//分组名称
	private String groupName;
	//圈子id
	private Long circleId;
	// 分组是否删除
	private Integer isDeleted;
	//页数
	private Integer p;
	//每页条数
	private Integer s;
}

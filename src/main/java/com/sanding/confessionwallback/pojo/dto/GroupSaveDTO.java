package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

@Data
public class GroupSaveDTO {
	//分组id
	private Long groupId;
	//分组名称
	private String groupName;
	//圈子id
	private Long circleId;

}

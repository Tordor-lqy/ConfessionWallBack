package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

@Data
public class PostCommentPageQueryDTO {
	//页数
	private Integer p;
	//每页条数
	private Integer s;
	//圈子id
	private Long circleId;
	//圈子名称
	private String circleName;
	//分组id
	private Long groupId;
	//分组名称
	private String groupName;
	//话题id
	private Long topicId;
	//话题名称
	private String topicName;
	//帖子ID
	private Long postId;
	//帖子标题
	private String postTitle;
	//评论ID
	private Long postCommentId;
	//评论内容
	private String postComment;
	//用户ID
	private Long userId;
	//用户名字
	private String userName;

}

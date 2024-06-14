package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

@Data
public class PostCommentPageQueryDTO {
	//页数
	private Integer p;
	//每页条数
	private Integer s;
	//帖子ID
	private Long postId;
	//评论ID
	private Long postCommentId;
	//用户ID
	private Long userId;
}

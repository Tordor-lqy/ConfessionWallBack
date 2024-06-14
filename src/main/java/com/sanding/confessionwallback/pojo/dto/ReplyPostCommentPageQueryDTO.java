package com.sanding.confessionwallback.pojo.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class ReplyPostCommentPageQueryDTO{
	@Getter
	@Setter
	private Integer p;

	@Getter
	@Setter
	private Integer s;
	private Long postReplyId; // 主键
	private Long userId; // 用户ID
	private String replyContent; // 评论内容
	private Long replyCommentId; // 回复评论ID
	private LocalDateTime createTime; // 创建时间
	private Long secReplyId; //回复的回复id
	private Long replyUserId; //被回复人的id
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

}

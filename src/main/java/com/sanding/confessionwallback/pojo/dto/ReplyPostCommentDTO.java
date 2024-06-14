package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ReplyPostCommentDTO {
	private Long postReplyId; // 主键
	private Long postId; // 帖子ID
	private Long userId; // 用户ID
	private String replyContent; // 评论内容
	private Long replyCommentId; // 回复评论ID
	private LocalDateTime createTime; // 创建时间
	private Long secReplyId; //回复的回复id
	private Long replyUserId; //被回复人的id

}

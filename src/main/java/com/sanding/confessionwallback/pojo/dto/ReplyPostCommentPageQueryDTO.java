package com.sanding.confessionwallback.pojo.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class ReplyPostCommentPageQueryDTO{
	private Integer p;
	private Integer s;
	private Long postReplyId; // 主键
	private Long userId; // 用户ID
	private String userName; // 用户ID
	private String replyContent; // 评论内容
	private Long replyCommentId; // 回复评论ID
	private LocalDateTime createTime; // 创建时间
	private Long secReplyId; //回复的回复id
	private String secReplyContent; //回复的回复回复内容
	private Long replyUserId; //被回复人的id

}

package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cw_post_reply")
public class ReplyPostComment {

    public static final Long NO_REPLY = -1L;  //未回复的评论为-1
    public static final Long MO_LIKE = 0L; //默认评论点赞数为0

    @TableId(type = IdType.AUTO)
    private Long postReplyId; // 主键
    private Long postId; // 帖子ID
    private Long userId; // 用户ID
    private String replyContent; // 评论内容
    private Long replyCommentId; // 回复评论ID
    private LocalDateTime createTime; // 创建时间
    private Long secReplyId; //回复的回复id
    private Long replyUserId; //被回复人的id



}
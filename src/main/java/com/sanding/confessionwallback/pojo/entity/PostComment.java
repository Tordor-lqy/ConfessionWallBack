package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cw_post_comment")
public class PostComment {

    public static final Long NO_REPLY = -1L;  //未回复的评论为-1
    public static final Long MO_LIKE = 0L; //默认评论点赞数为0

    @TableId(type = IdType.AUTO)
    private Long postCommentId; // 主键
    private Long postId; // 帖子ID
    private Long postUserId; // 帖子用户ID
    private Long userId; // 用户ID
    private String commentContent; // 评论内容
    private Long replyCommentId; // 回复评论ID
    private Long commentLikeCount; // 评论点赞数
    private LocalDateTime postCommentCreateTime; // 创建时间
}
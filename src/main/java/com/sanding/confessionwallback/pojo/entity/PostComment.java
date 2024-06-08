package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

@Data
@TableName("cw_post_comment")
public class PostComment {

    public static final Integer NO_REPLY = -1;

    @TableId(type = IdType.AUTO)
    private Long postCommentId; // 主键
    private Long postId; // 帖子ID
    private Long userId; // 用户ID
    private String commentContent; // 评论内容
    private Long replyCommentId; // 回复评论ID
    private Long commentLikeCount; // 评论点赞数
    private LocalDateTime createTime; // 创建时间

}
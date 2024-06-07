package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

@Data
@TableName("cw_post")
public class Post {

    @TableId(type = IdType.AUTO)
    private Long postId; // 帖子ID 主键
    private Long circleId; // 所属圈子ID
    private Long userId; // 所属用户ID
    private String postContent; // 帖子内容
    private Long postLikeCount;// 帖子点赞数
    private Long postCommentCount; // 帖子评论回复数
    private String postImgUrls; // 帖子图片URL数组base64编码
    private LocalDateTime postUpdateTime; // 帖子更新时间
    private LocalDateTime postCreateTime; // 帖子创建时间
}
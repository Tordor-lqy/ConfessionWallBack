package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

@Data
@TableName("cw_post_user_like")
public class PostUserLike{

    @TableId(type = IdType.AUTO)
    private Long postUserLikeId; // 主键
    private Long postId;  // 帖子ID
    private Long userId; // 用户ID

}
package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;


@Data
@TableName("cw_topic_post")
public class TopicPost  {

    @TableId(type = IdType.AUTO)
    private Long topicPostId; // 主键 话题帖子ID
    private Long postId; // 帖子ID
    private Long topicId; // 话题ID

}
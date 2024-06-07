package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

@Data
@TableName("cw_topic")
public class Topic {

    @TableId(type = IdType.AUTO)
    private Long TopicId; // 话题ID
    private String topicName; // 话题名称
    private Long topicPostCount; // 话题帖子数量

}
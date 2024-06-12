package com.sanding.confessionwallback.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TopicDTO {

    public static final long MO_TPCOUNT = 1L;

    private Long topicId; // 话题ID
    private String topicName; // 话题名称
    private Long circleId; //圈子ID
    private Long groupId; // 群组ID


}

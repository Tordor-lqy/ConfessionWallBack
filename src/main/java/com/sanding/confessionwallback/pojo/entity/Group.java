package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cw_group")
public class Group {
    @TableId(type = IdType.AUTO)
    private Long groupId; //分组ID
    private String groupName; //分组名称
    private Long circleId; //圈子ID
    private Integer isDeleted; //逻辑删除
}

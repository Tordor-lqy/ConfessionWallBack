package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cw_group")
public class Group {
    public static final String NOT_FOUND = "分组不存在";
    public static final String EXIST_TOPIC = "分组下存在话题";
    public static final String ALREADY_EXISTS = "分组已存在";
    public static final String SAVE_FAILED= "保存失败";
    public static final String ALREADY_DELETE= "分组已删除";
    public static final Integer DELETE= 1; //已删除
    public static final Integer NOT_DELETE= 0; //未删除


    @TableId(type = IdType.AUTO)
    private Long groupId; //分组ID
    private String groupName; //分组名称
    private Long circleId; //圈子ID
    private Integer isDeleted; //逻辑删除
}

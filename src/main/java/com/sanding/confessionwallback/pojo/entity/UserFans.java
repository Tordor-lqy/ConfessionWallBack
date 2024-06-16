package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("cw_user_fans")
public class UserFans {
    @TableId(type = IdType.AUTO)
    private Long userFansId;

    private Long userId;

    private Long FansId;
}

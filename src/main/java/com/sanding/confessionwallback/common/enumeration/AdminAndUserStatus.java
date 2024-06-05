package com.sanding.confessionwallback.common.enumeration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "管理员或用户状态枚举")
public enum AdminAndUserStatus {
        @ApiModelProperty(value = "禁用")
        UNOCCUPIED(0, "禁用"),
        @ApiModelProperty(value = "启用")
        OCCUPIED(1, "启用");

        private Integer ordinal;
        private String name;

        AdminAndUserStatus(Integer ordinal, String name) {
            this.ordinal = ordinal;
            this.name = name;
        }

        public Integer getOrdinal() {
            return ordinal;
        }

        public String getName() {
            return name;
        }
}

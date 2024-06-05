package com.sanding.confessionwallback.pojo.dto;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员登录
 */
@Data
public class AdminLoginDTO {
    // 管理员名称
    private String adminName;
    // 密码
    private String adminPassword;
}

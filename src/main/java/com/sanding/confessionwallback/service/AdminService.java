package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.AdminLoginDTO;
import com.sanding.confessionwallback.pojo.entity.Admin;

public interface AdminService {
    Admin login(AdminLoginDTO employeeLoginDTO);
}

package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;

public interface CircleService {
    Circle insertCircle(InsertCircleDTO insertCircleDTO);

    void updateUserCount(Circle circle, boolean b);

    Circle getCircleById(Long circleId);
}

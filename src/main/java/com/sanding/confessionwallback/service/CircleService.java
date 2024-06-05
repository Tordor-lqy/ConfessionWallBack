package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.InsertCircleDTO;
import com.sanding.confessionwallback.pojo.entity.Circle;
import com.sanding.confessionwallback.pojo.entity.User;

import java.util.List;

public interface CircleService {
    Circle insertCircle(InsertCircleDTO insertCircleDTO);

}

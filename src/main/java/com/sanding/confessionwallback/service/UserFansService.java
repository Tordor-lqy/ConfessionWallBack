package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.UserFansDTO;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.pojo.vo.UserVO;

import java.util.List;

public interface UserFansService {


    PageResult getFansList(UserFansDTO userFansDTO);

    PageResult getFollowList(UserFansDTO userFansDTO);

    UserVO getUserInfo(UserFansDTO userFansDTO);
}

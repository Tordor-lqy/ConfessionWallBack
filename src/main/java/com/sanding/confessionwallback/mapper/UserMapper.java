package com.sanding.confessionwallback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanding.confessionwallback.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

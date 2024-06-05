package com.sanding.confessionwallback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanding.confessionwallback.pojo.entity.CircleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CircleUserMapper extends BaseMapper<CircleUser> {
    @Select("select user_id from cw_circle_user where circle_id=#{id}")
    List<Long> selectUsersId(Long id);
}

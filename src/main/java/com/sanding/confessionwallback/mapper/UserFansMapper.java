package com.sanding.confessionwallback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanding.confessionwallback.pojo.entity.UserFans;
import com.sanding.confessionwallback.pojo.vo.UserFansVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserFansMapper extends BaseMapper<UserFans> {


    @Select("SELECT u.user_name as user_name, f.fans_id as user_id \n" +
            "FROM cw_user u\n" +
            "LEFT JOIN cw_user_fans f ON u.user_id = f.fans_id\n" +
            "WHERE f.user_id = #{userId}")
    List<UserFansVO> getFansList(@Param("userId") Long userId);

}

package com.sanding.confessionwallback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanding.confessionwallback.pojo.dto.UpdateUserRoleDTO;
import com.sanding.confessionwallback.pojo.entity.CircleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CircleUserMapper extends BaseMapper<CircleUser> {

}

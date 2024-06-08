package com.sanding.confessionwallback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanding.confessionwallback.pojo.entity.Circle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper

public interface CircleMapper extends BaseMapper<Circle> {

//动态查询

    void updateDong(Circle circle);

    /**
     * 圈子帖子数加一
     * @param circleId
     */
    @Update("update cw_circle set circle_post_count = circle_post_count + 1 where circle_id = #{circleId}")
    void insertCircleTopicCount(Long circleId);
}

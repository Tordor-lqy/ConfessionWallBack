package com.sanding.confessionwallback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanding.confessionwallback.pojo.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    @Update("UPDATE cw_post SET post_like_count = post_like_count - 1 WHERE post_id = #{postId}")
    void decrementLikeCount(@Param("postId") Long postId);
}

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
    @Update("update cw_post set post_comment_count = post_comment_count +1 where post_id =#{postId}")
    void insertCommentCount(@Param("postId") Long postId);
    @Update("update cw_post set post_comment_count = post_comment_count -1 where post_id =#{postId}")
    void delCommentCount(@Param("postId") Long postId);
}

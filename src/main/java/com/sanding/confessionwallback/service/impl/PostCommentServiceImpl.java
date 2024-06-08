package com.sanding.confessionwallback.service.impl;

import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.mapper.PostCommentMapper;
import com.sanding.confessionwallback.mapper.PostMapper;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.entity.PostComment;
import com.sanding.confessionwallback.service.PostCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    private PostCommentMapper postCommentMapper;
    @Autowired
    private PostMapper postMapper;
    /**
     * 用户评论帖子
     * @param postCommentDTO
     */
    @Override
    public void commentTopic(PostCommentDTO postCommentDTO) {
        Long userId = BaseContext.getCurrentId();
        //添加评论与帖子联系
        PostComment postComment=new PostComment();
        postComment.setPostCommentCreateTime(LocalDateTime.now());
        postComment.setCommentLikeCount(PostComment.MO_LIKE);
        BeanUtils.copyProperties(postCommentDTO,postComment);
        postComment.setUserId(userId);
        postComment.setReplyCommentId(PostComment.NO_REPLY);
        postCommentMapper.insert(postComment);
        //帖子评论数加一
        postMapper.insertCommentCount(postCommentDTO.getPostId());


    }
}

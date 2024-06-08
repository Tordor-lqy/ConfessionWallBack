package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.PostCommentMapper;
import com.sanding.confessionwallback.mapper.PostMapper;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.PostComment;
import com.sanding.confessionwallback.service.PostCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    private PostCommentMapper postCommentMapper;
    @Autowired
    private PostMapper postMapper;
    /** 根据帖子id查看帖子评论
     * */
    @Override
    public PageResult selectPostComments(PostCommentPageQueryDTO postCommentPageQueryDTO) {
        // 创建分页对象
        Page<PostComment> page = new Page<>(postCommentPageQueryDTO.getP(), postCommentPageQueryDTO.getS());
        // 创建查询条件
        /**select * from postcomment表 where post_id=帖子id
         * */
        LambdaQueryWrapper<PostComment> wrapper = new LambdaQueryWrapper<PostComment>();
        wrapper.eq(PostComment::getPostId,postCommentPageQueryDTO.getPostId());
        // 执行分页查询
        Page<PostComment> resultPage = postCommentMapper.selectPage(page, wrapper);
        // 返回结果
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }
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

    /**
     * 用户删除帖子评论
     * @param postCommentId
     */
    @Override
    @Transactional
    public void delCommentTopic(Long postCommentId) {


        //获取postId
        LambdaQueryWrapper<PostComment> wrapper=new LambdaQueryWrapper<PostComment>()
                .select(PostComment::getPostId)
                .eq(PostComment::getPostCommentId,postCommentId);
        PostComment postComment=postCommentMapper.selectOne(wrapper);
        Long postId = postComment.getPostId();
        //帖子评论数减一
        postMapper.delCommentCount(postId);

        //解除关系
        postCommentMapper.deleteById(postCommentId);
    }
}

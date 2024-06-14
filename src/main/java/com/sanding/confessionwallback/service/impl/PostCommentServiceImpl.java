package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.PostCommentMapper;
import com.sanding.confessionwallback.mapper.PostMapper;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Post;
import com.sanding.confessionwallback.pojo.entity.PostComment;
import com.sanding.confessionwallback.pojo.entity.ReplyPostComment;
import com.sanding.confessionwallback.service.PostCommentService;
import com.sanding.confessionwallback.service.ReplyPostCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PostCommentServiceImpl implements PostCommentService {
	@Autowired
	private PostCommentMapper postCommentMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private ReplyPostCommentService replyPostCommentService;
	/**
	 * 用户评论帖子
	 */
	@Override
	public void savePostComment(PostCommentDTO postCommentDTO) {
		Long userId = BaseContext.getCurrentId(); //发布评论的用户id
		Long postId = postCommentDTO.getPostId(); //发布评论的帖子id
		//根据帖子id查询发布人
		Long postUserId = postMapper.selectOne(
				new LambdaQueryWrapper<Post>()
						.eq(Post::getPostId, postId)
		).getUserId();
		/*
		  新增评论post表操作: 评论成功, post_comment_count +1
		  评论表插入即可
		 */
		//构建评论对象
		PostComment postComment = PostComment.builder()
				.postId(postId)
				.userId(userId)
				.commentContent(postCommentDTO.getCommentContent())
				.commentLikeCount(PostComment.MO_LIKE)
				.postCommentCreateTime(LocalDateTime.now())
				.postUserId(postUserId)
				.build();
		postCommentMapper.insert(postComment);
		//帖子评论数加一
		postMapper.insertCommentCount(postCommentDTO.getPostId());
	}

	/**
	 * 查看当前用户发布的评论
	 */
	@Override
	public PageResult getPostComment(PostCommentPageQueryDTO postCommentPageQueryDTO) {
		Page<PostComment> page = new Page<>(
				postCommentPageQueryDTO.getP(),
				postCommentPageQueryDTO.getS()
		);

		LambdaQueryWrapper<PostComment> queryWrapper = new LambdaQueryWrapper<>();

		if (postCommentPageQueryDTO.getPostId() != null) {
			queryWrapper.eq(PostComment::getPostId, postCommentPageQueryDTO.getPostId());
		}

		if (postCommentPageQueryDTO.getPostCommentId() != null) {
			queryWrapper.eq(PostComment::getPostCommentId, postCommentPageQueryDTO.getPostCommentId());
		}

		if (postCommentPageQueryDTO.getUserId() != null) {
			queryWrapper.eq(PostComment::getUserId, postCommentPageQueryDTO.getUserId());
		}

		queryWrapper.orderByDesc(PostComment::getPostCommentCreateTime);

		postCommentMapper.selectPage(page, queryWrapper);

		return new PageResult(page.getTotal(), page.getRecords());
	}

	/**
	 * 根据用户评论id批量删除评论
	 */
	@Transactional
	@Override
	public void batchDeleteByPostCommentId(List<Long> ids) {
		//1. 删除reply_comment表数据
		replyPostCommentService.batchDeleteByCommentId(ids);
		//2. 删除post_comment表数据
		postCommentMapper.deleteBatchIds(ids);
	}

}

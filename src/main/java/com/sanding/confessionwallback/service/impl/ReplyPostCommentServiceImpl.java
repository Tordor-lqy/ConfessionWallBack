package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.PostCommentMapper;
import com.sanding.confessionwallback.mapper.ReplyPostCommentMapper;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.PostComment;
import com.sanding.confessionwallback.pojo.entity.ReplyPostComment;
import com.sanding.confessionwallback.service.ReplyPostCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReplyPostCommentServiceImpl implements ReplyPostCommentService {

	@Autowired
	private ReplyPostCommentMapper replyPostCommentMapper;

	@Autowired
	private PostCommentMapper postCommentMapper;

	/**
	 * 新增回复
	 **/
	@Override
	public void saveReplyComment(ReplyPostCommentDTO replyPostCommentDTO) {
		ReplyPostComment replyPostComment = new ReplyPostComment();
		BeanUtils.copyProperties(replyPostCommentDTO, replyPostComment);
		Long replyUserId;
		//根据评论id查询被回复人的id
		if (replyPostComment.getSecReplyId() == null) {
			//如果回复的是评论
			replyPostComment.setSecReplyId(ReplyPostComment.NO_REPLY);
			PostComment postComment = postCommentMapper.selectOne(
					new LambdaQueryWrapper<PostComment>()
							.eq(PostComment::getPostCommentId, replyPostComment.getReplyCommentId())
			);
			replyUserId = postComment.getUserId();
		} else {
			// 回复的是回复
			replyUserId = replyPostCommentMapper.selectOne(
					new LambdaQueryWrapper<ReplyPostComment>()
							.eq(ReplyPostComment::getPostReplyId, replyPostComment.getSecReplyId())
			).getUserId();
		}
		replyPostComment.setReplyUserId(replyUserId);
		replyPostComment.setUserId(BaseContext.getCurrentId());
		replyPostComment.setCreateTime(LocalDateTime.now());

		replyPostCommentMapper.insert(replyPostComment);
	}

	/**
	 * 根据DTO分页查询回复
	 */
	@Override
	public PageResult getReplyPostComment(ReplyPostCommentPageQueryDTO replyPostCommentPageQueryDTO) {
		Page<ReplyPostComment> page = new Page<>(
				replyPostCommentPageQueryDTO.getP(),
				replyPostCommentPageQueryDTO.getS()
		);

		LambdaQueryWrapper<ReplyPostComment> queryWrapper = new LambdaQueryWrapper<>();

		if (replyPostCommentPageQueryDTO.getUserId() != null) {
			queryWrapper.eq(ReplyPostComment::getUserId, replyPostCommentPageQueryDTO.getUserId());
		}

		queryWrapper.orderByDesc(ReplyPostComment::getCreateTime);

		replyPostCommentMapper.selectPage(page, queryWrapper);

		return new PageResult(page.getTotal(), page.getRecords());

	}
	/**
	 * 根据回复id批量删除回复
	 */
	@Override
	public void batchDeleteByPostReplyId(List<Long> replyIds) {
		replyPostCommentMapper.delete(
				new LambdaQueryWrapper<ReplyPostComment>()
						.in(ReplyPostComment::getPostReplyId, replyIds)
		);
	}

	/**
	 * 根据评论的id删除回复
	 */
	@Override
	public void batchDeleteByCommentId(List<Long> postCommentIds) {
		replyPostCommentMapper.delete(
				new LambdaQueryWrapper<ReplyPostComment>()
						.in(ReplyPostComment::getReplyCommentId, postCommentIds)
		);
	}


}

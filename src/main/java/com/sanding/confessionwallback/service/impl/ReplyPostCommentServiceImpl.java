package com.sanding.confessionwallback.service.impl;

import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.mapper.ReplyPostCommentMapper;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentDTO;
import com.sanding.confessionwallback.pojo.entity.ReplyPostComment;
import com.sanding.confessionwallback.service.ReplyPostCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ReplyPostCommentServiceImpl implements ReplyPostCommentService {

	@Autowired
	private ReplyPostCommentMapper replyPostCommentMapper;

	/**
	 * 新增回复
	 **/
	@Override
	public void saveReplyComment(ReplyPostCommentDTO replyPostCommentDTO) {
		ReplyPostComment replyPostComment = new ReplyPostComment();
		BeanUtils.copyProperties(replyPostCommentDTO, replyPostComment);
		if (replyPostComment.getSecReplyId() == null) {
			replyPostComment.setSecReplyId(ReplyPostComment.NO_REPLY);
		}
		replyPostComment.setUserId(BaseContext.getCurrentId());
		replyPostComment.setCreateTime(LocalDateTime.now());
		replyPostCommentMapper.insert(replyPostComment);
	}
}

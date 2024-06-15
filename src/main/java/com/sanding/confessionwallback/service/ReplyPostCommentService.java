package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentPageQueryDTO;

import java.util.List;

public interface ReplyPostCommentService {
	void saveReplyComment(ReplyPostCommentDTO replyPostCommentDTO);

	void batchDeleteReplyCommentByReplyCommentId(List<Long> replyIds);

	void batchDeleteByCommentId(List<Long> commentIds);

	PageResult getReplyPostCommentByUserId(Long userId, Integer p, Integer s);


	PageResult getReplyPostCommentByCommentId(Long commentId, Integer p, Integer s);

	PageResult getReplyCommentsPage(ReplyPostCommentPageQueryDTO replyPostCommentPageQueryDTO);

}

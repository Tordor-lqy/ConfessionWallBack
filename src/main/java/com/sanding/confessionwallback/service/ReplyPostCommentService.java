package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentPageQueryDTO;

import java.util.List;

public interface ReplyPostCommentService {
	void saveReplyComment(ReplyPostCommentDTO replyPostCommentDTO);

	PageResult getReplyPostComment(ReplyPostCommentPageQueryDTO replyPostCommentPageQueryDTO);

	void batchDeleteByPostReplyId(List<Long> replyIds);

	void batchDeleteByCommentId(List<Long> commentIds);
}

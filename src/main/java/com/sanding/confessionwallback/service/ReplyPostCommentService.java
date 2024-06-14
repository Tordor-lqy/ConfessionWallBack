package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentPageQueryDTO;

public interface ReplyPostCommentService {
	void saveReplyComment(ReplyPostCommentDTO replyPostCommentDTO);

	PageResult getReplyPostComment(ReplyPostCommentPageQueryDTO replyPostCommentPageQueryDTO);
}

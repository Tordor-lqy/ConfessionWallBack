package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentDTO;

public interface ReplyPostCommentService {
	void saveReplyComment(ReplyPostCommentDTO replyPostCommentDTO);
}

package com.sanding.confessionwallback.controller.admin;


import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.service.ReplyPostCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin/replyComment")
@Api("管理端回复相关接口")
public class AdminReplyPostCommentController {

	@Autowired
	private ReplyPostCommentService replyPostCommentService;

	@ApiOperation("根据评论id查询回复")
	@GetMapping
	public PageResult getReplyPostCommentsByPostCommentId(Long id, Integer p, Integer s) {
		log.info("查询评论id={}下的回复", id);
		return replyPostCommentService.getReplyPostCommentByCommentId(id, p, s);
	}

}

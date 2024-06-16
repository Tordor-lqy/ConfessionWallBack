package com.sanding.confessionwallback.controller.admin;


import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentPageQueryDTO;
import com.sanding.confessionwallback.service.ReplyPostCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@ApiOperation("批量删除回复")
	@DeleteMapping
	public Result batchDeleteByReply(@RequestParam List<Long> ids) {
		log.info("删除id={}下的回复", ids);
		replyPostCommentService.batchDeleteReplyCommentByReplyCommentId(ids);
		return Result.success();
	}

	@ApiOperation("多条件分页查询回复")
	@GetMapping("/list")
	public PageResult getReplyCommentsPage(@ModelAttribute ReplyPostCommentPageQueryDTO replyPostCommentPageQueryDTO) {
		log.info("查询{}下的回复", replyPostCommentPageQueryDTO);
		return replyPostCommentService.getReplyCommentsPage(replyPostCommentPageQueryDTO);

	}
}

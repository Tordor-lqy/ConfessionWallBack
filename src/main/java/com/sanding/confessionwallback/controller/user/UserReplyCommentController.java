package com.sanding.confessionwallback.controller.user;


import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.ReplyPostCommentPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.PostComment;
import com.sanding.confessionwallback.pojo.entity.ReplyPostComment;
import com.sanding.confessionwallback.service.PostCommentService;
import com.sanding.confessionwallback.service.ReplyPostCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/replyPostComment")
@Api("用户端评论回复相关接口")
public class UserReplyCommentController {

	@Autowired
	private ReplyPostCommentService replyPostCommentService;


	@ApiOperation("新增评论回复")
	@PostMapping
	public Result saveReplyComment(@RequestBody ReplyPostCommentDTO replyPostCommentDTO) {
		log.info("新增回复{}", replyPostCommentDTO);
		replyPostCommentService.saveReplyComment(replyPostCommentDTO);
		return Result.success();
	}

	@GetMapping
	@ApiOperation("查看自己发布的回复")
	public PageResult getMyComment(ReplyPostCommentPageQueryDTO replyPostCommentPageQueryDTO) {
		Long userId = BaseContext.getCurrentId();
		log.info("查看用户{}发布的回复", userId);
		replyPostCommentPageQueryDTO.setUserId(userId);
		PageResult pageResult = replyPostCommentService.getReplyPostComment(replyPostCommentPageQueryDTO);
		return pageResult;
	}

	@DeleteMapping
	@ApiOperation("批量删除自己发布的回复")
	public Result deleteMyReplyComment(@RequestParam List<Long> ids) {
		log.info("删除评论ids={}", ids);
		replyPostCommentService.batchDeleteByPostReplyId(ids);
		return Result.success();
	}
}

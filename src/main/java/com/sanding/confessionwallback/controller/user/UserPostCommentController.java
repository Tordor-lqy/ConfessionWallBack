package com.sanding.confessionwallback.controller.user;


import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;
import com.sanding.confessionwallback.service.PostCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/postComment")
@Api("用户端评论相关接口")
public class UserPostCommentController {


	@Autowired
	private PostCommentService postCommentService;

	@PostMapping
	@ApiOperation("发布帖子评论")
	public Result addPostComment(@RequestBody PostCommentDTO postCommentDTO) {
		log.info("新增帖子评论{}", postCommentDTO);
		postCommentService.savePostComment(postCommentDTO);
		return Result.success();
	}

	@GetMapping
	@ApiOperation("查看自己发布的评论")
	public PageResult getMyComment(PostCommentPageQueryDTO postCommentPageQueryDTO) {
		Long userId = BaseContext.getCurrentId();
		log.info("查看用户{}发布的评论", userId);
		postCommentPageQueryDTO.setUserId(userId);
		PageResult pageResult = postCommentService.getPostComment(postCommentPageQueryDTO);
		return pageResult;
	}

	@DeleteMapping
	@ApiOperation("批量删除自己发布的评论")
	public Result deleteMyComment(@RequestParam List<Long> ids) {
		log.info("删除评论ids={}", ids);
		postCommentService.batchDeleteByPostCommentId(ids);
		return Result.success();
	}
}

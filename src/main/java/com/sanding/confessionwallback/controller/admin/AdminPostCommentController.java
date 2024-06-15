package com.sanding.confessionwallback.controller.admin;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
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
@RequestMapping("/api/admin/comment")
@Api("管理端评论相关接口")
public class AdminPostCommentController {
	@Autowired
	private PostCommentService postCommentService;


	@GetMapping
	@ApiOperation("多条件查询评论")
	public PageResult getPostCommentList(@ModelAttribute PostCommentPageQueryDTO postCommentPageQueryDTO) {
		log.info("多条件查询评论{}", postCommentPageQueryDTO);
		return postCommentService.getPostComment(postCommentPageQueryDTO);
	}

	@DeleteMapping
	@ApiOperation("批量删除评论")
	public Result batchDeletePostComment(@RequestParam List<Long> ids) {
		log.info("删除评论{}", ids);
		postCommentService.batchDeleteByPostCommentIdFromAdmin(ids);
		return Result.success();
	}

}

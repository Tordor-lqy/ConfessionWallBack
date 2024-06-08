package com.sanding.confessionwallback.controller.user;

import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.service.PostCommentService;
import com.sanding.confessionwallback.service.PostService;
import com.sanding.confessionwallback.service.PostUserLikeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/post")
@Api("帖子操作相关接口")
@Slf4j
public class UserPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private PostUserLikeService postUserLikeService;
    // TODO 用户点赞帖子
    // TODO 用户取消点赞帖子
    // TODO 用户评论帖子
    // TODO 用户删除评论
    // TODO 用户新增帖子
    // TODO 用户删除帖子
    // TODO 查询自己发布的帖子(分页)
}

package com.sanding.confessionwallback.controller.admin;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.*;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Post;
import com.sanding.confessionwallback.pojo.entity.PostUserLike;
import com.sanding.confessionwallback.service.PostCommentService;
import com.sanding.confessionwallback.service.PostService;
import com.sanding.confessionwallback.service.PostUserLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/post")
@Api("帖子操作相关接口")
@Slf4j
public class AdminPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private PostUserLikeService postUserLikeService;

    // TODO 新增帖子
    //  List<String>
    //  (参数：圈子ID ， 话题数组 ，帖子内容 ， 帖子图片url数组base64编码 .... )
    //  判断话题是否存在，不存在则添加新话题
    //  然后添加话题和帖子的关系信息

    // TODO 删除帖子 将字段is_delete改为 1

    // TODO 修改帖子
    /** 条件查询帖子（分页 ， 包含标题搜索 and 圈子ID and 用户ID ....）
     * */
    @GetMapping
    @ApiOperation("分页条件查询帖子")
    public Result<PageResult> selectPosts(PostPageQueryDTO postPageQueryDTO){
        log.info("分页条件查询帖子{}",postPageQueryDTO);
        PageResult pageResult=postService.selectPosts(postPageQueryDTO);
        return Result.success(pageResult);
    }

    /**查询单个帖子详情
     * */
    @GetMapping("/{postId}")
    @ApiOperation("查询单个帖子详情")
    public Result<Post> getPostByPostId(@PathVariable Long postId) {
        log.info("查询id{} 帖子详情", postId);
        Post post=postService.getPostByPostId(postId);
        return Result.success(post);
    }
    /** 查看帖子评论(不考虑回复 ， 分页)
     * */
    @GetMapping("/comment")
    @ApiOperation("分页查看帖子评论")
    public Result<PageResult> selectPostComments(PostCommentPageQueryDTO postCommentPageQueryDTO){
        log.info("分页查看帖子{} 的评论",postCommentPageQueryDTO);
        PageResult pageResult=postCommentService.selectPostComments(postCommentPageQueryDTO);
        return Result.success(pageResult);
    }
    /** 根据评论ID查询回复(分页)
     * */
    @GetMapping("/replyComment")
    @ApiOperation("根据评论ID查询回复")
    public Result<PageResult> selectReplyComments(PostCommentPageQueryDTO postCommentPageQueryDTO){
        log.info("根据评论ID:{}查询回复",postCommentPageQueryDTO.getPostCommentId());
        PageResult pageResult=postCommentService.selectReplyComments(postCommentPageQueryDTO);
        return Result.success(pageResult);
    }

    /** 查询帖子的点赞用户列表(分页)
     * */
    @GetMapping("/userLike")
    @ApiOperation("查询帖子的点赞用户列表")
    public Result<PageResult> selectPostUserLike(PostPageQueryDTO postPageQueryDTO){
        log.info("查询帖子id:{} 的点赞用户列表",postPageQueryDTO.getPostId());
        PageResult pageResult=postUserLikeService.selectPostUserLike(postPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 移除某用户对某帖子的点赞
     */
    @DeleteMapping("/like")
    public Result delLikeTopic(PostUserLikeDTO postUserLikeDTO){
        log.info("移除用户:{}对帖子:{}的点赞",postUserLikeDTO.getUserId(),postUserLikeDTO.getPostId());
        postUserLikeService.AdminDelLikeTopic(postUserLikeDTO);
        return Result.success();
    }


}

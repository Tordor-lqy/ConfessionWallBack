package com.sanding.confessionwallback.controller.user;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostDTO;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.PostUserLikeDTO;
import com.sanding.confessionwallback.service.PostCommentService;
import com.sanding.confessionwallback.service.PostService;
import com.sanding.confessionwallback.service.PostUserLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户点赞帖子
     * @return
     */
    @PostMapping("/like")
    public Result likeTopic(Long postId){
        log.info("用户点赞帖子的id:{}",postId);
        postUserLikeService.likePost(postId);
        return  Result.success();
    }

    /**
     * 用户取消点赞评论
     * @param postId
     * @return
     */
    @DeleteMapping("like")
    public Result delLikeTopic(Long postId){
        log.info("用户取消点赞帖子的id:{}",postId);
        postUserLikeService.delLikeTopic(postId);
       return Result.success();
    }

    ///**
    // *  用户评论帖子
    // * @param postCommentDTO
    // * @return
    // */
    //@PostMapping("/commentTopic")
    //public  Result commentTopic(@RequestBody PostCommentDTO postCommentDTO){
    //    log.info("用户评论帖子的内容:{}",postCommentDTO);
    //    postCommentService.commentTopic(postCommentDTO);
    //    return  Result.success();
    //}

    ///**
    // * 用户删除评论
    // * @param postCommentId
    // * @return
    // */
    //@DeleteMapping("/commentTopic")
    //public   Result delCommentTopic(Long postCommentId){
    //    log.info("用户删除评论的id:{}",postCommentId);
    //    postCommentService.delCommentTopic(postCommentId);
    //    return  Result.success();
    //}

    /**
     * 用户新增帖子
     * @param postDTO
     * @return
     */
    @PostMapping
    public Result savePostTopic(@RequestBody PostDTO postDTO){
        log.info("用户新增帖子：{}",postDTO);
        postService.savePostTopic(postDTO);
        return  Result.success();
    }

    /**
     * 用户删除帖子
     * @param postId
     * @return
     */
    @DeleteMapping
    public Result delPost(Long postId){
        log.info("用户删除帖子的id:{}",postId);
        postService.userDelPost(postId);
        return Result.success();
    }
    /**查询自己发布的帖子(分页)
    * */
    @GetMapping
    @ApiOperation("查询自己发布的帖子")
    public Result<PageResult> selectMyPost(PostPageQueryDTO postPageQueryDTO){
        log.info("查询自己发布的帖子");
        PageResult pageResult=postService.selectMyPost(postPageQueryDTO);
        return Result.success(pageResult);
    }
}

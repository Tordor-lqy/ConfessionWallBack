package com.sanding.confessionwallback.controller.admin;

import com.sanding.confessionwallback.service.PostCommentService;
import com.sanding.confessionwallback.service.PostService;
import com.sanding.confessionwallback.service.PostUserLikeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    // TODO 条件查询帖子（分页 ， 包含标题搜索 and 圈子ID and 用户ID ....）

    // TODO 查询单个帖子详情

    // TODO 查看帖子评论(不考虑回复 ， 分页)

    // TODO 根据评论ID查询回复(分页)

    // TODO 查询帖子的点赞用户列表(分页)

    // TODO 移除某用户对某帖子的点赞


}

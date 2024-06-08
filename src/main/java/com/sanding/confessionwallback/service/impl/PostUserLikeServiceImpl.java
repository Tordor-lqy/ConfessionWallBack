package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.exception.UserLikeException;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.PostMapper;
import com.sanding.confessionwallback.mapper.PostUserLikeMapper;
import com.sanding.confessionwallback.mapper.UserMapper;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Post;
import com.sanding.confessionwallback.pojo.entity.PostComment;
import com.sanding.confessionwallback.pojo.entity.PostUserLike;
import com.sanding.confessionwallback.pojo.entity.User;
import com.sanding.confessionwallback.service.PostUserLikeService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostUserLikeServiceImpl implements PostUserLikeService {
    @Autowired
    private PostUserLikeMapper postUserLikeMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户点赞帖子
     *
     * @param postId
     */
    @Override
    @Transactional
    public void likePost(Long postId) {
        Long userId = BaseContext.getCurrentId();
        //判断用户是否已点赞
        LambdaQueryWrapper<PostUserLike> wr=new LambdaQueryWrapper<PostUserLike>()
                .eq(PostUserLike::getPostId,postId)
                .eq(PostUserLike::getUserId,userId);
        List<PostUserLike> list = postUserLikeMapper.selectList(wr);
        if(list.size()>0){
            throw  new UserLikeException("您已点赞该帖子");
        }else {
            //先添加用户帖子关系
            PostUserLike postUserLike = new PostUserLike();
            postUserLike.setPostId(postId);
            postUserLike.setUserId(userId);
            postUserLikeMapper.insert(postUserLike);
            //先获取帖子点赞数
            LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<Post>()
                    .select(Post::getPostLikeCount)
                    .eq(Post::getPostId, postId)
                    .eq(Post::getUserId, userId);
            Post post = postMapper.selectOne(wrapper);
            Long count = post.getPostLikeCount() + 1;
            //帖子点赞数加一
            LambdaUpdateWrapper<Post> wrapper1 = new LambdaUpdateWrapper<Post>()
                    .set(Post::getPostLikeCount, count)
                    .eq(Post::getPostId, postId);
            postMapper.update(wrapper1);
        }
    }

    /**
     * 用户取消点赞帖子
     *
     * @param postId
     */
    @Override
    public void delLikeTopic(Long postId) {
        Long userId = BaseContext.getCurrentId();

        //判断用户是否已点赞
        LambdaQueryWrapper<PostUserLike> wr=new LambdaQueryWrapper<PostUserLike>()
                .eq(PostUserLike::getPostId,postId)
                .eq(PostUserLike::getUserId,userId);
        List<PostUserLike> list = postUserLikeMapper.selectList(wr);
        if(list.size()>0){
            //解除关系
            Long postUserLikeId = list.get(0).getPostUserLikeId();
            postUserLikeMapper.deleteById(postUserLikeId);
            //帖子点赞数减一
            postMapper.decrementLikeCount(postId);
        }else{
            throw  new UserLikeException("您并没有点赞该帖子");
        }
    }

    /**
     * 查询帖子的点赞用户列表(分页)
     */
    @Override
    public PageResult selectPostUserLike(PostPageQueryDTO postPageQueryDTO) {
        //根据帖子id查询点赞的用户id
        // 创建查询条件
        /**select userId from postUserLike表 where postId=帖子id
         * */
        LambdaQueryWrapper<PostUserLike> wrapper = new LambdaQueryWrapper<PostUserLike>()
                .select(PostUserLike::getUserId)
                .eq(PostUserLike::getPostId, postPageQueryDTO.getPostId());
        List<Long> userIds = postUserLikeMapper.selectObjs(wrapper);
        // 创建分页对象
        Page<User> page = new Page<>(postPageQueryDTO.getP(), postPageQueryDTO.getS());
        // 创建查询条件
        //根据用户id查询用户
        /**select * from user表 where userId in (用户id)
         * */
        LambdaQueryWrapper<User> wrapper1 = new LambdaQueryWrapper<User>();
        if (userIds.isEmpty()) {
            //无点赞用户
            return new PageResult(0, List.of());
        } else {
            wrapper1.in(User::getUserId,userIds);
            // 执行分页查询
            Page<User> resultPage = userMapper.selectPage(page, wrapper1);
            // 返回结果
            return new PageResult(resultPage.getTotal(), resultPage.getRecords());

        }

    }
}

package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.exception.UserLikeException;
import com.sanding.confessionwallback.mapper.PostMapper;
import com.sanding.confessionwallback.mapper.PostUserLikeMapper;
import com.sanding.confessionwallback.pojo.entity.Post;
import com.sanding.confessionwallback.pojo.entity.PostUserLike;
import com.sanding.confessionwallback.service.PostUserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostUserLikeServiceImpl implements PostUserLikeService {
    @Autowired
    private PostUserLikeMapper postUserLikeMapper;
    @Autowired
    private PostMapper postMapper;
    /**
     * 用户点赞帖子
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
}

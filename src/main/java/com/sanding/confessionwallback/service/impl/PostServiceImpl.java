package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.*;
import com.sanding.confessionwallback.pojo.dto.PostDTO;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.*;
import com.sanding.confessionwallback.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TopicPostMapper topicPostMapper;
    @Autowired
    private CircleMapper circleMapper;
    @Autowired
    private PostCommentMapper postCommentMapper;
    @Autowired
    private PostUserLikeMapper postUserLikeMapper;

    /**
     * 条件查询帖子（分页 ， 包含标题搜索 and 圈子ID and 用户ID ....）
     */
    @Override
    @Transactional
    public PageResult selectPosts(PostPageQueryDTO postPageQueryDTO) {
        // 创建分页对象
        Page<Post> page = new Page<>(postPageQueryDTO.getP(), postPageQueryDTO.getS());
        // 创建查询条件
        QueryWrapper<Post> wrapper = new QueryWrapper<Post>();
        //开始时间
        if (postPageQueryDTO.getBeginTime() != null) {
            wrapper.ge(PostPageQueryDTO.BEGIN_TIME, postPageQueryDTO.getBeginTime());
        }
        //结束时间
        if (postPageQueryDTO.getEndTime() != null) {
            wrapper.le(PostPageQueryDTO.END_TIME, postPageQueryDTO.getEndTime());
        }
        // 帖子id
        if (postPageQueryDTO.getPostId() != null) {
            wrapper.eq(PostPageQueryDTO.POST_ID, postPageQueryDTO.getPostId());
            // 执行分页查询
            Page<Post> resultPage = postMapper.selectPage(page, wrapper);
            // 返回结果
            return new PageResult(resultPage.getTotal(), resultPage.getRecords());
        }
        //圈子Id
        if (postPageQueryDTO.getCircleId() != null) {
            wrapper.eq(PostPageQueryDTO.CIRCLE_ID, postPageQueryDTO.getCircleId());
        }
        //用户Id
        if (postPageQueryDTO.getUserId() != null) {
            wrapper.eq(PostPageQueryDTO.USER_ID, postPageQueryDTO.getUserId());
        }
        // 分组ID
        if (postPageQueryDTO.getGroupId() != null) {
            wrapper.eq(PostPageQueryDTO.GROUP_ID, postPageQueryDTO.getGroupId());
        }
        //
        if (postPageQueryDTO.getTopicId() != null) {
            wrapper.eq(PostPageQueryDTO.TOPIC_ID, postPageQueryDTO.getTopicId());
        }
        // 标题
        if (postPageQueryDTO.getPostTitle() != null && !postPageQueryDTO.getPostTitle().isEmpty()) {
            wrapper.like(PostPageQueryDTO.POST_TITLE, postPageQueryDTO.getPostTitle());
        }
        // circleName
        if (postPageQueryDTO.getCircleName() != null && !postPageQueryDTO.getCircleName().isEmpty()) {
            wrapper.like(PostPageQueryDTO.CIRCLE_NAME, postPageQueryDTO.getCircleName());
        }
        // groupName
        if (postPageQueryDTO.getGroupName() != null && !postPageQueryDTO.getGroupName().isEmpty()) {
            wrapper.like(PostPageQueryDTO.GROUP_NAME, postPageQueryDTO.getGroupName());
        }
        // topicName
        if (postPageQueryDTO.getTopicName() != null && !postPageQueryDTO.getTopicName().isEmpty()) {
            wrapper.like(PostPageQueryDTO.TOPIC_NAME, postPageQueryDTO.getTopicName());
        }
        // delete
        if(postPageQueryDTO.getIsDelete() != null){
            wrapper.eq(PostPageQueryDTO.IS_DELETE, postPageQueryDTO.getIsDelete());
        }
        //话题
//        if(postPageQueryDTO.getTopicList() != null && !postPageQueryDTO.getTopicList().isEmpty()){
//            //根据话题名字找话题Id
//            /**select topicId from topic表 where topicName=话题名
//             * */
//            LambdaQueryWrapper<Topic> wrapper1=new LambdaQueryWrapper<Topic>()
//                    .select(Topic::getTopicId)
//                    .in(Topic::getTopicName,postPageQueryDTO.getTopicList());
//            List<Long> topicIds=topicMapper.selectObjs(wrapper1);
//            //根据话题Id找对应的帖子id
//            /**select postId from post表 where topicId=话题id
//             * */
//            LambdaQueryWrapper<TopicPost> wrapper2=new LambdaQueryWrapper<TopicPost>()
//                    .in(TopicPost::getTopicId,topicIds);
//            List<Long> postIds=topicPostMapper.selectObjs(wrapper2);
//            //根据帖子Id找帖子
//            /**select post from post表 where postId in 帖子id
//             * */
//            if (!postIds.isEmpty()) {
//                //话题有帖子
//                wrapper.in(PostPageQueryDTO.POST_ID, postIds);
//            }
//            //话题没帖子不构建
//        }
        // 执行分页查询
        Page<Post> resultPage = postMapper.selectPage(page, wrapper);
        // 返回结果
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }

    /**
     * 查询单个帖子详情
     */
    @Override
    public Post getPostByPostId(Long postId) {
        Post post = postMapper.selectById(postId);
        return post;
    }

    /**
     * 用户新增帖子
     * <p>
     * //  List<String>
     * //  (参数：圈子ID ， 话题数组 ，帖子内容 ， 帖子图片url数组base64编码 .... )
     * //  判断话题是否存在，不存在则添加新话题
     * //  然后添加话题和帖子的关系信息
     *
     * @param postDTO
     */
    //TODO 话题字段
    @Override
    public void savePostTopic(PostDTO postDTO) {
        Long userId = BaseContext.getCurrentId();
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        post.setPostCreateTime(LocalDateTime.now());
        post.setPostUpdateTime(LocalDateTime.now());
        post.setPostCommentCount(Post.MO_COMMENT);
        post.setPostLikeCount(Post.MO_LIKE);
        post.setUserId(userId);
        postMapper.insert(post);
        // 判断话题是否存在，不存在则添加新话题
        List<String> topicList = postDTO.getTopicList();
        for (String topic :
                topicList) {
            LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<Topic>()
                    .eq(Topic::getTopicName, topic);
            Topic tp = topicMapper.selectOne(wrapper);
            if (tp == null) {
                //创建新话题
                Topic topic1 = new Topic();
                topic1.setTopicName(topic);
                topic1.setTopicPostCount(Topic.MO_TPCOUNT);
                topicMapper.insert(topic1);
                //创建话题帖子关系
                TopicPost topicPost = new TopicPost();
                topicPost.setPostId(post.getPostId());
                topicPost.setTopicId(topic1.getTopicId());
                topicPostMapper.insert(topicPost);
            } else {
                //话题对应帖子数量加一
                Long topicPostCount = tp.getTopicPostCount() + 1;

                LambdaUpdateWrapper<Topic> wrapper1 = new LambdaUpdateWrapper<Topic>()
                        .set(Topic::getTopicPostCount, topicPostCount)
                        .eq(Topic::getTopicId, tp.getTopicId());
                topicMapper.update(wrapper1);
            }
        }

        //新增帖子后 圈子帖子数加一
        circleMapper.insertCircleTopicCount(post.getCircleId());
    }

    /**
     * 删除帖子
     *
     * @param postId
     */
    @Override
    public void delPost(Long postId) {
        //获取圈子所有内容
        Post post = postMapper.selectById(postId);
        //删除帖子评论
        LambdaQueryWrapper<PostComment> wrappe = new LambdaQueryWrapper<PostComment>()
                .eq(PostComment::getPostId, postId);
        postCommentMapper.delete(wrappe);
        //删除所有点赞
        LambdaQueryWrapper<PostUserLike> wra = new LambdaQueryWrapper<PostUserLike>()
                .eq(PostUserLike::getPostId, postId);
        postUserLikeMapper.delete(wra);
        //减少话题帖子数量，为0时删除话题。
        List<Long> topicId = topicPostMapper.selectList(new LambdaQueryWrapper<TopicPost>()
                .eq(TopicPost::getPostId, postId)).stream().map(TopicPost::getTopicId).collect(Collectors.toList());
        for (Long topicid : topicId) {
            Topic topic = topicMapper.selectById(topicid);
            Long topicPostCount = topic.getTopicPostCount();
            if (topicPostCount > 1) {
                LambdaUpdateWrapper<Topic> wrapper1 = new LambdaUpdateWrapper<Topic>()
                        .set(Topic::getTopicPostCount, topicPostCount - 1)
                        .eq(Topic::getTopicId, topicid);
                topicMapper.update(wrapper1);
            } else {
                topicMapper.deleteById(topicid);
            }
        }
        //解开关系
        if (!topicId.isEmpty()) {
            LambdaQueryWrapper<TopicPost> wrapper = new LambdaQueryWrapper<TopicPost>()
                    .in(TopicPost::getTopicId, topicId)
                    .eq(TopicPost::getPostId, postId);
            topicPostMapper.delete(wrapper);
        }
        //圈子帖子数量减一
        Long circleId = post.getCircleId();
        circleMapper.reducePostCount(circleId);
        //最后删除帖子
        postMapper.deleteById(postId);
    }

    /**
     * 修改帖子
     *
     * @param postDTO
     */
    @Override
    public void update(PostDTO postDTO) {
        //更新帖子信息
        LambdaUpdateWrapper<Post> wrapper = new LambdaUpdateWrapper<Post>()
                .set(postDTO.getCircleId() != null, Post::getCircleId, postDTO.getCircleId())
                .set(postDTO.getUserId() != null, Post::getUserId, postDTO.getUserId())
                .set(Post::getPostUpdateTime, LocalDateTime.now())
                .set(postDTO.getPostImgUrls() != null && postDTO.getPostImgUrls() != "", Post::getPostImgUrls, postDTO.getPostImgUrls())
                .set(postDTO.getPostContent() != null && postDTO.getPostContent() != "", Post::getPostContent, postDTO.getPostContent())
                .eq(Post::getPostId, postDTO.getPostId());
        postMapper.update(wrapper);
        //删除所有话题，重新添加

        //减少话题帖子数量，为0时删除话题。
        List<Long> topicId = topicPostMapper.selectList(new LambdaQueryWrapper<TopicPost>()
                .eq(TopicPost::getPostId, postDTO.getPostId())).stream().map(TopicPost::getTopicId).collect(Collectors.toList());
        for (Long topicid : topicId) {
            Topic topic = topicMapper.selectById(topicid);
            Long topicPostCount = topic.getTopicPostCount();
            if (topicPostCount > 1) {
                LambdaUpdateWrapper<Topic> wrapper1 = new LambdaUpdateWrapper<Topic>()
                        .set(Topic::getTopicPostCount, topicPostCount - 1)
                        .eq(Topic::getTopicId, topicid);
                topicMapper.update(wrapper1);
            } else {
                topicMapper.deleteById(topicid);
            }
        }
        //解开关系
        if (!topicId.isEmpty()) {
            LambdaQueryWrapper<TopicPost> wra = new LambdaQueryWrapper<TopicPost>()
                    .in(TopicPost::getTopicId, topicId)
                    .eq(TopicPost::getPostId, postDTO.getPostId());
            topicPostMapper.delete(wra);
        }
        //重新添加
        List<String> topicList = postDTO.getTopicList();
        for (String topic :
                topicList) {
            LambdaQueryWrapper<Topic> wr = new LambdaQueryWrapper<Topic>()
                    .eq(Topic::getTopicName, topic);
            Topic tp = topicMapper.selectOne(wr);
            if (tp == null) {
                //创建新话题
                Topic topic1 = new Topic();
                topic1.setTopicName(topic);
                topic1.setTopicPostCount(Topic.MO_TPCOUNT);
                topicMapper.insert(topic1);
                //创建话题帖子关系
                TopicPost topicPost = new TopicPost();
                topicPost.setPostId(postDTO.getPostId());
                topicPost.setTopicId(topic1.getTopicId());
                topicPostMapper.insert(topicPost);
            } else {
                //话题对应帖子数量加一
                Long topicPostCount = tp.getTopicPostCount() + 1;

                LambdaUpdateWrapper<Topic> wrapper1 = new LambdaUpdateWrapper<Topic>()
                        .set(Topic::getTopicPostCount, topicPostCount)
                        .eq(Topic::getTopicId, tp.getTopicId());
                topicMapper.update(wrapper1);
            }
        }
    }

    /**
     * 用户查询自己的帖子
     */
    @Override
    public PageResult selectMyPost(PostPageQueryDTO postPageQueryDTO) {
        Long userId = BaseContext.getCurrentId();
        //创建分页对象
        Page<Post> page = new Page<>(postPageQueryDTO.getP(), postPageQueryDTO.getS());
        // 创建查询条件
        /**select * from post表 where userId=用户id
         * */
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<Post>()
                .eq(Post::getUserId, userId);
        // 执行分页查询
        Page<Post> resultPage = postMapper.selectPage(page, wrapper);
        // 返回结果
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }
}

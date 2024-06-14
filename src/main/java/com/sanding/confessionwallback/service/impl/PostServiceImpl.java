package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.exception.BaseException;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.*;
import com.sanding.confessionwallback.pojo.dto.PostDTO;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.*;
import com.sanding.confessionwallback.service.PostService;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private GroupMapper groupMapper;
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
            LambdaQueryWrapper<Circle> wrapper1 = new LambdaQueryWrapper<Circle>();
            List<Long> circleIdList = circleMapper.selectList(wrapper1.like(Circle::getCircleName, postPageQueryDTO.getCircleName())).stream().map(Circle::getCircleId).collect(Collectors.toList());
            if (circleIdList.isEmpty()) {
                return new PageResult(0, new ArrayList<>());
            }
            wrapper.in(PostPageQueryDTO.CIRCLE_ID, circleIdList);
        }
        // groupName
        if (postPageQueryDTO.getGroupName() != null && !postPageQueryDTO.getGroupName().isEmpty()) {
            LambdaQueryWrapper<Group> wrapper1 = new LambdaQueryWrapper<Group>();
            List<Long> groupIdList = groupMapper.selectList(wrapper1.like(Group::getGroupName, postPageQueryDTO.getGroupName())).stream().map(Group::getGroupId).collect(Collectors.toList());
            if (groupIdList.isEmpty()) {
                return new PageResult(0, new ArrayList<>());
            }
            wrapper.in(PostPageQueryDTO.GROUP_ID, groupIdList);
        }
        // topicName
        if (postPageQueryDTO.getTopicName() != null && !postPageQueryDTO.getTopicName().isEmpty()) {
            LambdaQueryWrapper<Topic> wrapper1 = new LambdaQueryWrapper<Topic>();
            List<Long> topicIdList = topicMapper.selectList(wrapper1.like(Topic::getTopicName, postPageQueryDTO.getTopicName())).stream().map(Topic::getTopicId).collect(Collectors.toList());
            if (topicIdList.isEmpty()) {
                return new PageResult(0, new ArrayList<>());
            }
            wrapper.in(PostPageQueryDTO.TOPIC_ID, topicIdList);
        }
        // delete
        if (postPageQueryDTO.getIsDelete() != null) {
            wrapper.eq(PostPageQueryDTO.IS_DELETE, postPageQueryDTO.getIsDelete());
        }
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


    @Override
    public void adminSavePOST(PostDTO postDTO) {
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        Topic topic = topicMapper.selectOne(new LambdaQueryWrapper<Topic>()
                .eq(Topic::getGroupId, postDTO.getGroupId())
                .eq(Topic::getTopicName, postDTO.getTopicName())
                .eq(Topic::getIsDelete, 0));
        if (topic == null) {
            topic = new Topic();
            topic.setTopicName(postDTO.getTopicName());
            Group group = groupMapper.selectById(postDTO.getGroupId());
            topic.setGroupId(postDTO.getGroupId());
            topic.setTopicPostCount(1L);
            topic.setCircleId(group.getCircleId());
            topicMapper.insert(topic);
        }

        post.setPostCreateTime(LocalDateTime.now());
        post.setPostUpdateTime(LocalDateTime.now());
        post.setPostCommentCount(Post.MO_COMMENT);
        post.setPostLikeCount(Post.MO_LIKE);
        post.setCircleId(topic.getCircleId());
        post.setGroupId(topic.getGroupId());
        post.setTopicId(topic.getTopicId());
        post.setIsDelete(0);
        postMapper.insert(post);

    }


    @Override
    public void savePostTopic(PostDTO postDTO) {
        Long userId = BaseContext.getCurrentId();
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        Topic topic = topicMapper.selectOne(new LambdaQueryWrapper<Topic>()
                .eq(Topic::getGroupId, postDTO.getGroupId())
                .eq(Topic::getTopicName, postDTO.getTopicName())
                .eq(Topic::getIsDelete, 0));

        if (topic == null) {
            topic = new Topic();
            topic.setTopicName(postDTO.getTopicName());
            Group group = groupMapper.selectById(postDTO.getGroupId());
            topic.setGroupId(postDTO.getGroupId());
            topic.setTopicPostCount(1L);
            topic.setCircleId(group.getCircleId());
            topicMapper.insert(topic);
        }
        post.setUserId(userId);
        post.setPostCreateTime(LocalDateTime.now());
        post.setPostUpdateTime(LocalDateTime.now());
        post.setPostCommentCount(Post.MO_COMMENT);
        post.setPostLikeCount(Post.MO_LIKE);
        post.setCircleId(topic.getCircleId());
        post.setGroupId(topic.getGroupId());
        post.setTopicId(topic.getTopicId());
        post.setIsDelete(0);
        postMapper.insert(post);

    }

    @Override
    public void adminUpdate(PostDTO postDTO) {
        Post post = postMapper.selectById(postDTO.getPostId());


        Topic oldTopic = topicMapper.selectOne(new LambdaQueryWrapper<Topic>()
                .eq(Topic::getGroupId, post.getGroupId())
                .eq(Topic::getTopicId, post.getTopicId()));
        Topic topic = topicMapper.selectOne(new LambdaQueryWrapper<Topic>()
                .eq(Topic::getGroupId, postDTO.getGroupId())
                .eq(Topic::getTopicName, postDTO.getTopicName()));

        if (oldTopic != null) {
            // 更新帖子旧话题数量
            if (!oldTopic.getTopicName().equals(postDTO.getTopicName()) || !Objects.equals(post.getPostId(), postDTO.getGroupId())) {
                oldTopic.setTopicPostCount(oldTopic.getTopicPostCount() - 1);
                topicMapper.updateById(oldTopic);
            }
        }
        // 没有话题则创建新话题
        if (topic == null) {
            topic = new Topic();
            topic.setTopicName(postDTO.getTopicName());
            Group group = groupMapper.selectById(postDTO.getGroupId());
            topic.setGroupId(postDTO.getGroupId());
            topic.setCircleId(group.getCircleId());
            topic.setTopicPostCount(1L);
            topicMapper.insert(topic);
        }
        // 设置新话题
        BeanUtils.copyProperties(postDTO, post);
        post.setTopicId(topic.getTopicId());
        post.setPostUpdateTime(LocalDateTime.now());
        postMapper.updateById(post);
    }

    /**
     * 删除帖子
     *
     * @param postId
     */
    @Override
    public void userDelPost(Long postId) {
        Long userId = BaseContext.getCurrentId();
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BaseException("没有这个帖子");
        }
        if (post.getUserId() != userId) {
            throw new BaseException("无权限删除帖子");
        }
        // 删除所有评论
        LambdaQueryWrapper<PostComment> wrappe = new LambdaQueryWrapper<PostComment>()
                .eq(PostComment::getPostId, postId);
        postCommentMapper.delete(wrappe);
        LambdaQueryWrapper<PostUserLike> wra = new LambdaQueryWrapper<PostUserLike>()
                .eq(PostUserLike::getPostId, postId);
        postUserLikeMapper.delete(wra);
        // 话题帖子数量-1
        Topic topic = topicMapper.selectOne(new LambdaQueryWrapper<Topic>().eq(Topic::getTopicId, post.getTopicId()));
        topic.setTopicPostCount(topic.getTopicPostCount() - 1);
        topicMapper.updateById(topic);
        //圈子帖子数量减一
        Long circleId = post.getCircleId();
        circleMapper.reducePostCount(circleId);
        //最后删除帖子
        postMapper.update(new LambdaUpdateWrapper<Post>().set(Post::getIsDelete, 1).eq(Post::getPostId, postId));
    }

    @Override
    public void adminDelPost(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BaseException("没有这个帖子");
        }
        // 删除所有评论
        LambdaQueryWrapper<PostComment> wrappe = new LambdaQueryWrapper<PostComment>()
                .eq(PostComment::getPostId, postId);
        postCommentMapper.delete(wrappe);
        LambdaQueryWrapper<PostUserLike> wra = new LambdaQueryWrapper<PostUserLike>()
                .eq(PostUserLike::getPostId, postId);
        postUserLikeMapper.delete(wra);
        // 话题帖子数量-1
        Topic topic = topicMapper.selectOne(new LambdaQueryWrapper<Topic>().eq(Topic::getTopicId, post.getTopicId()));
        topic.setTopicPostCount(topic.getTopicPostCount() - 1);
        topicMapper.updateById(topic);
        //圈子帖子数量减一
        Long circleId = post.getCircleId();
        circleMapper.reducePostCount(circleId);
        //最后删除帖子
        postMapper.update(new LambdaUpdateWrapper<Post>().set(Post::getIsDelete, 1).eq(Post::getPostId, postId));
    }

    /**
     * 修改帖子
     *
     * @param postDTO
     */
    @Override
    public void update(PostDTO postDTO) {
//        //更新帖子信息
//        LambdaUpdateWrapper<Post> wrapper = new LambdaUpdateWrapper<Post>()
//                .set(postDTO.getCircleId() != null, Post::getCircleId, postDTO.getCircleId())
//                .set(postDTO.getUserId() != null, Post::getUserId, postDTO.getUserId())
//                .set(Post::getPostUpdateTime, LocalDateTime.now())
//                .set(postDTO.getPostImgUrls() != null && postDTO.getPostImgUrls() != "", Post::getPostImgUrls, postDTO.getPostImgUrls())
//                .set(postDTO.getPostContent() != null && postDTO.getPostContent() != "", Post::getPostContent, postDTO.getPostContent())
//                .eq(Post::getPostId, postDTO.getPostId());
//        postMapper.update(wrapper);
//        //删除所有话题，重新添加
//
//        //减少话题帖子数量，为0时删除话题。
//        List<Long> topicId = topicPostMapper.selectList(new LambdaQueryWrapper<TopicPost>()
//                .eq(TopicPost::getPostId, postDTO.getPostId())).stream().map(TopicPost::getTopicId).collect(Collectors.toList());
//        for (Long topicid : topicId) {
//            Topic topic = topicMapper.selectById(topicid);
//            Long topicPostCount = topic.getTopicPostCount();
//            if (topicPostCount > 1) {
//                LambdaUpdateWrapper<Topic> wrapper1 = new LambdaUpdateWrapper<Topic>()
//                        .set(Topic::getTopicPostCount, topicPostCount - 1)
//                        .eq(Topic::getTopicId, topicid);
//                topicMapper.update(wrapper1);
//            } else {
//                topicMapper.deleteById(topicid);
//            }
//        }
//        //解开关系
//        if (!topicId.isEmpty()) {
//            LambdaQueryWrapper<TopicPost> wra = new LambdaQueryWrapper<TopicPost>()
//                    .in(TopicPost::getTopicId, topicId)
//                    .eq(TopicPost::getPostId, postDTO.getPostId());
//            topicPostMapper.delete(wra);
//        }
//        //重新添加
//        List<String> topicList = postDTO.getTopicList();
//        for (String topic :
//                topicList) {
//            LambdaQueryWrapper<Topic> wr = new LambdaQueryWrapper<Topic>()
//                    .eq(Topic::getTopicName, topic);
//            Topic tp = topicMapper.selectOne(wr);
//            if (tp == null) {
//                //创建新话题
//                Topic topic1 = new Topic();
//                topic1.setTopicName(topic);
//                topic1.setTopicPostCount(Topic.MO_TPCOUNT);
//                topicMapper.insert(topic1);
//                //创建话题帖子关系
//                TopicPost topicPost = new TopicPost();
//                topicPost.setPostId(postDTO.getPostId());
//                topicPost.setTopicId(topic1.getTopicId());
//                topicPostMapper.insert(topicPost);
//            } else {
//                //话题对应帖子数量加一
//                Long topicPostCount = tp.getTopicPostCount() + 1;
//
//                LambdaUpdateWrapper<Topic> wrapper1 = new LambdaUpdateWrapper<Topic>()
//                        .set(Topic::getTopicPostCount, topicPostCount)
//                        .eq(Topic::getTopicId, tp.getTopicId());
//                topicMapper.update(wrapper1);
//            }
//        }
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
                .eq(Post::getUserId, userId)
                .eq(Post::getIsDelete, 0);
        // 执行分页查询
        Page<Post> resultPage = postMapper.selectPage(page, wrapper);
        // 返回结果
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }
}

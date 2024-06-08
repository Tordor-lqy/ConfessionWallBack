package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.PostMapper;
import com.sanding.confessionwallback.mapper.TopicMapper;
import com.sanding.confessionwallback.mapper.TopicPostMapper;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.*;
import com.sanding.confessionwallback.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TopicPostMapper topicPostMapper;

    /** 条件查询帖子（分页 ， 包含标题搜索 and 圈子ID and 用户ID ....）
     * */
    @Override
    @Transactional
    public PageResult selectPosts(PostPageQueryDTO postPageQueryDTO) {
        // 创建分页对象
        Page<Post> page = new Page<>(postPageQueryDTO.getP(), postPageQueryDTO.getS());
        // 创建查询条件
        QueryWrapper<Post> wrapper = new QueryWrapper<>();
        //圈子Id
        if (postPageQueryDTO.getCircleId() != null) {
            wrapper.eq(PostPageQueryDTO.CIRCLE_ID, postPageQueryDTO.getCircleId());
        }
        //用户Id
        if (postPageQueryDTO.getUserId() != null) {
            wrapper.eq(PostPageQueryDTO.USER_ID, postPageQueryDTO.getUserId());
        }
        //开始时间
        if (postPageQueryDTO.getBeginTime() != null) {
            wrapper.ge(PostPageQueryDTO.BEGIN_TIME, postPageQueryDTO.getBeginTime());
        }
        //结束时间
        if (postPageQueryDTO.getEndTime() != null) {
            wrapper.le(PostPageQueryDTO.END_TIME, postPageQueryDTO.getEndTime());
        }
        //话题
        if (postPageQueryDTO.getTopicList() != null && !postPageQueryDTO.getTopicList().isEmpty()) {
            //根据话题名字找话题Id
            /**select topicId from topic表 where topicName=话题名
             * */
            LambdaQueryWrapper<Topic> wrapper1 = new LambdaQueryWrapper<Topic>()
                    .select(Topic::getTopicId)
                    .in(Topic::getTopicName, postPageQueryDTO.getTopicList());
            List<Long> topicIds = topicMapper.selectObjs(wrapper1);
            //根据话题Id找对应的帖子id
            /**select postId from post表 where topicId=话题id
             * */
            LambdaQueryWrapper<TopicPost> wrapper2 = new LambdaQueryWrapper<TopicPost>()
                    .in(TopicPost::getTopicId, topicIds);
            List<Long> postIds = topicPostMapper.selectObjs(wrapper2);
            //根据帖子Id找帖子
            /**select post from post表 where postId in 帖子id
             * */
            if (!postIds.isEmpty()) {
                //话题有帖子
                wrapper.in(PostPageQueryDTO.POST_ID, postIds);
            }
            //话题没帖子不构建
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
}

package com.sanding.confessionwallback.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.exception.TopicException;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.PostMapper;
import com.sanding.confessionwallback.mapper.TopicMapper;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.TopicDTO;
import com.sanding.confessionwallback.pojo.dto.TopicPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Group;
import com.sanding.confessionwallback.pojo.entity.Post;
import com.sanding.confessionwallback.pojo.entity.Topic;
import com.sanding.confessionwallback.service.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private PostMapper postMapper;
    /**
     * 分页查询某组话题
     * @param topicPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageByGroup(TopicPageQueryDTO topicPageQueryDTO) {
        Page<Topic> page =new Page<Topic>(topicPageQueryDTO.getP(),topicPageQueryDTO.getS());

        LambdaQueryWrapper<Topic> wrapper =new LambdaQueryWrapper<Topic>()
                .eq(Topic::getGroupId,topicPageQueryDTO.getGroupId());

        Page<Topic> resultPage = topicMapper.selectPage(page,wrapper);

        return new PageResult(resultPage.getTotal(),resultPage.getRecords());
    }

    /**
     * 新建话题
     * @param topicDTO
     */
    @Override
    public void save(TopicDTO topicDTO) {
        //判断话题是否存在
        LambdaQueryWrapper<Topic> wrapper =new LambdaQueryWrapper<Topic>()
                .select(Topic::getTopicId)
                .eq(Topic::getTopicName,topicDTO.getTopicName())
                .eq(Topic::getCircleId,topicDTO.getCircleId())
                .eq(Topic::getGroupId,topicDTO.getGroupId());
        List<Long> topicId = topicMapper.selectObjs(wrapper);
        if(topicId.size()>0){
            throw new TopicException("该话题已存在");
        }else {
            //创建新话题
            Topic tp=new Topic();
            BeanUtils.copyProperties(topicDTO,tp);
            topicMapper.insert(tp);
        }
    }

    /**
     * TODO 分页多表查询待完善
     * 多条件分页查询话题
     * @param topicPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(TopicPageQueryDTO topicPageQueryDTO) {

        Page<Topic> page =new Page<>(topicPageQueryDTO.getP(), topicPageQueryDTO.getS());
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        // 根据传入的条件构建查询条件
        //groupId
        if (topicPageQueryDTO.getGroupId() != null) {
            wrapper.eq(topicPageQueryDTO.GROUP_ID, topicPageQueryDTO.getGroupId());
        }
        //topicId
        if (topicPageQueryDTO.getTopicId() != null) {
            wrapper.eq(topicPageQueryDTO.TOPIC_ID, topicPageQueryDTO.getTopicId());
        }
        //圈子Id
        if (topicPageQueryDTO.getCircleId() != null) {
            wrapper.eq(topicPageQueryDTO.CIRCLE_ID,topicPageQueryDTO.getCircleId());
        }
        // 标题
        if (topicPageQueryDTO.getPostTitle() != null && !topicPageQueryDTO.getPostTitle().isEmpty()) {
            wrapper.like(topicPageQueryDTO.POST_TITLE, topicPageQueryDTO.getPostTitle());
        }
        // circleName
        if (topicPageQueryDTO.getCircleName() != null && !topicPageQueryDTO.getCircleName().isEmpty()) {
            wrapper.like(topicPageQueryDTO.CIRCLE_NAME, topicPageQueryDTO.getCircleName());
        }
        // groupName
        if (topicPageQueryDTO.getGroupName() != null && !topicPageQueryDTO.getGroupName().isEmpty()) {
            wrapper.like(topicPageQueryDTO.GROUP_NAME, topicPageQueryDTO.getGroupName());
        }
        // topicName
        if (topicPageQueryDTO.getTopicName() != null && !topicPageQueryDTO.getTopicName().isEmpty()) {
            wrapper.like(topicPageQueryDTO.TOPIC_NAME, topicPageQueryDTO.getTopicName());
        }
        // 执行分页查询

        Page<Topic> resultPage = topicMapper.selectPage(page, wrapper);

        return  new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }

    /**
     * 修改话题
     * @param topicDTO
     */
    @Override
    public void updateTopic(TopicDTO topicDTO) {
        //修改话题
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicDTO,topic);
        LambdaQueryWrapper<Topic> wrapper=new LambdaQueryWrapper<Topic>()
                .eq(Topic::getTopicId,topicDTO.getTopicId());
        topicMapper.update(topic,wrapper);
    }

    /**
     * 删除话题
     * @param topicId
     */
    @Override

    public void deleteByTopicId(Long topicId) {
        //判断话题下是否有帖子
        LambdaQueryWrapper<Post> wrapper =new LambdaQueryWrapper<Post>()
                .eq(Post::getTopicId,topicId);
        List<Post> list = postMapper.selectList(wrapper);
        if(list.isEmpty()){
            //删除话题
            topicMapper.deleteById(topicId);
        }else{
            throw new TopicException("存在帖子不允许删除");
        }


    }
}

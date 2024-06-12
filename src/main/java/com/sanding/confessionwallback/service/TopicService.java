package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.TopicDTO;
import com.sanding.confessionwallback.pojo.dto.TopicPageQueryDTO;

public interface TopicService {
    /**
     * 分页查某组话题
     * @param topicPageQueryDTO
     * @return
     */
    PageResult pageByGroup(TopicPageQueryDTO topicPageQueryDTO);

    /**
     *  新增话题
     * @param topicDTO
     */
    void save(TopicDTO topicDTO);

    /**
     * 多条件分页查询
     * @param
     * @return
     */
    PageResult page(TopicPageQueryDTO topicPageQueryDTO);

    /**
     * 修改话题
     * @param topicDTO
     */
    void updateTopic(TopicDTO topicDTO);

    /**
     * 删除话题
     * @param topicId
     */
    void deleteByTopicId(Long topicId);
}

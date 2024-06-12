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
}

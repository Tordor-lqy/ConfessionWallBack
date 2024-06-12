package com.sanding.confessionwallback.controller.admin;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.TopicDTO;
import com.sanding.confessionwallback.pojo.dto.TopicPageQueryDTO;
import com.sanding.confessionwallback.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin/topic")
public class AdminTopicController {
    @Autowired
    private TopicService topicService;

    /**
     * 多条件分页查询
     * @param topicPageQueryDTO
     * @return
     */
    @GetMapping
    public Result<PageResult> page(TopicPageQueryDTO topicPageQueryDTO){
        log.info("多条件分页查询话题列表:{}",topicPageQueryDTO);
        PageResult pageResult =topicService.page(topicPageQueryDTO);
        return Result.success(pageResult);
    }
}

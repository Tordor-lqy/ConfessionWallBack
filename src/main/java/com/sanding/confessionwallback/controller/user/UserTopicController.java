package com.sanding.confessionwallback.controller.user;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.TopicPageQueryDTO;
import com.sanding.confessionwallback.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/topic")
@Slf4j

public class UserTopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public Result<PageResult> pageByGroup(TopicPageQueryDTO  topicPageQueryDTO){
        log.info("分页查询某分组话题：{}",topicPageQueryDTO);
        PageResult pageResult=topicService.pageByGroup(topicPageQueryDTO);
        return  Result.success(pageResult);
    }

}

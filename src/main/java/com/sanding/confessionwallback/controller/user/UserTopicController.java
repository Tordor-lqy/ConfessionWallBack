package com.sanding.confessionwallback.controller.user;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.TopicDTO;
import com.sanding.confessionwallback.pojo.dto.TopicPageQueryDTO;
import com.sanding.confessionwallback.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/topic")
@Slf4j

public class UserTopicController {

    @Autowired
    private TopicService topicService;

    /**
     * 分页查询某分组话题
     * @param topicPageQueryDTO
     * @return
     */
    @GetMapping
    public Result<PageResult> pageByGroup(TopicPageQueryDTO  topicPageQueryDTO){
        log.info("分页查询某分组话题：{}",topicPageQueryDTO);
        PageResult pageResult=topicService.pageByGroup(topicPageQueryDTO);
        return  Result.success(pageResult);
    }

    /**
     * 新建话题
     * @param topicDTO
     * @return
     */
    @PostMapping
    public Result<TopicDTO> save(@RequestBody TopicDTO topicDTO){
        log.info("新建话题:{}",topicDTO);
        topicService.save(topicDTO);
        return Result.success();
    }
}

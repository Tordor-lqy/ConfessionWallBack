package com.sanding.confessionwallback.controller.admin;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.common.result.Result;
import com.sanding.confessionwallback.pojo.dto.TopicDTO;
import com.sanding.confessionwallback.pojo.dto.TopicPageQueryDTO;
import com.sanding.confessionwallback.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增话题
     * @param topicDTO
     * @return
     */
    @PostMapping
    public Result saveTopic(@RequestBody TopicDTO topicDTO){
        log.info("新增话题：{}",topicDTO);
        topicService.save(topicDTO);
        return Result.success();
    }

    /**
     * 修改话题
     * @param topicDTO
     * @return
     */
    @PutMapping
    public Result updateTopic(@RequestBody TopicDTO topicDTO){
        log.info("修改话题：{}",topicDTO);
        topicService.updateTopic(topicDTO);
        return Result.success();
    }

    /**
     * 删除话题
     * @param topicId
     * @return
     */
    @DeleteMapping("{topicId}")
    public Result deleteTopic(@PathVariable Long topicId){
        log.info("删除话题：{}",topicId);
        topicService.deleteByTopicId(topicId);
        return Result.success();
    }
}

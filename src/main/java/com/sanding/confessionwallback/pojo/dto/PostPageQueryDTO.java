package com.sanding.confessionwallback.pojo.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostPageQueryDTO {

    //页数
    private Integer p;
    //每页条数
    private Integer s;
    //圈子ID
    private Long circleId;
    //用户ID
    private Long userId;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    //话题
    private List<String> topicList;
}

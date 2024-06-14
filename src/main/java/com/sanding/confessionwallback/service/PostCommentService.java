package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;

import java.util.List;

public interface PostCommentService {

    //void delCommentTopic(Long postCommentId);
    //PageResult selectPostComments(PostCommentPageQueryDTO postCommentDTO);
    //PageResult selectReplyComments(PostCommentPageQueryDTO postCommentPageQueryDTO);

    void savePostComment(PostCommentDTO postCommentDTO);

    PageResult getPostComment(PostCommentPageQueryDTO postCommentPageQueryDTO);

    void batchDeleteByPostCommentId(List<Long> ids);
}

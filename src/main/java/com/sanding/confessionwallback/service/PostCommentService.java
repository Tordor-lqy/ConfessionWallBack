package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;

import java.util.List;

public interface PostCommentService {

    void savePostComment(PostCommentDTO postCommentDTO);

    PageResult getPostComment(PostCommentPageQueryDTO postCommentPageQueryDTO);

    void batchDeleteByPostCommentIdFromUser(List<Long> ids);

    PageResult getPostCommentByUserId(Long userId, Integer pageNum, Integer pageSize);

    void batchDeleteByPostCommentIdFromAdmin(List<Long> ids);
}

package com.sanding.confessionwallback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.context.BaseContext;
import com.sanding.confessionwallback.common.exception.BaseException;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.*;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.*;
import com.sanding.confessionwallback.service.PostCommentService;
import com.sanding.confessionwallback.service.ReplyPostCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostCommentServiceImpl implements PostCommentService {
	@Autowired
	private PostCommentMapper postCommentMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private ReplyPostCommentService replyPostCommentService;
	@Autowired
	private CircleMapper circleMapper;
	@Autowired
	private GroupMapper groupMapper;
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private UserMapper userMapper;

	/**
	 * 用户评论帖子
	 */
	@Override
	public void savePostComment(PostCommentDTO postCommentDTO) {
		Long userId = BaseContext.getCurrentId(); //发布评论的用户id
		Long postId = postCommentDTO.getPostId(); //发布评论的帖子id
		//根据帖子id查询发布人
		Long postUserId = postMapper.selectOne(new LambdaQueryWrapper<Post>().eq(Post::getPostId, postId)).getUserId();
		/*
		  新增评论post表操作: 评论成功, post_comment_count +1
		  评论表插入即可
		 */
		//构建评论对象
		PostComment postComment = PostComment.builder()
				.postId(postId)
				.userId(userId)
				.commentContent(postCommentDTO.getCommentContent())
				.commentLikeCount(PostComment.MO_LIKE)
				.postCommentCreateTime(LocalDateTime.now())
				.postUserId(postUserId).build();
		postCommentMapper.insert(postComment);
		//帖子评论数加一
		postMapper.insertCommentCount(postCommentDTO.getPostId());
	}

	/**
	 * 查看当前用户发布的评论
	 */
	@Override
	public PageResult getPostComment(PostCommentPageQueryDTO postCommentPageQueryDTO) {
		Page<PostComment> page = new Page<>(postCommentPageQueryDTO.getP(), postCommentPageQueryDTO.getS());

		//圈子id
		Set<Long> circleIds = new HashSet<>();
		String circleName = postCommentPageQueryDTO.getCircleName();
		Long circleId = postCommentPageQueryDTO.getCircleId();
		if (circleId != null) {
			if (circleName != null && !circleName.isEmpty()) {
				//根据名字查id
				Set<Long> set = circleMapper.selectList(new LambdaQueryWrapper<Circle>().like(Circle::getCircleName, circleName)).stream().map(Circle::getCircleId).collect(Collectors.toSet());
				// 名字没有传来的id
				if (!set.contains(circleId)) {
					return emptyPage();
				}
			}
			circleIds.add(circleId);
		} else {
			circleIds.addAll(circleMapper.selectList(new LambdaQueryWrapper<Circle>().like(Circle::getCircleName, circleName)).stream().map(Circle::getCircleId).collect(Collectors.toSet()));
		}

		//群组id
		Set<Long> groupIds = new HashSet<>();
		String groupName = postCommentPageQueryDTO.getGroupName();
		Long groupId = postCommentPageQueryDTO.getGroupId();
		if (groupId != null) {
			if (groupName != null && !groupName.isEmpty()) {
				//根据名字查id
				Set<Long> set = groupMapper.selectList(new LambdaQueryWrapper<Group>().like(Group::getGroupName, groupName)).stream().map(Group::getGroupId).collect(Collectors.toSet());
				// 名字没有传来的id
				if (!set.contains(groupId)) {
					return emptyPage();
				}
			}
			groupIds.add(groupId);
		} else {
			groupIds.addAll(groupMapper.selectList(new LambdaQueryWrapper<Group>().like(Group::getGroupName, groupName)).stream().map(Group::getGroupId).collect(Collectors.toSet()));
		}

		//话题id
		Set<Long> topicIds = new HashSet<>();
		String topicName = postCommentPageQueryDTO.getTopicName();
		Long topicId = postCommentPageQueryDTO.getTopicId();
		if (topicId != null) {
			if (topicName != null && !topicName.isEmpty()) {
				//根据名字查id
				Set<Long> set = topicMapper.selectList(new LambdaQueryWrapper<Topic>().like(Topic::getTopicName, topicName)).stream().map(Topic::getTopicId).collect(Collectors.toSet());
				// 名字没有传来的id
				if (!set.contains(topicId)) {
					return emptyPage();
				}
			}
			topicIds.add(topicId);
		} else {
			topicIds.addAll(topicMapper.selectList(new LambdaQueryWrapper<Topic>().like(Topic::getTopicName, topicName)).stream().map(Topic::getTopicId).collect(Collectors.toSet()));
		}

		//帖子  根据圈子id,群组id,话题id,帖子标题 查询帖子
		Set<Long> postIds = new HashSet<>();
		String postTitle = postCommentPageQueryDTO.getPostTitle();
		Long postId = postCommentPageQueryDTO.getPostId();
		LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
		if (!circleIds.isEmpty()) {
			queryWrapper.in(Post::getCircleId, circleIds);
		}
		if (!groupIds.isEmpty()) {
			queryWrapper.in(Post::getGroupId, groupIds);
		}
		if (!topicIds.isEmpty()) {
			queryWrapper.in(Post::getTopicId, topicIds);
		}
		if (postTitle != null && !postTitle.isEmpty()) {
			queryWrapper.like(Post::getPostTitle, postTitle);
		}
		postIds = postMapper.selectList(queryWrapper).stream().map(Post::getPostId).collect(Collectors.toSet());
		//没有满足条件的帖子
		if (postIds.isEmpty()) {
			return emptyPage();
		}
		if (postId != null) {
			if (!postIds.contains(postId)) {
				return emptyPage();
			} else {
				postIds.clear();
				postIds.add(postId);
			}
		}

		//用户id
		Set<Long> userIds = new HashSet<>();
		String userName = postCommentPageQueryDTO.getUserName();
		Long userId = postCommentPageQueryDTO.getUserId();
		if (userId != null) {
			if (userName != null && !userName.isEmpty()) {
				//根据名字查id
				Set<Long> set = userMapper.selectList(new LambdaQueryWrapper<User>().like(User::getUserName, userName)).stream().map(User::getUserId).collect(Collectors.toSet());
				// 名字没有传来的id
				if (!set.contains(userId)) {
					return emptyPage();
				}
			}
			userIds.add(userId);
		} else {
			userIds.addAll(userMapper.selectList(new LambdaQueryWrapper<User>().like(User::getUserName, userName)).stream().map(User::getUserId).collect(Collectors.toSet()));
		}


		// 根据帖子id, userId, commentId, comment内容  查询评论
		LambdaQueryWrapper<PostComment> queryWrapper1 = new LambdaQueryWrapper<>();
		if (!postIds.isEmpty()) {
			queryWrapper1.in(PostComment::getPostId, postIds);
		}
		if (!userIds.isEmpty()) {
			queryWrapper1.in(PostComment::getUserId, userIds);
		}
		if (postCommentPageQueryDTO.getPostCommentId() != null) {
			queryWrapper1.in(PostComment::getPostCommentId, postCommentPageQueryDTO.getPostCommentId());
		}
		String postComment = postCommentPageQueryDTO.getPostComment();
		if (postComment != null && !postComment.isEmpty()) {
			queryWrapper1.like(PostComment::getCommentContent, postComment);
		}

		queryWrapper1.orderByDesc(PostComment::getPostCommentCreateTime);

		postCommentMapper.selectPage(page, queryWrapper1);

		return new PageResult(page.getTotal(), page.getRecords());
	}

	/**
	 * 根据用户评论id批量删除评论
	 */
	@Transactional
	@Override
	public void batchDeleteByPostCommentId(List<Long> ids) {
		Long userId = BaseContext.getCurrentId();
		postCommentMapper.selectList(new LambdaQueryWrapper<PostComment>()
						.in(PostComment::getPostCommentId, ids)
		).forEach(
				postComment -> {
					if (!Objects.equals(postComment.getUserId(), userId)) {
						throw new BaseException("评论发布者与用户不匹配");
					}
				}
		);
		//1. 删除reply_comment表数据
		replyPostCommentService.batchDeleteByCommentId(ids);
		//2. 删除post_comment表数据
		postCommentMapper.deleteBatchIds(ids);
	}

	/**
	 * 根据用户id获取评论
	 */
	@Override
	public PageResult getPostCommentByUserId(Long userId, Integer pageNum, Integer pageSize) {
		Page<PostComment> page = new Page<>(pageNum, pageSize);

		LambdaQueryWrapper<PostComment> queryWrapper = new LambdaQueryWrapper<PostComment>()
				.eq(PostComment::getUserId, userId)
				.orderByDesc(PostComment::getPostCommentCreateTime);

		postCommentMapper.selectPage(page, queryWrapper);

		return new PageResult(page.getTotal(), page.getRecords());
	}


	private PageResult emptyPage() {
		return new PageResult(0, new ArrayList<>());
	}

}

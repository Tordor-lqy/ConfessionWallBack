package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.GroupDTO;
import com.sanding.confessionwallback.pojo.dto.GroupPageQueryDTO;

import java.util.List;


public interface GroupService {


	PageResult list(GroupPageQueryDTO groupPageQueryDTO);

	void saveGroup(GroupDTO groupDTO);

	void updateGroup(GroupDTO groupDTO);

	void deleteById(Long groupId);

	void BatchDeleteById(List<Long> ids);

}

package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.GroupPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.GroupSaveDTO;
import com.sanding.confessionwallback.pojo.entity.Group;

public interface GroupService {


	PageResult list(GroupPageQueryDTO groupPageQueryDTO);

	void saveGroup(GroupSaveDTO groupSaveDTO);

	void updateGroup(GroupSaveDTO groupSaveDTO);
}

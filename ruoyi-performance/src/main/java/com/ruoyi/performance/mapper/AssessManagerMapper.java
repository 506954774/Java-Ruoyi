/*
 *    Copyright (c) 2018-2025, iot All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: iot
 */

package com.ruoyi.performance.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.performance.domain.AssessManager;
import com.ruoyi.performance.domain.DcArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 项目分区表
 *
 * @author pigx code generator
 * @date 2020-11-26 10:05:07
 */
@Mapper
public interface AssessManagerMapper extends BaseMapper<AssessManager> {

	@Select("SELECT id FROM dc_area WHERE parent_id = #{parentId}")
	List<Integer> getIdsByParentId(@Param("parentId") Integer parentId);

	@Select("select * from dc_area where  (parent_id is NULL or parent_id = 0) and dept_id = #{projectId}")
	List<DcArea> getRootByProjectId(Integer projectId);
	@Select("select count(1) from ynlk_ry.sys_user where dept_id=#{deptid} and del_flag=0")
	public Integer selecttotalnum(Integer deptid);

	@Select("select * from ynlk_ry.sys_user where dept_id=#{deptid} and del_flag=0")
	public List<Map> selectlistforusers(Integer deptid);

	@Select("select  GROUP_CONCAT(post_name) as postname,GROUP_CONCAT(post_code) as postcode from ynlk_ry.sys_post where post_id in(\n" +
			" select post_id from  ynlk_ry.sys_user_post where user_id=#{userid} \n" +
			" ) and status=0")
	public Map<String,String> selectpostforuser(Integer userid);
}

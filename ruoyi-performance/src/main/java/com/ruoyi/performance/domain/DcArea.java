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

package com.ruoyi.performance.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 项目分区表
 *
 * @author pigx code generator
 * @date 2020-11-26 10:05:07
 */
@Data
@TableName("dc_area")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目分区表")
public class DcArea extends Model<DcArea> {
private static final long serialVersionUID = 1L;

	/**
	 * 所属租户
	 */
	@ApiModelProperty(value = "所属租户", hidden = true)
	private Integer tenantId;
    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;
    /**
     * 所在项目id
     */
    @ApiModelProperty(value="所在项目id")
    private Integer deptId;
    /**
     * 上级节点
     */
    @ApiModelProperty(value="上级节点")
    private Integer parentId;
    /**
     * 编码
     */
    @ApiModelProperty(value="编码")
    private String code;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createBy;
    /**
     * 数据创建时间
     */
    @ApiModelProperty(value="数据创建时间")
    private LocalDateTime createTime;



    /**
     * 逻辑删除
     */
    @TableLogic(value="0",delval="1")
    @ApiModelProperty(value="逻辑删除")
    private Integer isdel;

    }

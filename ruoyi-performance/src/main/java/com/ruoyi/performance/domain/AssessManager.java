package com.ruoyi.performance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @author exon
 * @version 1.0
 * @date 2022/12/8 9:13
 */
@Data
@TableName("assess_manager")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "考核管理表")
public class AssessManager extends Model<AssessManager> {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;
    /**
     * 考核名称
     */
    @ApiModelProperty(value="考核名称")
    private String checkname;
    /**
     * 部门
     */
    @ApiModelProperty(value="部门")
    private String department;

    /**
     * 部门id
     */
    @ApiModelProperty(value="部门id")
    private String deptid;

    /**
     * 考核开始日期
     */
    @ApiModelProperty(value="考核开始日期")
    private String checkstartdate;
    /**
     * 考核结束日期
     */
    @ApiModelProperty(value="考核结束日期")
    private String checkenddate;

    /**
     * 考核模板名称
     */
    @ApiModelProperty(value="考核模板名称")
    private String tempname;

    /**
     * 模板id
     */
    @ApiModelProperty(value="考核模板id")
    private String tempid;

    /**
     * 考核进度完成人数
     */
    @ApiModelProperty(value="考核进度完成人数")
    private Integer checknum;

    /**
     * 考核进度总人数
     */
    @ApiModelProperty(value="考核进度总人数")
    private Integer totalnum;

    /**
     * 发布状态
     */
    @ApiModelProperty(value="发布状态")
    private Integer status;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    private Integer isdel;


    /**
     * 创建日期
     */
    @ApiModelProperty(value="创建日期")
    private String createtime;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createname;

    /**
     * 修改日期
     */
    @ApiModelProperty(value="修改日期")
    private String updatetime;

    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updatename;
}

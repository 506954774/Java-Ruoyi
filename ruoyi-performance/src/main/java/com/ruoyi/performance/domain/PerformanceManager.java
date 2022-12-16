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

import java.math.BigDecimal;

/**
 * @author exon
 * @version 1.0
 * @date 2022/12/8 9:13
 */
@Data
@TableName("perfermance_manager")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "绩效管理表")
public class PerformanceManager extends Model<PerformanceManager> {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;


    /**
     * 考核管理id
     */
    @ApiModelProperty(value="考核管理id")
    private Integer pid;



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
     * 考核人姓名
     */
    @ApiModelProperty(value="考核人姓名")
    private String username;

    /**
     * 考核人id
     */
    @ApiModelProperty(value="考核人id")
    private Long userid;

    /**
     * 部门层级,逗号分割
     */
    @ApiModelProperty(value="部门层级,逗号分割")
    private String deptids;

    /**
     * 部门id
     */
    @ApiModelProperty(value="部门id")
    private Integer deptid;

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
     * 自评分
     */
    @ApiModelProperty(value="自评分")
    private BigDecimal selfscore;

    /**
     * 上级评分
     */
    @ApiModelProperty(value="上级评分")
    private BigDecimal leaderscore;

    /**
     * 实际总分
     */
    @ApiModelProperty(value="实际总分")
    private BigDecimal actualscore;

    /**
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private String post;

    /**
     * 岗位编号
     */
    @ApiModelProperty(value="岗位编号")
    private String postcode;


    /**
     * 最终评级
     */
    @ApiModelProperty(value="最终评级")
    private String finallevel;

    /**
     * 绩效考核状态
     */
    @ApiModelProperty(value="绩效考核状态")
    private Integer status;

    /**
     * 模板id
     */
    @ApiModelProperty(value="模板id")
    private Integer tempid;

    /**
     * 自评提交日期
     */
    @ApiModelProperty(value="自评提交日期")
    private String selfdate;

    /**
     * 创建日期
     */
    @ApiModelProperty(value="领导评级提交日期")
    private String leaderdate;


    /**
     * 上级领导名称
     */
    @ApiModelProperty(value="上级领导名称")
    private String leadername;

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

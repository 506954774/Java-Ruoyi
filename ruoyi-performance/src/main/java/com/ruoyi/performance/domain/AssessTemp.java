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

/**
 * @author exon
 * @version 1.0
 * @date 2022/12/8 9:13
 */
@Data
@TableName("assess_temp")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "绩效考核模板表")
public class AssessTemp extends Model<AssessTemp> {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 模板名称
     */
    @ApiModelProperty(value="模板名称")
    private String tempname;
    /**
     * 模板内容
     */
    @ApiModelProperty(value="模板内容")
    private String content;

    /**
     * 考核项组ids
     */
    @ApiModelProperty(value="考核项组ids")
    private String itemgroup;

    /**
     * 总分值
     */
    @ApiModelProperty(value="总分值")
    private Integer totalscore;

    /**
     * 适用等级名称
     */
    @ApiModelProperty(value="适用等级名称")
    private String levelname;

    /**
     * 适用等级的key
     */
    @ApiModelProperty(value="适用等级的key")
    private String levelcode;

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

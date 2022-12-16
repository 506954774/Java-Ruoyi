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
@TableName("assess_items")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "考核项管理表")
public class AssessItems extends Model<AssessItems> {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;
    /**
     * 考核指标
     */
    @ApiModelProperty(value="考核指标")
    private String kpiname;
    /**
     * 考核项名称
     */
    @ApiModelProperty(value="考核项名称")
    private String itemname;
    /**
     * 考核内容
     */
    @ApiModelProperty(value="考核内容")
    private String content;
    /**
     * 评分标准
     */
    @ApiModelProperty(value="评分标准")
    private String scorecriteria;

    /**
     * 总分值
     */
    @ApiModelProperty(value="总分值")
    private Integer totalscore;

    /**
     * 权重
     */
    @ApiModelProperty(value="权重")
    private Integer percent;



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

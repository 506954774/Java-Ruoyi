package com.ruoyi.performance.domain.qo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TestBean
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2022/12/7 10:27
 * Copyright :  版权所有
 **/
@Data
public class TestQo {

    @NotNull(message = "所属租户不可为空")
    @NotEmpty (message = "所属租户不可为空字符串")
    @ApiModelProperty(value = "所属租户", hidden = true)
    private String tenantId;

    @NotNull(message = "id不可为空")
    @ApiModelProperty(value="")
    private Integer id;
}

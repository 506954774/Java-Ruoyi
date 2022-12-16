package com.ruoyi.performance.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.performance.domain.AssessItems;
import com.ruoyi.performance.domain.AssessTemp;
import com.ruoyi.performance.service.IAssessItemsService;
import com.ruoyi.performance.service.IAssessTempService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author exon
 * @version 1.0
 * @date 2022/12/8 8:59
 * 考核模板库
 */

@Slf4j
@RestController
@RequestMapping("/temp")
@DS("slave_2")
public class AssessTempController extends BaseController {

    @Autowired
   public IAssessTempService assessTempService;

    /**
     * 考核模板库清单
     */

    @PostMapping("/list")
    public AjaxResult list(@RequestBody JSONObject jsonObject, Page page) {


        final val lambdaQuery = Wrappers.<AssessTemp>lambdaQuery();
        if(StringUtils.isNotBlank(jsonObject.getString("tempname"))){
            lambdaQuery.like(AssessTemp::getTempname, jsonObject.getString("tempname"));
        }

        final val list = assessTempService.page(page,lambdaQuery.orderByDesc(AssessTemp::getId));
        return AjaxResult.success(list);
    }




    /**
     * 考核模板库下拉
     */

    @GetMapping("/select")
    public AjaxResult select() {
        final val list = assessTempService.list();
        return AjaxResult.success(list);
    }



    /**
     * 考核模板库新增
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody AssessTemp assessTemp) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

        assessTemp.setCreatename(user.getNickName());
        assessTemp.setCreatetime(DateUtils.dateTimeNow());

        assessTempService.save(assessTemp);
        return AjaxResult.success();
    }

    /**
     * 考核模板库修改
     */

    @PostMapping("/modify")
    public AjaxResult modify(@RequestBody AssessTemp assessTemp) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

        assessTemp.setUpdatename(user.getNickName());
        assessTemp.setUpdatetime(DateUtils.dateTimeNow());

        assessTempService.saveOrUpdate(assessTemp);
        return AjaxResult.success();
    }



    /**
     * 考核模板库删除
     */

    @PostMapping("/del")
    public AjaxResult del(@RequestBody JSONObject jsonObject) {
        String ids = jsonObject.getString("ids");
        List<Integer> int_ids = new ArrayList<>();
        final val split = ids.split(",");
        for (String s : split) {
            int_ids.add(Integer.valueOf(s));
        }
        assessTempService.removeByIds(int_ids);
        return AjaxResult.success();
    }



}

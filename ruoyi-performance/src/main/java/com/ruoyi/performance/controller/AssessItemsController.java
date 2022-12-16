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
import com.ruoyi.performance.domain.AssessManager;
import com.ruoyi.performance.domain.AssessTemp;
import com.ruoyi.performance.service.IAssessItemsService;
import com.ruoyi.performance.service.IAssessManagerService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author exon
 * @version 1.0
 * @date 2022/12/8 8:59
 * 绩效考核项
 */

@Slf4j
@RestController
@RequestMapping("/items")
@DS("slave_2")
public class AssessItemsController extends BaseController {

    @Autowired
   public IAssessItemsService assessItemsService;

    /**
     * 考核项清单
     */

    @PostMapping("/list")
    public AjaxResult list(@RequestBody JSONObject jsonObject, Page page) {


        final val lambdaQuery = Wrappers.<AssessItems>lambdaQuery();
        if(StringUtils.isNotBlank(jsonObject.getString("itemname"))){
            lambdaQuery.like(AssessItems::getItemname, jsonObject.getString("itemname"));
        }

        final val list = assessItemsService.page(page,lambdaQuery.orderByDesc(AssessItems::getId));
        return AjaxResult.success(list);
    }


    /**
     * 考核模板内容点击下钻
     */

    @PostMapping("/contentlist")
    public AjaxResult contentlist(@RequestBody JSONObject jsonObject, Page page) {
        String ids = jsonObject.getString("ids");
         List<Integer> int_ids = new ArrayList<>();
        final val split = ids.split(",");
        for (String s : split) {
            int_ids.add(Integer.valueOf(s));
        }
        final val list = assessItemsService.page(page,Wrappers.<AssessItems>lambdaQuery().in(AssessItems::getId,int_ids));
        return AjaxResult.success(list);
    }



    /**
     * 考核模板下拉列表清单
     */

    @GetMapping("/select")
    public AjaxResult items() {
        final val list = assessItemsService.list();
        return AjaxResult.success(list);
    }

    /**
     * 考核项新增
     */

    @PostMapping("/add")
    public AjaxResult add(@RequestBody AssessItems assessItems) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

        assessItems.setCreatename(user.getNickName());
        assessItems.setCreatetime(DateUtils.dateTimeNow());

        assessItemsService.save(assessItems);
        return AjaxResult.success();
    }

    /**
     * 考核项修改
     */

    @PostMapping("/modify")
    public AjaxResult modify(@RequestBody AssessItems assessItems) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

        assessItems.setUpdatename(user.getNickName());
        assessItems.setUpdatetime(DateUtils.dateTimeNow());

        assessItemsService.saveOrUpdate(assessItems);
        return AjaxResult.success();
    }



    /**
     * 考核项删除
     */

    @PostMapping("/del")
    public AjaxResult del(@RequestBody JSONObject jsonObject) {
        String ids = jsonObject.getString("ids");
        List<Integer> int_ids = new ArrayList<>();
        final val split = ids.split(",");
        for (String s : split) {
            int_ids.add(Integer.valueOf(s));
        }
        assessItemsService.removeByIds(int_ids);
        return AjaxResult.success();
    }



}

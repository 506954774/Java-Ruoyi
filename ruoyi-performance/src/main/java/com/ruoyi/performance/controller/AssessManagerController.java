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
import com.ruoyi.performance.domain.AssessManager;
import com.ruoyi.performance.domain.PerformanceManager;
import com.ruoyi.performance.service.IAssessManagerService;
import com.ruoyi.performance.service.IPerformanceManagerService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author exon
 * @version 1.0
 * @date 2022/12/8 8:59
 * 绩效考核管理
 */

@Slf4j
@RestController
@RequestMapping("/check")
@DS("slave_2")
public class AssessManagerController extends BaseController {

    @Autowired
   public IAssessManagerService assessManagerService;

    @Autowired
    public IPerformanceManagerService performanceManagerService;
    /**
     * 考核管理清单
     */

    @PostMapping("/list")
    public AjaxResult list(@RequestBody JSONObject jsonObject, Page<AssessManager> page) {

        final val lambdaQuery = Wrappers.<AssessManager>lambdaQuery();
        if(StringUtils.isNotBlank(jsonObject.getString("checkname"))){
            lambdaQuery.like(AssessManager::getCheckname, jsonObject.getString("checkname"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("department"))){
            lambdaQuery.like(AssessManager::getDepartment, jsonObject.getString("department"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("checkdate"))){
            lambdaQuery.le(AssessManager::getCheckstartdate, jsonObject.getString("checkdate"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("checkdate"))){
            lambdaQuery.ne(AssessManager::getCheckenddate, jsonObject.getString("checkdate"));
        }

        final val list = assessManagerService.page(page,lambdaQuery.orderByDesc(AssessManager::getId));

        final val collect = list.getRecords().stream().map(a -> {
            Integer id = a.getId();
            final val counts = performanceManagerService.list(Wrappers.<PerformanceManager>lambdaQuery().eq(PerformanceManager::getPid, id)
                    .eq(PerformanceManager::getStatus, 2)
                    .eq(PerformanceManager::getIsdel, 0)).stream().count();
            int num = (int) counts;
            a.setChecknum(num);
            return a;
        }).collect(Collectors.toList());

        final val records = page.setRecords(collect);

        return AjaxResult.success(records);

    }


    
    /**
     * 绩效考核新增
     */

    @PostMapping("/add")
    public AjaxResult add(@RequestBody AssessManager assessManager) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

            assessManager.setCreatename(user.getNickName());
            assessManager.setCreatetime(DateUtils.dateTimeNow());
            assessManager.setStatus(0);
            assessManager.setChecknum(0);
            assessManager.setTotalnum(0);

            String deptids = assessManager.getDeptid();

        final val split = deptids.split(",");

        Integer deptid = Integer.valueOf(split[split.length-1]);
       Integer totalnum =  assessManagerService.selecttotalnum(deptid);
        assessManager.setTotalnum(totalnum);
        assessManagerService.save(assessManager);
        return AjaxResult.success();
    }

    /**
     * 绩效考核修改
     */

    @PostMapping("/modify")
    public AjaxResult modify(@RequestBody AssessManager assessManager) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

            assessManager.setUpdatename(user.getNickName());
            assessManager.setUpdatetime(DateUtils.dateTimeNow());

        String deptids = assessManager.getDeptid();

        final val split = deptids.split(",");

        Integer deptid = Integer.valueOf(split[split.length-1]);
        Integer totalnum =  assessManagerService.selecttotalnum(deptid);
        assessManager.setTotalnum(totalnum);

        assessManagerService.saveOrUpdate(assessManager);
        return AjaxResult.success();
    }



    /**
     * 绩效考核删除
     */

    @PostMapping("/del")
    public AjaxResult del(@RequestBody JSONObject jsonObject) {
        String ids = jsonObject.getString("ids");
        List<Integer> int_ids = new ArrayList<>();
        final val split = ids.split(",");
        for (String s : split) {
            int_ids.add(Integer.valueOf(s));
        }
        assessManagerService.removeByIds(int_ids);
        return AjaxResult.success();
    }



}

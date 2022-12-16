package com.ruoyi.performance.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.performance.domain.AssessManager;
import com.ruoyi.performance.domain.Mark;
import com.ruoyi.performance.domain.PerformanceManager;
import com.ruoyi.performance.service.IAssessManagerService;
import com.ruoyi.performance.service.IMarkService;
import com.ruoyi.performance.service.IPerformanceManagerService;
import com.ruoyi.performance.util.Const;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author exon
 * @version 1.0
 * @date 2022/12/8 8:59
 * 绩效管理
 */

@Slf4j
@RestController
@RequestMapping("/mop")
@DS("slave_2")
public class PerformanceManagerController extends BaseController {

    @Autowired
   public IAssessManagerService assessManagerService;
    @Autowired
   public IPerformanceManagerService performanceManagerService;

    @Autowired
    public IMarkService markService;

    @Autowired
    private ISysDictTypeService dictTypeService;


    /**
     * 绩效自评清单
     */

    @PostMapping("/list")
    public AjaxResult list(@RequestBody JSONObject jsonObject, Page page) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

        final val lambdaQuery = Wrappers.<PerformanceManager>lambdaQuery();
        final val roleIds = user.getRoleIds();
        System.out.println(roleIds);
        final val roles = user.getRoles();

        List<String> rolenames = new ArrayList<>();

        for (SysRole role : roles) {
            rolenames.add(role.getRoleKey());
        }

        if(!rolenames.contains(Const.ROLE_ADMIN)){
            lambdaQuery.eq(PerformanceManager::getUserid,user.getUserId());
        }

        if(StringUtils.isNotBlank(jsonObject.getString("checkname"))){
            lambdaQuery.like(PerformanceManager::getCheckname, jsonObject.getString("checkname"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("status"))){
            lambdaQuery.eq(PerformanceManager::getStatus, jsonObject.getString("status"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("checkdate"))){
            lambdaQuery.le(PerformanceManager::getCheckstartdate, jsonObject.getString("checkdate"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("checkdate"))){
            lambdaQuery.ne(PerformanceManager::getCheckenddate, jsonObject.getString("checkdate"));
        }

        final val list = performanceManagerService.page(page,lambdaQuery.orderByDesc(PerformanceManager::getId));
        return AjaxResult.success(list);
    }


    /**
     * 绩效审批清单
     */

    @PostMapping("/leaderlist")
    public AjaxResult leaderlist(@RequestBody JSONObject jsonObject, Page page) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

        final val lambdaQuery = Wrappers.<PerformanceManager>lambdaQuery();

        final val roleIds = user.getRoleIds();
        System.out.println(roleIds);
        final val roles = user.getRoles();

        List<String> rolenames = new ArrayList<>();

        for (SysRole role : roles) {
            rolenames.add(role.getRoleKey());
        }

        if(StringUtils.isNotBlank(jsonObject.getString("checkname"))){
            lambdaQuery.like(PerformanceManager::getCheckname, jsonObject.getString("checkname"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("status"))){
            lambdaQuery.eq(PerformanceManager::getStatus, jsonObject.getString("status"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("username"))){
            lambdaQuery.eq(PerformanceManager::getUsername, jsonObject.getString("username"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("checkdate"))){
            lambdaQuery.le(PerformanceManager::getCheckstartdate, jsonObject.getString("checkdate"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("checkdate"))){
            lambdaQuery.ne(PerformanceManager::getCheckenddate, jsonObject.getString("checkdate"));
        }

        if(!rolenames.contains(Const.ROLE_ADMIN)){
            //普通员工级别
            if(rolenames.size()==1 && rolenames.contains(Const.ROLE_COMMON)){
                lambdaQuery.eq(PerformanceManager::getUserid,user.getUserId());
            }
            //部门经理级别
            else if(rolenames.contains(Const.ROLE_MANAGE) && !rolenames.contains(Const.ROLE_LEADER)){
                Integer deptId =  Integer.valueOf(user.getDeptId().toString()) ;
                lambdaQuery.eq(PerformanceManager::getDeptid,deptId);
                lambdaQuery.ne(PerformanceManager::getPostcode,Const.POST_SE);
            }
            //最高级别
            else if(rolenames.contains(Const.ROLE_LEADER)){
                //暂时只让看部门经理人员
                //lambdaQuery.eq(PerformanceManager::getPostcode,Const.POST_SE);
            }

        }

        final val list = performanceManagerService.page(page,lambdaQuery.orderByDesc(PerformanceManager::getId));
        return AjaxResult.success(list);
    }


    /**
     * 考核管理清单
     */

    @PostMapping("/progress")
    public AjaxResult progress(@RequestBody JSONObject jsonObject, Page page) {

           Integer id = jsonObject.getInteger("id");
        final val lambdaQuery = Wrappers.<PerformanceManager>lambdaQuery();
                lambdaQuery.eq(PerformanceManager::getPid,id)
                        .eq(PerformanceManager::getStatus,2);

        final val list = performanceManagerService.page(page,lambdaQuery.orderByDesc(PerformanceManager::getId));
        return AjaxResult.success(list);
    }



    /**
     * 评分详情
     */

    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody JSONObject jsonObject) {

        Integer id = jsonObject.getInteger("id");

        final val performanceManager = performanceManagerService.getById(id);

        final val marks = markService.list(Wrappers.<Mark>lambdaQuery().eq(Mark::getPid, id));

        JSONObject jo = new JSONObject();
        jo.put("detail",performanceManager);
        jo.put("content",marks);
        return AjaxResult.success(jo);
    }


    /**
     * 自评分
     */

    @PostMapping("/mark")
    public AjaxResult mark(@RequestBody JSONObject jsonObject) {

        JSONArray ja = jsonObject.getJSONArray("data");

        Mark mark = null;
        for (int i = 0; i < ja.size(); i++) {
            final val object = ja.getJSONObject(i);
            mark = new Mark();
            mark.setId(object.getInteger("id"));
            mark.setSelfscore(object.getBigDecimal("score"));
            mark.setLeadscore(object.getBigDecimal("score"));
            markService.updateById(mark);
        }

        Integer pid = jsonObject.getInteger("pid");
        Integer status = jsonObject.getInteger("status");
        final val marks = markService.list(Wrappers.<Mark>lambdaQuery().eq(Mark::getPid, pid).eq(Mark::getIsdel, 0));
        final val total_selfscore = marks.stream().map(Mark::getSelfscore).reduce(BigDecimal.ZERO,BigDecimal::add);
        //final val total_leaderfscore = marks.stream().map(Mark::getLeadscore).reduce(BigDecimal.ZERO,BigDecimal::add);
        //final val total_actualscore = marks.stream().map(Mark::getActualscore).reduce(BigDecimal.ZERO,BigDecimal::add);

        PerformanceManager pm = new PerformanceManager();
        pm.setId(pid);
        pm.setSelfscore(total_selfscore);
        //pm.setLeaderscore(total_leaderfscore);
        //pm.setActualscore(total_actualscore);
        pm.setSelfdate(DateUtils.dateTimeNow());
        pm.setStatus(status);
        performanceManagerService.updateById(pm);
        return AjaxResult.success();
    }


    /**
     * 上级评分
     */

    @PostMapping("/leadermark")
    @SneakyThrows
    public AjaxResult leadermark(@RequestBody JSONObject jsonObject){
        SysUser user = SecurityUtils.getLoginUser().getUser();
        JSONArray ja = jsonObject.getJSONArray("data");

        Mark mark = null;
        for (int i = 0; i < ja.size(); i++) {
            final val object = ja.getJSONObject(i);
            mark = new Mark();
            mark.setId(object.getInteger("id"));
            mark.setLeadscore(object.getBigDecimal("score"));
            mark.setActualscore(object.getBigDecimal("score"));
            mark.setReason(object.getString("reason"));
            markService.updateById(mark);
        }

        Integer pid = jsonObject.getInteger("pid");
        Integer status = jsonObject.getInteger("status");
        final val marks = markService.list(Wrappers.<Mark>lambdaQuery().eq(Mark::getPid, pid).eq(Mark::getIsdel, 0));
        final val total_selfscore = marks.stream().map(Mark::getSelfscore).reduce(BigDecimal.ZERO,BigDecimal::add);
        final val total_leaderfscore = marks.stream().map(Mark::getLeadscore).reduce(BigDecimal.ZERO,BigDecimal::add);
        final val total_actualscore = marks.stream().map(Mark::getActualscore).reduce(BigDecimal.ZERO,BigDecimal::add);

        PerformanceManager pm = new PerformanceManager();
        pm.setId(pid);
        pm.setSelfscore(total_selfscore);
        pm.setLeaderscore(total_leaderfscore);
        pm.setActualscore(total_actualscore);
        pm.setLeaderdate(DateUtils.dateTimeNow());
        pm.setLeadername(user.getNickName());
        pm.setStatus(status);

        final val dictData = dictTypeService.selectDictDataByType(Const.DIC_CODE);

        final val level = getLevel(total_actualscore,dictData);
           pm.setFinallevel(level);
        performanceManagerService.updateById(pm);
        return AjaxResult.success();
    }

    public String getLevel(BigDecimal score, List<SysDictData> list){
        for (SysDictData sysDictData : list) {
            String values = sysDictData.getDictValue();
            String num0 = values.split(",")[0];
            String num1 = values.split(",")[1];
               if(org.apache.commons.lang3.StringUtils.equals(num1,"Infinity")){
                   BigDecimal bd = new BigDecimal(num0);
                   if(score.compareTo(bd)==1 || score.compareTo(bd)==0){
                            return sysDictData.getDictLabel();
                   }
               } else if(org.apache.commons.lang3.StringUtils.equals(num0,"-Infinity")){
                   BigDecimal bd = new BigDecimal(num1);
                   if(score.compareTo(bd)==-1 || score.compareTo(bd)==0){
                       return sysDictData.getDictLabel();
                   }
               }else{
                   BigDecimal bd1 = new BigDecimal(num0);
                   BigDecimal bd2 = new BigDecimal(num1);
                   if((score.compareTo(bd1)==1 || score.compareTo(bd1)==0) && (score.compareTo(bd2)==-1 || score.compareTo(bd2)==0)){
                       return sysDictData.getDictLabel();
                   }
               }
        }
        return "";
    }


    /**
     * 绩效考核发布
     */

    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody JSONObject jsonObject) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        AssessManager assessManager = new AssessManager();
        assessManager.setUpdatename(user.getNickName());
        assessManager.setUpdatetime(DateUtils.dateTimeNow());
        assessManager.setId(jsonObject.getInteger("id"));
        assessManager.setStatus(1);
        assessManagerService.saveOrUpdate(assessManager);

        //绩效表插入对应人员的考核答卷

        List<PerformanceManager> list = new ArrayList<>();
        String deptids = jsonObject.getString("deptid");

        final val split = deptids.split(",");

        Integer deptid = Integer.valueOf(split[split.length-1]);
        List<Map>  maps =  assessManagerService.selectlistforusers(deptid);
        for (int i = 0; i < maps.size(); i++) {
                   PerformanceManager pm = new PerformanceManager();
            pm.setCheckname(jsonObject.getString("checkname"));
            pm.setDepartment(jsonObject.getString("department"));
            pm.setUsername(maps.get(i).get("nick_name").toString());
            pm.setUserid((Long)maps.get(i).get("user_id"));
            pm.setDeptids(deptids);
            pm.setDeptid(deptid);
            pm.setCheckstartdate(jsonObject.getString("checkstartdate"));
            pm.setCheckenddate(jsonObject.getString("checkenddate"));
            pm.setSelfscore(BigDecimal.valueOf(0));
            pm.setLeaderscore(BigDecimal.valueOf(0));
            pm.setActualscore(BigDecimal.valueOf(0));
            pm.setFinallevel(null);
            pm.setCreatetime(DateUtils.dateTimeNow());
            pm.setCreatename(user.getNickName());
            pm.setTempid(jsonObject.getInteger("tempid"));
            pm.setPid(jsonObject.getInteger("id"));
            pm.setStatus(0);
            final val stringMap = assessManagerService.selectpostforuser(Integer.valueOf(maps.get(i).get("user_id").toString()));
            if(stringMap==null || org.apache.commons.lang3.StringUtils.isBlank(stringMap.get("postname"))){
               pm.setPost(Const.POST_OTHER);
           }else{
               pm.setPost(stringMap.get("postname"));
           }

            if(stringMap==null || org.apache.commons.lang3.StringUtils.isBlank(stringMap.get("postcode"))){
                pm.setPostcode(Const.POST_CODE);
            }else{
                pm.setPostcode(stringMap.get("postcode"));
            }
            performanceManagerService.insert(pm);
        }

        return AjaxResult.success();
    }



    /**
     * 绩效统计人员分析
     */

    @PostMapping("/statistics")
    public AjaxResult statistics(@RequestBody JSONObject jsonObject, Page page) {

        SysUser user = SecurityUtils.getLoginUser().getUser();

        final val lambdaQuery = Wrappers.<PerformanceManager>lambdaQuery();

        final val roleIds = user.getRoleIds();
        System.out.println(roleIds);
        final val roles = user.getRoles();

        List<String> rolenames = new ArrayList<>();

        for (SysRole role : roles) {
            rolenames.add(role.getRoleKey());
        }
        if(StringUtils.isNotBlank(jsonObject.getString("checkname"))){
            lambdaQuery.like(PerformanceManager::getCheckname, jsonObject.getString("checkname"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("username"))){
            lambdaQuery.like(PerformanceManager::getUsername, jsonObject.getString("username"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("status"))){
            lambdaQuery.eq(PerformanceManager::getStatus, jsonObject.getString("status"));
        }
        if(StringUtils.isNotBlank(jsonObject.getString("level"))){
            lambdaQuery.eq(PerformanceManager::getFinallevel, jsonObject.getString("level"));
        }

        if(!rolenames.contains(Const.ROLE_ADMIN)){
            //普通员工级别
            if(rolenames.size()==1 && rolenames.contains(Const.ROLE_COMMON)){
                lambdaQuery.eq(PerformanceManager::getUserid,user.getUserId());
            }
            //部门经理级别
            else if(rolenames.contains(Const.ROLE_MANAGE) && !rolenames.contains(Const.ROLE_LEADER)){
                      Integer deptId =  Integer.valueOf(user.getDeptId().toString()) ;
                lambdaQuery.eq(PerformanceManager::getDeptid,deptId);
            }
            //最高级别
            else if(rolenames.contains(Const.ROLE_LEADER)){
                 //暂时只让看部门经理人员
                //lambdaQuery.eq(PerformanceManager::getPostcode,Const.POST_SE);
            }

        }

        final val list = performanceManagerService.page(page,lambdaQuery.orderByDesc(PerformanceManager::getId));
        return AjaxResult.success(list);
    }

}

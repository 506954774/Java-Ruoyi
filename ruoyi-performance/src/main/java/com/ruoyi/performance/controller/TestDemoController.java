package com.ruoyi.performance.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.performance.domain.DcArea;
import com.ruoyi.performance.domain.qo.TestQo;
import com.ruoyi.performance.mapper.DcAreaMapper;
import com.ruoyi.performance.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 测试
 * 
 * @author chenlei
 */
@Slf4j
@RestController
@RequestMapping("/testdemo")
@DS("slave_1")
public class TestDemoController extends BaseController {
    @Autowired
    private ITestService sysConfigService;

    @Autowired
    private DcAreaMapper dcAreaMapper;

    /**
     * 测试get请求
     */

    @GetMapping("/test")
    public AjaxResult test( ) {

        log.info("test,测试get请求");
        List<String> strings = sysConfigService.selectAllProjectCodes();
        return AjaxResult.success(strings);
    }

    /**
     * 测试testMybatisPlus
     */

    @GetMapping("/testMybatisPlus")
    public AjaxResult testMybatisPlus( ) {

        log.info("testMybatisPlus,测试testMybatisPlus，注意，TestDemoController 里加了@DS(\"slave_1\")注解");

        SysUser user = SecurityUtils.getLoginUser().getUser();
        log.info("testMybatisPlus,当前登录用户id："+user.getUserId());

        DcArea dcArea = dcAreaMapper.selectById(3);
        log.info("testMybatisPlus,dcArea:"+dcArea);
        return AjaxResult.success(dcArea);
    }


    /**
     * 测试testMybatisPlus逻辑删除
     */

    @GetMapping("/testLogicDelete")
    public AjaxResult testLogicDelete( ) {

        log.info("testLogicDelete,测试逻辑删除");
        DcArea dcArea = dcAreaMapper.selectById(121);
        log.info("testLogicDelete,dcArea:"+dcArea);
        dcAreaMapper.deleteById(121);
        return AjaxResult.success("已删除");
    }

    /**
     * 测试valid校验
     */

    @PostMapping("/testValid")
    public AjaxResult testValid(@RequestBody @Valid TestQo qo) {
        log.info("testValid,参数："+ JSON.toJSONString(qo));
        return AjaxResult.success("");
    }

    /**
     * 测试抛出业务异常
     */

    @GetMapping("/testServiceException")
    public AjaxResult testServiceException( ) {
        log.info("testServiceException,：");
        throw new ServiceException("业务异常");
    }

    /**
     * 测试mybatisPlus的分页
     */

    @GetMapping("/testMybatisPage")
    public AjaxResult testMybatisPage( ) {
        log.info("testMybatisPage,：");

        IPage<DcArea> page=new Page<>();
        page.setCurrent(1);
        page.setSize(10);
        Wrapper<DcArea> wrappers= Wrappers.<DcArea>query().lambda();
        IPage<DcArea> dcAreaIPage = dcAreaMapper.selectPage(page, wrappers);
        return AjaxResult.success(dcAreaIPage);
    }



}

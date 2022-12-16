package com.ruoyi.performance.service;


import java.util.List;

/**
 * 参数配置 服务层
 * 
 * @author ruoyi
 */
public interface ITestService {
    /**
     * @Name:
     * @Description: 查询全部的项目部code
     * @Param:
     * @return:
     * @Author: LeiChen
     * @Date:2022/12/6 10:21
     *
     * */
    List<String> selectAllProjectCodes();
}

package com.ruoyi.performance.mapper;


import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
public interface TestMapper
{

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

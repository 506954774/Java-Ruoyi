package com.ruoyi.performance.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.performance.mapper.TestMapper;
import com.ruoyi.performance.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数配置 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class TestServiceImpl implements ITestService
{
    @Autowired
    private TestMapper configMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<String> selectAllProjectCodes() {
        return configMapper.selectAllProjectCodes();
    }
}

package com.ruoyi.performance.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.performance.domain.AssessTemp;
import com.ruoyi.performance.domain.PerformanceManager;

/**
 * 参数配置 服务层
 * 
 * @author ruoyi
 */
public interface IPerformanceManagerService extends IService<PerformanceManager> {
       public void insert(PerformanceManager performanceManager);
}

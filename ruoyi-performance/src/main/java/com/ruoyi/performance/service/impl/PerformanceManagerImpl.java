package com.ruoyi.performance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.performance.domain.AssessItems;
import com.ruoyi.performance.domain.Mark;
import com.ruoyi.performance.domain.PerformanceManager;
import com.ruoyi.performance.mapper.AssessItemsMapper;
import com.ruoyi.performance.mapper.AssessTempMapper;
import com.ruoyi.performance.mapper.MarkMapper;
import com.ruoyi.performance.mapper.PerformanceManagerMapper;
import com.ruoyi.performance.service.IPerformanceManagerService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数配置 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class PerformanceManagerImpl extends ServiceImpl<PerformanceManagerMapper, PerformanceManager>  implements IPerformanceManagerService {

    @Autowired
    public PerformanceManagerMapper performanceManagerMapper;
    @Autowired
    public AssessTempMapper assessTempMapper;
    @Autowired
    public MarkMapper markMapper;
    @Autowired
    public AssessItemsMapper assessItemsMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(PerformanceManager performanceManager) {
        final val id = performanceManagerMapper.insert(performanceManager);
        System.out.println(performanceManager);
        //遍历数据对应的评分项
       Integer tempid =  performanceManager.getTempid();
        final val temp = assessTempMapper.selectById(tempid);
        String group = temp.getItemgroup();
        final val split = group.split(",");
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            ids.add(Integer.valueOf(split[i]));
        }
        final val items = assessItemsMapper.selectBatchIds(ids);
        Mark mark = null;
        for (AssessItems item : items) {
            mark = new Mark();
            mark.setKpiname(item.getKpiname());
            mark.setItemname(item.getItemname());
            mark.setContent(item.getContent());
            mark.setScorecriteria(item.getScorecriteria());
            mark.setPercent(item.getPercent());
            mark.setTotalscore(item.getTotalscore());
            mark.setSelfscore(BigDecimal.valueOf(0));
            mark.setLeadscore(BigDecimal.valueOf(0));
            mark.setActualscore(BigDecimal.valueOf(0));
            mark.setPid(performanceManager.getId());
            mark.setCreatetime(performanceManager.getCreatetime());
            mark.setCreatename(performanceManager.getCreatename());
            markMapper.insert(mark);
        }


    }
}

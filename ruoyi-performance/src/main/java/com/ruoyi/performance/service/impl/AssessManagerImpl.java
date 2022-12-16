package com.ruoyi.performance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.performance.domain.AssessManager;
import com.ruoyi.performance.mapper.AssessManagerMapper;
import com.ruoyi.performance.mapper.TestMapper;
import com.ruoyi.performance.service.IAssessManagerService;
import com.ruoyi.performance.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 参数配置 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class AssessManagerImpl extends ServiceImpl<AssessManagerMapper, AssessManager>  implements IAssessManagerService {

    @Autowired
    public AssessManagerMapper assessManagerMapper;

    @Override
    public Integer selecttotalnum(Integer deptid) {
        return assessManagerMapper.selecttotalnum(deptid);
    }


    @Override
    public List<Map> selectlistforusers(Integer deptid) {
        return assessManagerMapper.selectlistforusers(deptid);
    }


    @Override
    public Map<String,String> selectpostforuser(Integer userid) {
        return assessManagerMapper.selectpostforuser(userid);
    }
}

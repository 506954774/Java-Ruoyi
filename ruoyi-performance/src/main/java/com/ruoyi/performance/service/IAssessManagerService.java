package com.ruoyi.performance.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.performance.domain.AssessManager;

import java.util.List;
import java.util.Map;

/**
 * 参数配置 服务层
 * 
 * @author ruoyi
 */
public interface IAssessManagerService extends IService<AssessManager> {
       public Integer selecttotalnum(Integer deptid);

       public List<Map>selectlistforusers(Integer deptid);

       public Map<String,String> selectpostforuser(Integer userid);
}

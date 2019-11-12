package org.jeecg.modules.test.business.service.impl;

import org.jeecg.modules.test.business.entity.SaasTenant;
import org.jeecg.modules.test.business.mapper.SaasTenantMapper;
import org.jeecg.modules.test.business.service.ISaasTenantService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 租户表
 * @Author: jeecg-boot
 * @Date:   2019-11-04
 * @Version: V1.0
 */
@Service
public class SaasTenantServiceImpl extends ServiceImpl<SaasTenantMapper, SaasTenant> implements ISaasTenantService {

}

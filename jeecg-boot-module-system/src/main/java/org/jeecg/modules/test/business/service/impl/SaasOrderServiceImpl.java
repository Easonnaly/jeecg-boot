package org.jeecg.modules.test.business.service.impl;

import org.jeecg.modules.test.business.entity.SaasOrder;
import org.jeecg.modules.test.business.mapper.SaasOrderMapper;
import org.jeecg.modules.test.business.service.ISaasOrderService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2019-11-04
 * @Version: V1.0
 */
@Service
public class SaasOrderServiceImpl extends ServiceImpl<SaasOrderMapper, SaasOrder> implements ISaasOrderService {

}

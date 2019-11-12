package org.jeecg.modules.test.test.service.impl;

import org.jeecg.modules.test.test.entity.TestDemo;
import org.jeecg.modules.test.test.mapper.TestDemoMapper;
import org.jeecg.modules.test.test.service.ITestDemoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 测试
 * @Author: jeecg-boot
 * @Date:   2019-10-31
 * @Version: V1.0
 */
@Service
public class TestDemoServiceImpl extends ServiceImpl<TestDemoMapper, TestDemo> implements ITestDemoService {

}

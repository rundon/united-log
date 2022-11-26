package com.onefly.united.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.onefly.united.common.constant.LogMessageDto;
import com.onefly.united.common.redis.RedisMqUtil;
import com.onefly.united.log.entity.SysLogErrorEntity;
import com.onefly.united.log.entity.SysLogLoginEntity;
import com.onefly.united.log.entity.SysLogOperationEntity;
import com.onefly.united.log.service.SysLogErrorService;
import com.onefly.united.log.service.SysLogLoginService;
import com.onefly.united.log.service.SysLogOperationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Order
@Slf4j
@Component
@DependsOn(value = "springContextUtils")
public class RedisMessageListener {

    @Autowired
    private SysLogLoginService sysLogLoginService;

    @Autowired
    private SysLogErrorService sysLogErrorService;

    @Autowired
    private SysLogOperationService sysLogOperationService;

    @PostConstruct
    public void startTask() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new ConvertTask(sysLogLoginService, sysLogErrorService, sysLogOperationService));
        log.info("队列处理日志启动完成 ");
    }

    static class ConvertTask implements Runnable {

        private final Logger logger = LoggerFactory.getLogger(ConvertTask.class);

        private final SysLogLoginService sysLogLoginService;

        private final SysLogErrorService sysLogErrorService;

        private final SysLogOperationService sysLogOperationService;

        public ConvertTask(SysLogLoginService sysLogLoginService,
                           SysLogErrorService sysLogErrorService,
                           SysLogOperationService sysLogOperationService) {
            this.sysLogLoginService = sysLogLoginService;
            this.sysLogErrorService = sysLogErrorService;
            this.sysLogOperationService = sysLogOperationService;
        }

        @Override
        public void run() {
            while (true) {
                String message = null;
                try {
                    message = RedisMqUtil.takeQueueTask();
                    if (message != null) {
                        ////logType 01 登录 02 操作 03 错误
                        LogMessageDto dto = JSON.parseObject(message, LogMessageDto.class);
                        if ("01".equals(dto.getLogType())) {// 登录
                            SysLogLoginEntity entity = JSON.parseObject(JSON.toJSONString(dto.getData()), SysLogLoginEntity.class);
                            sysLogLoginService.save(entity);
                        } else if ("02".equals(dto.getLogType())) {//操作
                            SysLogOperationEntity entity = JSON.parseObject(JSON.toJSONString(dto.getData()), SysLogOperationEntity.class);
                            sysLogOperationService.save(entity);
                        } else {//错误
                            SysLogErrorEntity entity = JSON.parseObject(JSON.toJSONString(dto.getData()), SysLogErrorEntity.class);
                            sysLogErrorService.save(entity);
                        }
                    }
                } catch (Exception e) {
                    try {
                        Thread.sleep(1000 * 10);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    logger.info("处理日志异常，url：{}", message, e);
                }
            }
        }
    }
}

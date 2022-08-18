package com.onefly.united.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.onefly.united.common.constant.LogMessageDto;
import com.onefly.united.log.entity.SysLogErrorEntity;
import com.onefly.united.log.entity.SysLogLoginEntity;
import com.onefly.united.log.entity.SysLogOperationEntity;
import com.onefly.united.log.service.SysLogErrorService;
import com.onefly.united.log.service.SysLogLoginService;
import com.onefly.united.log.service.SysLogOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisMessageListener implements MessageListener {
    @Autowired
    private SysLogLoginService sysLogLoginService;

    @Autowired
    private SysLogErrorService sysLogErrorService;

    @Autowired
    private SysLogOperationService sysLogOperationService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        ////logType 01 登录 02 操作 03 错误
        LogMessageDto dto = JSON.parseObject(message.toString(), LogMessageDto.class);
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
}

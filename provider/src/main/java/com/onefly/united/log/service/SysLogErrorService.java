package com.onefly.united.log.service;


import com.onefly.united.common.page.PageData;
import com.onefly.united.common.service.BaseService;
import com.onefly.united.log.dto.SysLogErrorDTO;
import com.onefly.united.log.entity.SysLogErrorEntity;
import java.util.List;
import java.util.Map;

/**
 * 异常日志
 *
 * @author rundon
 * @since 1.0.0
 */
public interface SysLogErrorService extends BaseService<SysLogErrorEntity> {

    PageData<SysLogErrorDTO> page(Map<String, Object> params);

    List<SysLogErrorDTO> list(Map<String, Object> params);

    void save(SysLogErrorEntity entity);

}
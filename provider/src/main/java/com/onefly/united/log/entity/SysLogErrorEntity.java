package com.onefly.united.log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 异常日志
 *
 * @author rundon
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log_error")
public class SysLogErrorEntity {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 操作IP
     */
    private String ip;
    /**
     * 异常信息
     */
    private String errorInfo;
    /**
     * 创建者
     */
    private Long creator;
    /**
     * 创建时间
     */
    private Date createDate;
}
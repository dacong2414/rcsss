package com.ambition.rcsss.service;

import com.ambition.rcsss.model.entity.SysLog;

/**
 * 系统日志
 *
 * @author cyh
 * @version $Id: SysLogService.java, v 0.1 2016年11月1日 下午3:57:16 cyh Exp $
 */
public interface SysLogService {
    /**
     * 数据插入log数据
     *
     * @param sysLog
     */
    public void addSysLog(SysLog sysLog);
}

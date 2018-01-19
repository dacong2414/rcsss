package com.ambition.rcsss.service;

/**
 * 用户服务
 *
 * @author ambition
 * @version $Id: UserInfoService.java, v 0.1 2017年8月17日 上午10:05:05 ambition Exp $
 */
public interface MonitorClientMappingService {

    /**
     * 监控端选择监控诊室
     * @param monitorUid
     * @param clientUid
     */
    public Boolean modClient(Long clientUid, Long monitorUid);

}

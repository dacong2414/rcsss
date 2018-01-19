package com.ambition.rcsss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambition.rcsss.dao.BaseConfigDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.entity.MonitorClientMapping;
import com.ambition.rcsss.service.MonitorClientMappingService;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoServiceImpl.java, v 0.1 2017年8月17日 上午10:11:45 ambition Exp $
 */
@Service
public class MonitorClientMappingServiceImpl extends BaseService implements
                                                                MonitorClientMappingService {
    @Autowired
    BaseConfigDao   baseConfigDao;

    @Autowired
    MysqlDaoSupport mysqlDaoSupport;

    /** 
     * @param monitorUid
     * @param clientUid
     * @return
     * @see com.ambition.rcsss.service.MonitorClientMappingService#chooseClient(java.lang.Long, java.lang.Long)
     */
    @Override
    public Boolean modClient(Long clientUid, Long monitorUid) {
        MonitorClientMapping monitorClientMappingDB = baseConfigDao
            .getMonitorClientMappingByClientUidAndMonitorUid(clientUid, monitorUid);
        if (monitorClientMappingDB == null) {
            monitorClientMappingDB = new MonitorClientMapping();
            monitorClientMappingDB.setClientUid(clientUid);
            monitorClientMappingDB.setMonitorUid(monitorUid);
            mysqlDaoSupport.save(monitorClientMappingDB);
        } else {
            mysqlDaoSupport.delete(monitorClientMappingDB);
        }
        return true;
    }
}

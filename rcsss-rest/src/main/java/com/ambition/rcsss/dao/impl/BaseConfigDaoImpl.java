package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.BaseConfigDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.MonitorClientMapping;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class BaseConfigDaoImpl extends MysqlDaoSupport implements BaseConfigDao {

    /** 
     * 我监控端监控了多少客户端
     * @param uId
     * @param roleNameInnerClient
     * @return
     * @see com.ambition.rcsss.dao.BaseConfigDao#getClientbyuId(java.lang.Long, java.lang.String)
     */
    @Override
    public List<MonitorClientMapping> getClientByuId(Long uId, String roleNameInnerClient) {
        String sql = "SELECT mcp.* FROM sys_roles sr, monitor_client_mapping mcp, sys_users_roles sur WHERE sr.role_name =:role_name AND sur.user_id = mcp.client_uid AND sur.role_id = sr.role_id AND sr.use_flag = 1 AND mcp.monitor_uid=:monitor_uid ";
        String[] keys = { "monitor_uid", "role_name" };
        Object[] values = { uId, roleNameInnerClient };
        return sqlExecuteList(MonitorClientMapping.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * 我客户端被多少监控端监控
     * @param uId
     * @param roleNameMonitorDclient
     * @return
     * @see com.ambition.rcsss.dao.BaseConfigDao#getMonitorsbyuId(java.lang.Long, java.lang.String)
     */
    @Override
    public List<MonitorClientMapping> getMonitorsByuId(Long uId, String roleNameMonitorDclient) {
        String sql = "SELECT mcp.* FROM sys_roles sr, monitor_client_mapping mcp, sys_users_roles sur WHERE sr.role_name =:role_name AND sur.user_id = mcp.monitor_uid AND sur.role_id = sr.role_id AND sr.use_flag = 1 AND mcp.client_uid=:client_uid ";
        String[] keys = { "client_uid", "role_name" };
        Object[] values = { uId, roleNameMonitorDclient };
        return sqlExecuteList(MonitorClientMapping.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /**
     * 获取基本配置  公共配置
     * @return
     * @see com.ambition.rcsss.dao.BaseConfigDao#getCommonConfigByFieldId(java.lang.Long)
     */
    @Override
    public List<Object[]> getCommonConfig() {
        String sql = "SELECT cv.property_name_en,cv.property_value ,cv.client_id FROM config_view cv WHERE cv.global_flag=1 AND cv.disable_flag=1";
        String[] keys = {};
        Object[] values = {};
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @param clientId
     * @param monitorId
     * @return
     * @see com.ambition.rcsss.dao.BaseConfigDao#getMonitorClientMappingByClientUidAndMonitorUid(java.lang.Long, java.lang.Long)
     */
    @Override
    public MonitorClientMapping getMonitorClientMappingByClientUidAndMonitorUid(Long clientUid,
                                                                                Long monitorUid) {
        String[] keys = { "clientUid", "monitorUid" };
        Object[] values = { clientUid, monitorUid };
        return criteriaExecuteUniqueResult(MonitorClientMapping.class, keys, values);
    }

    /** 
     * 获取所有的监控关系
     * @return
     * @see com.ambition.rcsss.dao.BaseConfigDao#getMonitorRelationList()
     */
    @Override
    public List<MonitorClientMapping> getMonitorRelationList() {
        String[] keys = {};
        Object[] values = {};
        return criteriaExecuteList(MonitorClientMapping.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

}

package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.UpdateConfigDao;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.UpdateConfig;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class UpdateConfigDaoImpl extends MysqlDaoSupport implements UpdateConfigDao {

    /** 
     * @param updateType
     * @param sysInfo
     * @return
     * @see com.ambition.rcsss.dao.UpdateConfigDao#getUpdateConfigBysysInfoAndupdateType(java.lang.String, java.lang.String)
     */
    @Override
    public List<UpdateConfig> getUpdateConfigBysysInfoAndupdateType(String updateType,
                                                                    String sysInfo) {

        String sql = "SELECT * FROM update_config WHERE sys_info=:sys_info AND update_type=:update_type GROUP BY rec_id DESC";
        String[] keys = { "sys_info", "update_type" };
        Object[] values = { sysInfo, updateType };
        return sqlExecuteList(UpdateConfig.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.dao.UpdateConfigDao#getSys()
     */
    @Override
    public List<Object[]> getSysByFieldId() {
        String sql = "SELECT DISTINCT a.update_type, a.sys_info FROM update_config a ";
        String[] keys = {};
        Object[] values = {};
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.dao.UpdateConfigDao#getNewestUpdateConfig()
     */
    @Override
    public UpdateConfig getNewestUpdateConfig() {
        String[] orderDesc = { "recId" };
        List<UpdateConfig> listDbConfigs = criteriaExecuteList(UpdateConfig.class,
            IGlobalConstant.NO_PAGE_QUERY, IGlobalConstant.NO_PAGE_QUERY, null, null, orderDesc,
            null);
        if (listDbConfigs.size() > 0) {
            return listDbConfigs.get(0);
        }
        return null;
    }
}

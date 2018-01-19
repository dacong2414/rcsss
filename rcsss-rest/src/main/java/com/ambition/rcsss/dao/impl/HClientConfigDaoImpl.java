package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.HClientConfigDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.ClientProperty;
import com.ambition.rcsss.model.entity.PropertyInfo;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class HClientConfigDaoImpl extends MysqlDaoSupport implements HClientConfigDao {

    /** 
     * @param macAddress
     * @return
     * @see com.ambition.rcsss.dao.HClientConfigDao#getHClientConfigByMacAddress(java.lang.String)
     */
    @Override
    public List<Object[]> getHClientConfigByMacAddress(String macAddress) {
        String sql = "SELECT cv.property_name_en, cv.property_value FROM config_view cv WHERE (cv.global_flag = 0 OR cv.global_flag=2) AND cv.disable_flag = 1 AND cv.mac_address =:mac_address ";
        String[] keys = { "mac_address" };
        Object[] values = { macAddress };
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @param propertyNameEn
     * @return
     * @see com.ambition.rcsss.dao.HClientConfigDao#getPropertyInfoByPropertyNameEn(java.lang.String, java.lang.Long)
     */
    @Override
    public PropertyInfo getPropertyInfoByPropertyNameEn(String propertyNameEn) {
        String[] keys = { "propertyNameEn" };
        Object[] values = { propertyNameEn };
        return criteriaExecuteUniqueResult(PropertyInfo.class, keys, values);
    }

    /** 
     * @param macAdress
     * @param propertyNameEn
     * @param fieldId
     * @return
     * @see com.ambition.rcsss.dao.HClientConfigDao#getClientPropertyByPropertyNameEn(java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    public ClientProperty getClientPropertyByMacAdressAndPropertyNameEn(String macAdress,
                                                                        String propertyNameEn) {
        String sql = "SELECT cv.property_id, cv.property_value, cv.client_id FROM config_view cv WHERE cv.mac_address =:mac_address AND cv.property_name_en =:property_name_en ";
        String[] keys = { "mac_address", "property_name_en" };
        Object[] values = { macAdress, propertyNameEn, };
        return sqlExecuteUniqueResult(ClientProperty.class, sql, keys, values);
    }
}

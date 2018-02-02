package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.ClientInfoDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.ClientInfo;
import com.ambition.rcsss.model.entity.ClientProperty;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class ClientInfoDaoImpl extends MysqlDaoSupport implements ClientInfoDao {

    /** 
     * @param clientId
     * @return
     * @see com.ambition.rcsss.dao.ClientInfoDao#getClientInfoByClientId(java.lang.Long)
     */
    @Override
    public ClientInfo getClientInfoByClientId(Long clientId) {
        String[] keys = { "clientId" };
        Object[] values = { clientId };
        return criteriaExecuteUniqueResult(ClientInfo.class, keys, values);
    }

    /** 
     * @param clientId
     * @return
     * @see com.ambition.rcsss.dao.ClientInfoDao#getAllClientPropertyByClinetId(java.lang.Long)
     */
    @Override
    public List<ClientProperty> getAllClientPropertyByClinetId(Long clientId) {
        String[] keys = { "clientId" };
        Object[] values = { clientId };
        return criteriaExecuteList(ClientProperty.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    @Override
    public void deleteClientProperty(Long clientId) {
        String sql = "DELETE FROM client_property WHERE client_id=:client_id";
        String[] keys = { "client_id" };
        Object[] values = { clientId };
        sqlExecuteUpdate(sql, keys, values);
    }

    @Override
    public Boolean deleteClientInfo(Long clientId) {
        String sql = "DELETE FROM client_info WHERE client_id=:client_id";
        String[] keys = { "client_id" };
        Object[] values = { clientId };
        return sqlExecuteUpdate(sql, keys, values);
    }

    /** 
     * @param clientName
     * @return
     * @see com.ambition.rcsss.dao.ClientInfoDao#getClientInfoByClientNameAndFieldId(java.lang.String, java.lang.Long)
     */
    @Override
    public ClientInfo getClientInfoByClientNameAndFieldId(String clientName) {
        String[] keys = { "clientName" };
        Object[] values = { clientName };
        return criteriaExecuteUniqueResult(ClientInfo.class, keys, values);
    }

    /** 
     * @param macAddress
     * @return
     * @see com.ambition.rcsss.dao.ClientInfoDao#getClientInfoByMacAddressAndFieldId(java.lang.String, java.lang.Long)
     */
    @Override
    public ClientInfo getClientInfoByMacAddress(String macAddress) {
        String[] keys = { "macAddress" };
        Object[] values = { macAddress };
        return criteriaExecuteUniqueResult(ClientInfo.class, keys, values);
    }

}

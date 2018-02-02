package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.model.entity.ClientInfo;
import com.ambition.rcsss.model.entity.ClientProperty;

public interface ClientInfoDao {

    /**
     * 
     * @param clientId
     * @return
     */
    ClientInfo getClientInfoByClientId(Long clientId);

    /**
     * 获取所有客户端属性
     *
     * @param clientId
     * @return
     */
    List<ClientProperty> getAllClientPropertyByClinetId(Long clientId);

    /**
     * 删除所有的ClientProperty
     *
     * @param clientId
     */
    public void deleteClientProperty(Long clientId);

    /**
     * 删除客户端信息
     * @param clientId
     * @return
     */
    Boolean deleteClientInfo(Long clientId);

    /**
     * 获取clientInfo
     * @param clientName
     */
    ClientInfo getClientInfoByClientNameAndFieldId(String clientName);

    /**
     * 获取clientInfo
     * @param macAddress
     * @return
     */
    ClientInfo getClientInfoByMacAddress(String macAddress);

}

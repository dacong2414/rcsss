package com.ambition.rcsss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambition.rcsss.dao.ClientInfoDao;
import com.ambition.rcsss.dao.HClientConfigDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.entity.ClientInfo;
import com.ambition.rcsss.model.entity.ClientProperty;
import com.ambition.rcsss.service.ClientInfoService;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoServiceImpl.java, v 0.1 2017年8月17日 上午10:11:45 ambition Exp $
 */
@Service
public class ClientInfoServiceImpl extends BaseService implements ClientInfoService {
    @Autowired
    ClientInfoDao    clientInfoDao;

    @Autowired
    MysqlDaoSupport  mysqlDaoSupport;
    @Autowired
    HClientConfigDao hClientConfigDao;

    /** 
     * @param clientInfo
     * @return
     * @see com.ambition.rcsss.service.ClientInfoService#modOrAddClientInfo(com.ambition.rcsss.model.entity.ClientInfo)
     */
    @Override
    public Boolean modOrAddClientInfo(ClientInfo clientInfo) {
        Long clientId = clientInfo.getClientId();
        ClientInfo clientInfoDB = clientInfoDao.getClientInfoByClientId(clientId);
        if (clientInfoDB == null) {
            clientInfoDB = new ClientInfo();
            clientInfoDB.setMacAddress(clientInfo.getMacAddress().toUpperCase());//mac地址大写字母
            clientInfoDB.setClientName(clientInfo.getClientName());

        } else {
            clientInfoDB.setMacAddress(clientInfo.getMacAddress().toUpperCase());
            clientInfoDB.setClientName(clientInfo.getClientName());
        }
        mysqlDaoSupport.saveOrUpdate(clientInfoDB);
        //先删除 在新增
        clientInfoDao.deleteClientProperty(clientId);
        List<ClientProperty> listClientProperty = clientInfo.getListClientProperty();
        if (listClientProperty != null && listClientProperty.size() > 0) {
            for (ClientProperty clientProperty : listClientProperty) {
                ClientProperty clientPropertyNew = new ClientProperty();
                clientPropertyNew.setClientId(clientInfoDB.getClientId());
                clientPropertyNew.setPropertyId(clientProperty.getPropertyId());
                clientPropertyNew.setPropertyValue(clientProperty.getPropertyValue());
                mysqlDaoSupport.save(clientPropertyNew);
            }
        }
        return true;
    }

    /** 
     * @param clientId
     * @return
     * @see com.ambition.rcsss.service.ClientInfoService#deleteClient(java.lang.Long)
     */
    @Override
    public Boolean deleteClient(Long clientId) {
        clientInfoDao.deleteClientProperty(clientId);//删除客户端配置的属性
        return clientInfoDao.deleteClientInfo(clientId);//删除客户端
    }

    /** 
     * @param clientName
     * @param clientId
     * @return
     * @see com.ambition.rcsss.service.ClientInfoService#isExistClientName(java.lang.String, java.lang.Long)
     */
    @Override
    public Boolean isExistClientName(String clientName, Long clientId) {
        ClientInfo clientInfoDB = clientInfoDao.getClientInfoByClientNameAndFieldId(clientName);
        if (clientInfoDB != null && !clientInfoDB.getClientId().equals(clientId)) {//不是自己，而且clientName名重复
            return false;
        }
        return true;
    }

    /** 
     * @param macAddress
     * @param clientId
     * @return
     * @see com.ambition.rcsss.service.ClientInfoService#isExistMacAddress(java.lang.String, java.lang.Long)
     */
    @Override
    public Boolean isExistMacAddress(String macAddress, Long clientId) {
        ClientInfo clientInfoDB = clientInfoDao.getClientInfoByMacAddress(macAddress);
        if (clientInfoDB != null && !clientInfoDB.getClientId().equals(clientId)) {//不是自己，而且mac地址名重复
            return false;
        }
        return true;
    }

    /** 
     * @param clientId
     * @return
     * @see com.ambition.rcsss.service.ClientInfoService#getClientInfo(java.lang.Long)
     */
    @Override
    public ClientInfo getClientInfo(Long clientId) {
        ClientInfo clientInfoDB = clientInfoDao.getClientInfoByClientId(clientId);
        if (clientInfoDB != null) {
            List<ClientProperty> clientPropertiesListDB = clientInfoDao
                .getAllClientPropertyByClinetId(clientId);
            clientInfoDB.setListClientProperty(clientPropertiesListDB);
        }
        return clientInfoDB;
    }

    /** 
     * @param macAddress
     * @return
     * @see com.ambition.rcsss.service.ClientInfoService#getClientInfo2C(java.lang.String)
     */
    @Override
    public ClientInfo getClientInfo2C(String macAddress) {
        ClientInfo clientInfoDB = clientInfoDao.getClientInfoByMacAddress(macAddress);
        if (clientInfoDB != null) {
            List<ClientProperty> clientPropertiesListDB = clientInfoDao
                .getAllClientPropertyByClinetId(clientInfoDB.getClientId());
            clientInfoDB.setListClientProperty(clientPropertiesListDB);
        }
        return clientInfoDB;
    }
}

package com.ambition.rcsss.service;

import com.ambition.rcsss.model.entity.ClientInfo;

/**
 * 客户端
 *
 * @author ambition
 * @version $Id: UserInfoService.java, v 0.1 2017年8月17日 上午10:05:05 ambition Exp $
 */
public interface ClientInfoService {

    /**
     * 新增或修改客户端
     * @param clientInfo
     * @return
     */
    public Boolean modOrAddClientInfo(ClientInfo clientInfo);

    /**
     * 删除客户端信息
     * @param clientId
     * @return
     */
    public Boolean deleteClient(Long clientId);

    /**
     * 客户端名称是否存在
     * @param clientName
     * @param clientId
     * @return
     */
    public Boolean isExistClientName(String clientName, Long clientId);

    /**
     * mac地址是否重复
     * @param macAddress
     * @param clientId
     * @return
     */
    public Boolean isExistMacAddress(String macAddress, Long clientId);

    /**
     * 获取客户端信息用于回显
     * @param clientId
     * @return
     */
    public ClientInfo getClientInfo(Long clientId);

    /**
     * 获取客户端信息用于回显
     * @param macAddress
     * @return
     */
    public ClientInfo getClientInfo2C(String macAddress);

}

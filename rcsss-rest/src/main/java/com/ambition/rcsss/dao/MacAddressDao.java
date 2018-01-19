package com.ambition.rcsss.dao;

import com.ambition.rcsss.model.entity.MacAddress;

public interface MacAddressDao {

    /**
     * 获取mac地址
     * @param macAddress
     * @return
     */
    MacAddress getMacAddressByMAC(String macAddress);

}

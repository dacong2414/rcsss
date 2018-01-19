package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.model.entity.ClientProperty;
import com.ambition.rcsss.model.entity.PropertyInfo;

public interface HClientConfigDao {

    /**
     * 根据mac地址 和域id获取hclientconfig  showconfig
     * @param macAddress
     * @return
     */
    List<Object[]> getHClientConfigByMacAddress(String macAddress);

    /**
     *  根据属性英文名称，和fieldId来获取一个propertyInfo对象(新增属性时 英文名称要做唯一校验)
     *
     * @param propertyNameEn
     * @return
     */
    PropertyInfo getPropertyInfoByPropertyNameEn(String propertyNameEn);

    /**
     *  根据属性macAdress 英文名称，和fieldId来获取一个ClientProperty对象
     *
     * @param macAdress
     * @param propertyNameEn
     * @return
     */
    ClientProperty getClientPropertyByMacAdressAndPropertyNameEn(String macAdress,
                                                                 String propertyNameEn);

}

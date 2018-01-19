package com.ambition.rcsss.dao.impl;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.MacAddressDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.entity.MacAddress;
import com.ambition.rcsss.model.entity.UserGroup;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class MacAddressDaoImpl extends MysqlDaoSupport implements MacAddressDao {

    /** 
     * @param macStr
     * @return
     * @see com.ambition.rcsss.dao.MacAddressDao#getMacAddress(java.lang.String)
     */
    @Override
    public MacAddress getMacAddressByMAC(String macAddress) {
        String[] keys = { "macAddress" };
        Object[] values = { macAddress };
        return criteriaExecuteUniqueResult(UserGroup.class, keys, values);
    }

}

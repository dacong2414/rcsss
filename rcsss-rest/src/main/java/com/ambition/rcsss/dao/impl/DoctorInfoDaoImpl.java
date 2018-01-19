package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.DoctorInfoDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.DoctorInfo;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class DoctorInfoDaoImpl extends MysqlDaoSupport implements DoctorInfoDao {

    /** 
     * @param hospitalName
     * @return
     * @see com.ambition.rcsss.dao.DoctorInfoDao#getDoctorByHospitalName(java.lang.String)
     */
    @Override
    public List<DoctorInfo> getDoctorByHospitalName(String hospitalName) {
        String[] keys = { "hospitalName" };
        Object[] values = { hospitalName };
        return criteriaExecuteList(DoctorInfo.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

}

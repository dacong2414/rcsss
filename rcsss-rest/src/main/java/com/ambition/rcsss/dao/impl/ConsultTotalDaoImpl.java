package com.ambition.rcsss.dao.impl;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.ConsultTotalDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.entity.ConsultTotal;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoDaoImpl.java, v 0.1 2017年8月22日 下午2:17:45 ambition Exp $
 */
@Repository
public class ConsultTotalDaoImpl extends MysqlDaoSupport implements ConsultTotalDao {

    /** 
     * 获取会诊总览信息
     * @param uId
     * @param billNum
     * @return
     * @see com.ambition.rcsss.dao.ConsultTotalDao#getByUIdAndBillNum(java.lang.String, java.lang.String)
     */
    @Override
    public ConsultTotal getByUIdAndBillNum(Long uId, String billNum) {
        String[] keys = { "uId", "billNum" };
        Object[] values = { uId, billNum };
        return criteriaExecuteUniqueResult(ConsultTotal.class, keys, values);
    }

    /** 
     * @param recId
     * @return
     * @see com.ambition.rcsss.dao.ConsultTotalDao#getByRecId(java.lang.Long)
     */
    @Override
    public ConsultTotal getByRecId(Long recId) {
        String[] keys = { "recId" };
        Object[] values = { recId };
        return criteriaExecuteUniqueResult(ConsultTotal.class, keys, values);
    }

}

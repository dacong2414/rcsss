package com.ambition.rcsss.dao;

import com.ambition.rcsss.model.entity.ConsultTotal;

public interface ConsultTotalDao {

    /**
     * 获取会诊总览信息
     * @param uId
     * @param billNum
     * @return
     */
    ConsultTotal getByUIdAndBillNum(Long uId, String billNum);

    /**
     * 
     * @param recId
     * @return
     */
    ConsultTotal getByRecId(Long recId);

}

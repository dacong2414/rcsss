package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.model.entity.DoctorInfo;

public interface DoctorInfoDao {

    /**
     * 获取医生列表
     *
     * @param hospitalName
     * @return
     */
    List<DoctorInfo> getDoctorByHospitalName(String hospitalName);

}

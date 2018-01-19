package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.model.entity.UpdateConfig;

public interface UpdateConfigDao {

    /**
     *获取这个系统的最新数据  根据updateType（hclient）  sysInfo 
     * @param updateType
     * @param sysInfo
     * @return
     */
    List<UpdateConfig> getUpdateConfigBysysInfoAndupdateType(String updateType, String sysInfo);

    /**
     * 
     * 获取系统信息
     * @return
     */
    List<Object[]> getSysByFieldId();

    /**
     * 获取最新文件配置
     * @return
     */
    UpdateConfig getNewestUpdateConfig();

}

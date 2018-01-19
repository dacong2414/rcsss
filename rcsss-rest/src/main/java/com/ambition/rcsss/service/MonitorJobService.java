package com.ambition.rcsss.service;

import java.util.List;

import com.ambition.rcsss.model.pojo.sendmessage2c.consult.ApiStatusVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.consult.UrlDescVo;

/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.service, v 0.1 2017/11/22 16:24 hhu Exp $$
 */
public interface MonitorJobService {
    /**
     * 更新表状态信息
     */
    void updateTableStats();

    /**
     * 收集新增表状态信息
     */
    void addNewTable();

    /**
     * 统计接口与数据库表的变化情况，并更新到缓存
     */
    void updateApiDb();

    /**
     * 获取系统中接口状态
     *
     * @return 接口状态集合
     */
    List<ApiStatusVo> getApiStatus();

    /**
     * 根据api获取状态
     *
     * @param apiKey api全路径
     * @return 状态值
     */
    String getApiStatusByApi(String apiKey);

    /**
     * 获取所有接口信息
     *
     * @return 接口信息集合
     */
    List<UrlDescVo> getUrls();
}
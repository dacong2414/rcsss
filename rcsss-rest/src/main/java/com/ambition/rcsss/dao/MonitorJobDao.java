package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.model.entity.HttpSendFailedResend;
import com.ambition.rcsss.model.entity.RcsssTableStatsEntity;

/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.dao, v 0.1 2017/11/22 11:30 hhu Exp $$
 */
public interface MonitorJobDao extends CrudDao {
    /**
     * 获取表当前实时信息
     *
     * @param tableName 表名
     * @return 表状态
     */
    RcsssTableStatsEntity getTableStatsNow(String tableName);

    /**
     * 收集并添加（含有gmt_mod的表）新表状态信息
     *
     * @return 更新数据库是否成功
     */
    Boolean insertNewTable();

    /**
     *  取发送失败的
     *
     * @param flag
     * @return
     */

    List<HttpSendFailedResend> getHttpSendFailedResends(Long flag);
}
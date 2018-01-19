package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.ambition.rcsss.dao.MonitorJobDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.HttpSendFailedResend;
import com.ambition.rcsss.model.entity.RcsssTableStatsEntity;

/**
 * 接口相关数据查询类
 * UisTableStatsEntity.class、ApiDbRelationEntity.class相关实体
 *
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.dao.impl, v 0.1 2017/11/22 14:02 hhu Exp $$
 */
@Service
public class MonitorJobDaoImpl extends MysqlDaoSupport implements MonitorJobDao {
    public MonitorJobDaoImpl(HibernateTemplate hibernateTemplate) {
        super(hibernateTemplate);
    }

    @Override
    public RcsssTableStatsEntity getTableStatsNow(String tableName) {
        String hql = "SELECT '"
                     + tableName
                     + "' AS tableName, 0 as counter,"
                     + " (SELECT gmt_mod FROM "
                     + tableName
                     + " ORDER BY gmt_mod DESC LIMIT 1) AS lastRowUpdate,"
                     + " (SELECT last_update FROM mysql.innodb_table_stats WHERE database_name = 'ambitionj2c'"
                     + " AND table_name = '" + tableName + "') AS lastColsUpdate,"
                     + " (SELECT NUM_ROWS FROM information_schema.innodb_sys_tablestats WHERE"
                     + " NAME = concat('ambitionj2c/', '" + tableName
                     + "')) AS lastNumRows FROM DUAL";
        /*
         * 通过Hql的方式获取到最新的表状态
         * 1、last_row_update 来自表的gmt_mod列排序为最新的时间
         * 2、表结构更新时间来自mysql.innodb_table_stats表last_update
         * 3、information_schema.innodb_sys_tablestats表NUM_ROWS列存储的是最新表总条数 last_num_rows
         * 将3个子查询，通过 FROM Dual行转列返回
         */
        return sqlNewObjectQuery(RcsssTableStatsEntity.class, hql);
    }

    @Override
    public Boolean insertNewTable() {
        /*
         * 通过sql的方式插入新的含有gmt_mod字段表信息（uis_table_stats）
         * 1、依赖information_schema.`COLUMNS`判断新增表是否有gmt_mod字段
         * 2、依赖information_schema.INNODB_SYS_TABLESTATS 获取到正确的表统计数据
         * 3、分别将表字段更新时间、表内数据更新时间统一设置为插入时间
         * 4、将更新计数器（counter列）设置为默认值0
         */
        String sql = "INSERT INTO rcsss_table_stats  ( table_name, counter, last_cols_update, last_row_update, "
                     + "last_num_rows )  SELECT i.table_name AS table_name, 0 AS counter, Now() AS last_cols_update, Now() "
                     + "AS last_row_update, t.NUM_ROWS AS last_num_rows FROM information_schema.INNODB_SYS_TABLESTATS t  "
                     + "RIGHT JOIN ( SELECT t.table_name FROM information_schema.`COLUMNS` t WHERE t.table_schema = 'ambitionj2c'  "
                     + "AND t.column_name = 'gmt_mod' AND NOT EXISTS ( SELECT 1 FROM ambitionj2c.rcsss_table_stats i  WHERE i"
                     + ".table_name = t.table_name )) i ON t. NAME = concat('ambitionj2c/', i.table_name) WHERE t.`NAME` LIKE 'ambitionj2c/%'";
        return sqlExecuteUpdate(sql);
    }

    /** 
     * @param flag
     * @return
     * @see com.ambition.rcsss.dao.MonitorJobDao#getHttpSendFailedResends(java.lang.Long)
     */
    @Override
    public List<HttpSendFailedResend> getHttpSendFailedResends(Long flag) {
        String[] keys = { "flag" };
        Object[] values = { flag };
        return criteriaExecuteList(HttpSendFailedResend.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }
}
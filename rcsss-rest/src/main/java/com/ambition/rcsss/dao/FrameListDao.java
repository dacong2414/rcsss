package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.common.frame.Page;

/**
 * 框架列表DAO接口类
 *
 * @author hhu
 * @version $Id: ImardLoginInfoDao.java, v 0.1 2017年6月12日 下午4:45:42 hhu Exp $
 */
public interface FrameListDao {

    /**
     * 根据主题sql获取数据
     * @param sql       查询语句
     * @param pageSize  分页信息 分页大小
     * @param pageIndex 分页信息 起始位置
     * @return
     */
    public List<?> getDataBySQL(Class entityClass, String sql, int pageSize, int pageIndex);

    /**
     * 获取总条数
     * @param tableNameAndQuery
     * @return
     */
    public Page initPage(String tableNameAndQuery, Page page);
}
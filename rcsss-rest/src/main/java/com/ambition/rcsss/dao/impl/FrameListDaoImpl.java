/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.common.frame.Page;
import com.ambition.rcsss.dao.FrameListDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;

/**
 * @author zhangxi
 * @version $Id: FrameListDaoImpl.java, v 0.1 2017年7月20日 上午11:49:45 zhangxi Exp $
 */
@Repository
public class FrameListDaoImpl extends MysqlDaoSupport implements FrameListDao {

    @Override
    public List<?> getDataBySQL(Class entityClass, String sql, int pageSize, int pageIndex) {
        return sqlExecuteList(entityClass, sql, pageSize, (pageIndex - 1));
    }

    @Override
    public Page initPage(String tableNameAndQuery, Page page) {
        Long totalPage = sqlExecuteCount(tableNameAndQuery, null, null, null);
        page.setTotalRows(totalPage.intValue());
        return page;
    }
}

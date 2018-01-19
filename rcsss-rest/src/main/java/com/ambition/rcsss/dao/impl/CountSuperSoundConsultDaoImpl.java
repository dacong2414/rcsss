package com.ambition.rcsss.dao.impl;

import com.ambition.rcsss.dao.CountSuperSoundConsultDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 *  统计超声会诊数据相关操作实现
 * Created by wxh on 2017/9/14.
 */
@Repository
public class CountSuperSoundConsultDaoImpl extends MysqlDaoSupport
                                           implements CountSuperSoundConsultDao {
    @Override
    public ArrayList<Object[]> listSuperSoundConsultDetail(Long linkId) {
        String sql = "SELECT ( SELECT title FROM user_info WHERE u_id = d.u_id ) AS user_name, ( SELECT title FROM user_info WHERE u_id = d.inviter_id ) AS inviter_name, d.opra, d.gmt_create FROM consult_detail d WHERE d.link_id =:linkId ORDER BY d.rec_id ASC";
        String[] keys = { "linkId" };
        Object[] values = { linkId };
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null, null);
    }
}

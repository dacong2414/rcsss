package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.MeetingInfoDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.MeetingInfo;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class MeetingInfoDaoImpl extends MysqlDaoSupport implements MeetingInfoDao {

    /** 
     * @return
     * @see com.ambition.rcsss.dao.MeetingInfoDao#getMeetingInfoAll()
     */
    @Override
    public List<MeetingInfo> getMeetingInfoAll() {
        String[] keys = {};
        Object[] values = {};
        return criteriaExecuteList(MeetingInfo.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param meetingName
     * @return
     * @see com.ambition.rcsss.dao.MeetingInfoDao#getMeetingInfoByMeetingName(java.lang.String)
     */
    @Override
    public MeetingInfo getMeetingInfoByMeetingName(String meetingName) {
        String[] keys = { "meetingName" };
        Object[] values = { meetingName };
        return criteriaExecuteUniqueResult(MeetingInfo.class, keys, values);
    }

    /**
     * 通过recId获取医生信息
     * @param recId
     */
    public MeetingInfo getMeetingInfoByRecId(Long recId) {
        String[] keys = { "recId" };
        Object[] values = { recId };
        return criteriaExecuteUniqueResult(MeetingInfo.class, keys, values);
    }

    /**
     * 获取所有MeetingClient的用户
     * @param kindName
     * @return
     */
    public List<Object[]> getMeetingClient(String roleName) {
        String sql = "SELECT l.u_id, l.title FROM user_info l, sys_roles sr, sys_users_roles sur WHERE l.u_id = sur.user_id AND sr.role_id = sur.role_id AND sr.role_name =:role_name";
        String[] keys = { "role_name" };
        Object[] values = { roleName };
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

}

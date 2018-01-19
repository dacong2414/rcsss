/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.ambition.rcsss.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambition.rcsss.dao.LogonInfoDao;
import com.ambition.rcsss.dao.MeetingInfoDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.entity.MeetingInfo;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.MeetingService;

/**
 * 会议实现
 *
 * @author ambition
 * @version $Id: DoctorServiceImpl.java, v 0.1 2017年7月3日 下午5:31:08 ambition Exp $
 */
@Service
public class MeetingServiceImpl extends BaseService implements MeetingService {
    @Autowired
    MeetingInfoDao  meetingInfoDao;
    @Autowired
    LogonInfoDao    LogonInfoDao;
    @Autowired
    MysqlDaoSupport mysqlDaoSupport;

    /** 
     * @param meetingInfo
     * @return
     * @see com.ambition.rcp.service.MeetingService#modOrAddMeeting(com.ambition.rcp.model.j2c.MeetingInfo)
     */
    @Override
    public Boolean modOrAddMeeting(MeetingInfo meetingInfo) {

        MeetingInfo meetingInfoDB = meetingInfoDao.getMeetingInfoByRecId(meetingInfo.getRecId());
        Calendar calendar = Calendar.getInstance();
        if (meetingInfoDB == null) {//新增
            meetingInfoDB = new MeetingInfo();
            meetingInfoDB.setCreateUserId(meetingInfo.getCreateUserId());
            meetingInfoDB.setGmtCreate(calendar.getTime());
            meetingInfoDB.setMeetingName(meetingInfo.getMeetingName());
            meetingInfoDB.setMeetingPassWord(meetingInfo.getMeetingPassWord());
        } else {
            //修改
            if (!isHavePermission(meetingInfoDB.getCreateUserId())) {//    校验是否可以被更改
                throw new ProcessException(CodeEnum.ERROR_5026);
            }
            meetingInfoDB.setCreateUserId(meetingInfo.getCreateUserId());
            meetingInfoDB.setGmtMod(calendar.getTime());
            meetingInfoDB.setMeetingName(meetingInfo.getMeetingName());
            meetingInfoDB.setMeetingPassWord(meetingInfo.getMeetingPassWord());
        }
        mysqlDaoSupport.saveOrUpdate(meetingInfoDB);
        return true;

    }

    /**
     * 是否可以更改 管理员和自己可以更改
     *
     * @param uId
     * @param request
     * @return
     */
    public Boolean isHavePermission(Long uId) {
        UserInfo userInfo = getCurrentLoginInfo().getUserInfo();
        //如果是管理员
        if (userInfo.getUserType().equals(UserInfo.USERTYPE_ADMIN_USER)) {
            return true;
        }
        //是会议管理者登录(会议主席)
        if (userInfo.getuId().equals(uId)) {
            return true;
        }
        return false;
    }

    /** 
     * @param recId
     * @return
     * @see com.ambition.rcp.service.MeetingService#deleteMeeting(java.lang.Long)
     */
    @Override
    public Boolean deleteMeeting(Long recId) {
        MeetingInfo meetingInfoDB = meetingInfoDao.getMeetingInfoByRecId(recId);
        if (!isHavePermission(meetingInfoDB.getCreateUserId())) {//校验是否可以被更改
            throw new ProcessException(CodeEnum.ERROR_5026);
        }
        mysqlDaoSupport.delete(meetingInfoDB);
        return true;
    }

    /** 
     * @return
     * @see com.ambition.rcp.service.MeetingService#getMeetingClient()
     */
    @Override
    public List<Object[]> getMeetingClient(String roleName) {
        return meetingInfoDao.getMeetingClient(roleName);
    }

    /** 
     * @param kindName
     * @return
     * @see com.ambition.rcp.service.MeetingService#isExist(java.lang.String)
     */
    @Override
    public Boolean isExist(String meetingName, Long recId) {
        MeetingInfo meetingInfoByNameDB = meetingInfoDao.getMeetingInfoByMeetingName(meetingName);
        if (meetingInfoByNameDB != null && recId != meetingInfoByNameDB.getRecId()) {//查到有重复的会议名称，并且不是自己的会议名称
            return false;
        }
        return true;
    }

    /** 
     * @param recId
     * @return
     * @see com.ambition.rcsss.service.MeetingService#getMeetingInfo(java.lang.Long)
     */
    @Override
    public MeetingInfo getMeetingInfo(Long recId) {
        return meetingInfoDao.getMeetingInfoByRecId(recId);
    }
}

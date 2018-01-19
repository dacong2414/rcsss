/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.ambition.rcsss.service;

import java.util.List;

import com.ambition.rcsss.model.entity.MeetingInfo;

/**
 * 会议service
 *
 * @author ambition
 * @version $Id: DoctorService.java, v 0.1 2017年7月3日 下午5:28:57 ambition Exp $
 */

public interface MeetingService {

    /**
     *新增或者修改会议
     * @param meetingInfo
     * @return
     */
    public Boolean modOrAddMeeting(MeetingInfo meetingInfo);

    /**
     * 删除会议
     * @param recId
     * @return
     */
    public Boolean deleteMeeting(Long recId);

    /**
     * 获取所有meetingClient的用户
     * @param roleName
     * @return
     */
    public List<Object[]> getMeetingClient(String roleName);

    /**
     * 判断是否存在
     * @param meetingName
     * @param recId
     * @return
     */
    public Boolean isExist(String meetingName, Long recId);

    /**
     * 获取meetingInfo用于回显
     * @param recId
     * @return
     */
    public MeetingInfo getMeetingInfo(Long recId);

}

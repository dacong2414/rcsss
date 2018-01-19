package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.model.entity.MeetingInfo;

public interface MeetingInfoDao {

    /**
     * 获取所有会议
     * @return
     */
    List<MeetingInfo> getMeetingInfoAll();

    /**
     * 获取会议信息
     *
     * @param meetingName
     * @return
     */
    MeetingInfo getMeetingInfoByMeetingName(String meetingName);

    /**
     * 通过recId获取医生信息
     * @param recId
     */
    public MeetingInfo getMeetingInfoByRecId(Long recId);

    /**
     * 获取所有MeetingClient的用户
     * @param roleName
     * @return
     */
    public List<Object[]> getMeetingClient(String roleName);

}

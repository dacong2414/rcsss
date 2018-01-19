package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteCMEETINGVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private String            meetingName;
    private String            meetingPassWord;
    private Long              createUserId;

    /**
     * Getter method for property <tt>meetingName</tt>.
     * 
     * @return property value of meetingName
     */
    public String getMeetingName() {
        return meetingName;
    }

    /**
     * Setter method for property <tt>meetingName</tt>.
     * 
     * @param meetingName value to be assigned to property meetingName
     */
    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    /**
     * Getter method for property <tt>meetingPassWord</tt>.
     * 
     * @return property value of meetingPassWord
     */
    public String getMeetingPassWord() {
        return meetingPassWord;
    }

    /**
     * Setter method for property <tt>meetingPassWord</tt>.
     * 
     * @param meetingPassWord value to be assigned to property meetingPassWord
     */
    public void setMeetingPassWord(String meetingPassWord) {
        this.meetingPassWord = meetingPassWord;
    }

    /**
     * Getter method for property <tt>createUserId</tt>.
     * 
     * @return property value of createUserId
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * Setter method for property <tt>createUserId</tt>.
     * 
     * @param createUserId value to be assigned to property createUserId
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

}

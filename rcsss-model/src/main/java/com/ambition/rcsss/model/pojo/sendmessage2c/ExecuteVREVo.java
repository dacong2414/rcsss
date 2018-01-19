package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteVREVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private String            patientId;
    private String            reportId;
    private String            checkEndTime;
    private Long              videoLength;
    private Long              videoId;

    /**
     * Getter method for property <tt>patientId</tt>.
     * 
     * @return property value of patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Setter method for property <tt>patientId</tt>.
     * 
     * @param patientId value to be assigned to property patientId
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Getter method for property <tt>reportId</tt>.
     * 
     * @return property value of reportId
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * Setter method for property <tt>reportId</tt>.
     * 
     * @param reportId value to be assigned to property reportId
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * Getter method for property <tt>checkEndTime</tt>.
     * 
     * @return property value of checkEndTime
     */
    public String getCheckEndTime() {
        return checkEndTime;
    }

    /**
     * Setter method for property <tt>checkEndTime</tt>.
     * 
     * @param checkEndTime value to be assigned to property checkEndTime
     */
    public void setCheckEndTime(String checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    /**
     * Getter method for property <tt>videoLength</tt>.
     * 
     * @return property value of videoLength
     */
    public Long getVideoLength() {
        return videoLength;
    }

    /**
     * Setter method for property <tt>videoLength</tt>.
     * 
     * @param videoLength value to be assigned to property videoLength
     */
    public void setVideoLength(Long videoLength) {
        this.videoLength = videoLength;
    }

    /**
     * Getter method for property <tt>videoId</tt>.
     * 
     * @return property value of videoId
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * Setter method for property <tt>videoId</tt>.
     * 
     * @param videoId value to be assigned to property videoId
     */
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

}

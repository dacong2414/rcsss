package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteVRBVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private String            patientId;
    private String            reportId;
    private String            checkBeginTime;
    private String            fileStoragePath;
    private String            sex;
    private String            age;
    private String            name;
    private String            checkPosition;

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
     * Getter method for property <tt>checkBeginTime</tt>.
     * 
     * @return property value of checkBeginTime
     */
    public String getCheckBeginTime() {
        return checkBeginTime;
    }

    /**
     * Setter method for property <tt>checkBeginTime</tt>.
     * 
     * @param checkBeginTime value to be assigned to property checkBeginTime
     */
    public void setCheckBeginTime(String checkBeginTime) {
        this.checkBeginTime = checkBeginTime;
    }

    /**
     * Getter method for property <tt>fileStoragePath</tt>.
     * 
     * @return property value of fileStoragePath
     */
    public String getFileStoragePath() {
        return fileStoragePath;
    }

    /**
     * Setter method for property <tt>fileStoragePath</tt>.
     * 
     * @param fileStoragePath value to be assigned to property fileStoragePath
     */
    public void setFileStoragePath(String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }

    /**
     * Getter method for property <tt>sex</tt>.
     * 
     * @return property value of sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * Setter method for property <tt>sex</tt>.
     * 
     * @param sex value to be assigned to property sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Getter method for property <tt>age</tt>.
     * 
     * @return property value of age
     */
    public String getAge() {
        return age;
    }

    /**
     * Setter method for property <tt>age</tt>.
     * 
     * @param age value to be assigned to property age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>checkPosition</tt>.
     * 
     * @return property value of checkPosition
     */
    public String getCheckPosition() {
        return checkPosition;
    }

    /**
     * Setter method for property <tt>checkPosition</tt>.
     * 
     * @param checkPosition value to be assigned to property checkPosition
     */
    public void setCheckPosition(String checkPosition) {
        this.checkPosition = checkPosition;
    }

}

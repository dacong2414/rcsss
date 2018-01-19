package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteQLVVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private String            sysInfo;
    private String            updateType;

    /**
     * Getter method for property <tt>sysInfo</tt>.
     * 
     * @return property value of sysInfo
     */
    public String getSysInfo() {
        return sysInfo;
    }

    /**
     * Setter method for property <tt>sysInfo</tt>.
     * 
     * @param sysInfo value to be assigned to property sysInfo
     */
    public void setSysInfo(String sysInfo) {
        this.sysInfo = sysInfo;
    }

    /**
     * Getter method for property <tt>updateType</tt>.
     * 
     * @return property value of updateType
     */
    public String getUpdateType() {
        return updateType;
    }

    /**
     * Setter method for property <tt>updateType</tt>.
     * 
     * @param updateType value to be assigned to property updateType
     */
    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

}

package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteDELVRVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private String            reportId;

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

}

package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteGUAVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private String            hospitalName;

    /**
     * Getter method for property <tt>hospitalName</tt>.
     * 
     * @return property value of hospitalName
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * Setter method for property <tt>hospitalName</tt>.
     * 
     * @param hospitalName value to be assigned to property hospitalName
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

}

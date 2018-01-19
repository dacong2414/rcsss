package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteMERVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private Long              uId;
    private Long              recId;

    /**
     * Getter method for property <tt>recId</tt>.
     * 
     * @return property value of recId
     */
    public Long getRecId() {
        return recId;
    }

    /**
     * Setter method for property <tt>recId</tt>.
     * 
     * @param recId value to be assigned to property recId
     */
    public void setRecId(Long recId) {
        this.recId = recId;
    }

    /**
     * Getter method for property <tt>uId</tt>.
     * 
     * @return property value of uId
     */
    public Long getuId() {
        return uId;
    }

    /**
     * Setter method for property <tt>uId</tt>.
     * 
     * @param uId value to be assigned to property uId
     */
    public void setuId(Long uId) {
        this.uId = uId;
    }

}

package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteBMVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private Long              uId;
    private Long              recvUId;
    private String            billNum;

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

    /**
     * Getter method for property <tt>recvUId</tt>.
     * 
     * @return property value of recvUId
     */
    public Long getRecvUId() {
        return recvUId;
    }

    /**
     * Setter method for property <tt>recvUId</tt>.
     * 
     * @param recvUId value to be assigned to property recvUId
     */
    public void setRecvUId(Long recvUId) {
        this.recvUId = recvUId;
    }

    /**
     * Getter method for property <tt>billNum</tt>.
     * 
     * @return property value of billNum
     */
    public String getBillNum() {
        return billNum;
    }

    /**
     * Setter method for property <tt>billNum</tt>.
     * 
     * @param billNum value to be assigned to property billNum
     */
    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

}

package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteMACVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private String            macaddress;
    private String            loginName;
    private String            zipname;

    /**
     * Getter method for property <tt>macaddress</tt>.
     * 
     * @return property value of macaddress
     */
    public String getMacaddress() {
        return macaddress;
    }

    /**
     * Setter method for property <tt>macaddress</tt>.
     * 
     * @param macaddress value to be assigned to property macaddress
     */
    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    /**
     * Getter method for property <tt>loginName</tt>.
     * 
     * @return property value of loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Setter method for property <tt>loginName</tt>.
     * 
     * @param loginName value to be assigned to property loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * Getter method for property <tt>zipname</tt>.
     * 
     * @return property value of zipname
     */
    public String getZipname() {
        return zipname;
    }

    /**
     * Setter method for property <tt>zipname</tt>.
     * 
     * @param zipname value to be assigned to property zipname
     */
    public void setZipname(String zipname) {
        this.zipname = zipname;
    }

}

package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteCCVo implements Serializable {

    /** */
    private static final long   serialVersionUID = -3361448845958602532L;
    private String              macAdress;
    private Map<String, String> showConfig;

    /**
     * Getter method for property <tt>macAdress</tt>.
     * 
     * @return property value of macAdress
     */
    public String getMacAdress() {
        return macAdress;
    }

    /**
     * Setter method for property <tt>macAdress</tt>.
     * 
     * @param macAdress value to be assigned to property macAdress
     */
    public void setMacAdress(String macAdress) {
        this.macAdress = macAdress;
    }

    /**
     * Getter method for property <tt>showConfig</tt>.
     * 
     * @return property value of showConfig
     */
    public Map<String, String> getShowConfig() {
        return showConfig;
    }

    /**
     * Setter method for property <tt>showConfig</tt>.
     * 
     * @param showConfig value to be assigned to property showConfig
     */
    public void setShowConfig(Map<String, String> showConfig) {
        this.showConfig = showConfig;
    }

}

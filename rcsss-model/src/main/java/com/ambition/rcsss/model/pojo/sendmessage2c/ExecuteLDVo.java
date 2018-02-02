package com.ambition.rcsss.model.pojo.sendmessage2c;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ExecuteLDVo implements Serializable {

    /** */
    private static final long serialVersionUID = -3361448845958602532L;
    private String            loginName;
    private String            password;
    private String            equipmentSysName;
    private String            equipmentId;
    private String            equipmentName;
    private String            versionInfo;

}

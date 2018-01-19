package com.ambition.rcsss.model.pojo.sendmessage2c.consult;

import java.io.Serializable;

import lombok.Data;

/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.model.pojo, v 0.1 2017/12/6 15:15 hhu Exp $$
 */
@Data
public class UrlDescVo implements Serializable {
    private static final long serialVersionUID = 3972058627553268117L;

    private String            url;

    private String            desc;
}
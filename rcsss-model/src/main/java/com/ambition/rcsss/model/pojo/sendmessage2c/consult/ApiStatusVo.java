package com.ambition.rcsss.model.pojo.sendmessage2c.consult;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 接口状态实体
 *
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.model.pojo, v 0.1 2017/11/24 14:26 hhu Exp $$
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiStatusVo implements Serializable {

    private String apiName;

    private String status;
}
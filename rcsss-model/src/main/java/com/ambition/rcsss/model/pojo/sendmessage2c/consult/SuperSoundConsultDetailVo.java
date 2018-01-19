package com.ambition.rcsss.model.pojo.sendmessage2c.consult;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计超声会诊详情视图对象
 * Created by wxh on 2017/9/14.
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuperSoundConsultDetailVo implements Serializable{
    /** */
    private static final long serialVersionUID = -8622244487488107056L;
    /**
     * 操作人名称
     */
    private String userName;
    /**
     * 邀请人名称
     */
    private String inviterName;
    /**
     * 操作类型::1.加入会话 2.离开会话 3.异常中断 4.断线重连
     */
    private Long operationType;
    /**
     * 操作时间
     */
    private Date  gmtCreate;
}

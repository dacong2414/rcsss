package com.ambition.rcsss.model.pojo.sendmessage2c.consult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * 客户端心跳信息
 *
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.model.pojo, v 0.1 2017/11/24 14:19 hhu Exp $$
 */
@Data
@ToString
@ApiModel
public class HeartBeatVo implements Serializable {
    /**
     * 接口状态集合
     */
    @ApiModelProperty(value = "接口状态集合")
    List<ApiStatusVo> apis;

    /**
     * 请求mac
     */
    @ApiModelProperty(value = "客户端Mac地址")
    private String    clientMac;

    /**
     * 请求时间
     */
    @ApiModelProperty(value = "客户端请求时间")
    private Long      clientTime;

    /**
     * 服务响应时间
     */
    @ApiModelProperty(value = "服务端响应时间")
    private Long      serverTime;
}
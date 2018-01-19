/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.service.MonitorClientMappingService;

/**
 * 监控端监控的诊室
 *
 * @author ambition
 * @version $Id: MonitorClientMappingController.java, v 0.1 2017年10月30日 下午4:48:59 ambition Exp $
 */
@RestController
@RequestMapping("/monitor")
@Api(tags = "MonitorClientMappingController", description = "监控端监控的诊室")
@Slf4j
public class MonitorClientMappingController {
    @Autowired
    private MonitorClientMappingService monitorClientMappingService;

    @RequestMapping(value = "/method=chooseClient", method = { RequestMethod.POST })
    @ApiOperation(value = "监控端选择监控诊室")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "monitorUid", value = "监控端uId", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientUid", value = "诊室端uId", required = true, paramType = "query", dataType = "int") })
    @NeedResubmitCheck
    public ResultInfo<String> chooseClient(Long monitorUid, Long clientUid) {
        log.debug("==[/user/method=addOrModUserInfo]==>参数：monitorUid={},clientUid={}", monitorUid,
            clientUid);
        Boolean b = monitorClientMappingService.modClient(clientUid, monitorUid);
        return ResultInfo.createSuccessResult("操作成功");

    }
}

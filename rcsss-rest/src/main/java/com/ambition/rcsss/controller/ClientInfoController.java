/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */
package com.ambition.rcsss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambition.rcsss.common.HttpSend2C;
import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.ClientInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.ClientInfoService;

/**
 * 客户端管理（mac地址配置）
 *
 * @author ambition
 * @version $Id: DoctorController.java, v 0.1 2017年7月3日 下午5:27:02 ambition Exp $
 */

@RestController
@RequestMapping("/client")
@Api(tags = "ClientInfoController", description = "客户端")
@Slf4j
public class ClientInfoController {

    @Autowired
    ClientInfoService clientInfoService;

    /**
     * 新增或修改客户端（client_id =0）
     *
     * @param groupInfo
     * @return Long
     */
    @RequestMapping(value = "/method=modOrAddClient", method = RequestMethod.POST)
    @ApiOperation(value = "新增或修改客户端信息-->包括clientInfo和List<ClientProperty>的信息")
    @NeedResubmitCheck
    @HttpSend2C
    public ResultInfo<String> modOrAddClientInfo(@RequestBody ClientInfo clientInfo) {
        log.debug("==[/client/method=modOrAddClient]==>参数：clientInfo={}", clientInfo);
        if (clientInfo == null) {
            throw new ProcessException(CodeEnum.ERROR_5010);
        }
        clientInfo.check();
        clientInfoService.modOrAddClientInfo(clientInfo);
        return ResultInfo.createSuccessResult("新增或者修改成功");
    }

    /**
     * 
     *
     * @param recId
     * @return
     */
    @RequestMapping(value = "/method=deleteClient", method = RequestMethod.POST)
    @ApiOperation(value = "删除客户端信息和客户端属性")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "clientId", value = "客户端Idid", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> deleteClient(Long clientId) {
        log.debug("==[/client/method=deleteClient]==>参数：clientId={}", clientId);
        if (clientId == null) {
            throw new ProcessException(CodeEnum.ERROR_5005);
        }
        Boolean b = clientInfoService.deleteClient(clientId);
        if (b) {
            return ResultInfo.createSuccessResult("删除成功");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5005);
    }

    @RequestMapping(value = "/method=isExistClientName", method = RequestMethod.POST)
    @ApiOperation(value = "clientName是否存在")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "clientName", value = "客户端名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> isExist(String clientName, Long clientId) {
        log.debug("==[/client/method=isExistClientName]==>参数：clientName={},clientId={}",
            clientName, clientId);
        if (clientName == null || clientId == null) {
            throw new ProcessException(CodeEnum.ERROR_5013);
        }
        Boolean b = clientInfoService.isExistClientName(clientName, clientId);
        if (b) {
            return ResultInfo.createSuccessResult("名称可用");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5001);
    }

    @RequestMapping(value = "/method=isExistMac", method = RequestMethod.POST)
    @ApiOperation(value = "macdress是否存在")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "macAddress", value = "mac地址", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> isExistMacAddress(String macAddress, Long clientId) {
        log.debug("==[/client/method=isExistMac]==>参数：macAddress={},clientId={}", macAddress,
            clientId);
        if (macAddress == null || clientId == null) {
            throw new ProcessException(CodeEnum.ERROR_5014);
        }
        Boolean b = clientInfoService.isExistMacAddress(macAddress, clientId);
        if (b) {
            return ResultInfo.createSuccessResult("MAC地址可用");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5007);
    }

    @RequestMapping(value = "/method=getClientInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取ClientInfo用于回显")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<ClientInfo> getClient(Long clientId) {
        log.debug("==[/client/method=isExistMac]==>参数：clientId={}", clientId);
        if (clientId == null) {
            throw new ProcessException(CodeEnum.ERROR_5025);
        }
        ClientInfo clientInfoList = clientInfoService.getClientInfo(clientId);
        return ResultInfo.createSuccessResult(clientInfoList);
    }
}

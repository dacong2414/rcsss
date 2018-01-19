/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.UserDefined;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.UserInfoService;

/**
 * 用户自定义属性
 * @author ambition
 * @version $Id: UserinfoController.java, v 0.1 2017年8月16日 上午10:34:21 ambition Exp $
 */
@RestController
@RequestMapping("/defined")
@Api(tags = "UserDefinedController", description = "用户自定义属性-->是指保存用户自己的操作习惯，在不同的用户登录同一个设备时，展示用户自己定义的属性信息")
@Slf4j
public class UserDefinedController {
    //private static final Logger LOG = LoggerFactory.getLogger(UserDefinedController.class);
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/method=addOrModUserDefined", method = { RequestMethod.POST })
    @ApiOperation(value = "新增或修改用户自定义属性（新增recId=0）")
    @NeedResubmitCheck
    public ResultInfo<String> addOrModUserDefined(@RequestBody UserDefined userDefined) {
        log.debug("==[/defined/method=addOrModUserDefined]==>参数：userDefined={}", userDefined);
        if (userDefined == null) {
            throw new ProcessException(CodeEnum.ERROR_5024);
        }
        userDefined.check();
        userInfoService.addOrModUserDefined(userDefined);
        return ResultInfo.createSuccessResult("新增或修改用户自定义属性成功");
    }

    @RequestMapping(value = "/method=getUserDefineds", method = { RequestMethod.POST })
    @ApiOperation(value = "获取用户所有自定义属性信息")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "uId", value = "用户id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<List<UserDefined>> getUserDefineds(Long uId) {
        log.debug("==[/defined/method=getUserDefined]==>参数：uId={}", uId);
        List<UserDefined> userDefinedList = userInfoService.getUserDefinedListByuId(uId);
        return ResultInfo.createSuccessResult(userDefinedList);
    }

    @RequestMapping(value = "/method=getUserDefined", method = { RequestMethod.POST })
    @ApiOperation(value = "获取用户指定自定义属性信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "uId", value = "用户id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "propertyId", value = "属性Id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<UserDefined> getUserDefined(Long uId, Long propertyId) {
        log.debug("==[/defined/method=getUserDefined]==>参数：uId={},propertyId={}", uId, propertyId);
        UserDefined userDefined = userInfoService.getUserDefinedListByuIdAndPropertyId(uId,
            propertyId);
        return ResultInfo.createSuccessResult(userDefined);
    }

    @RequestMapping(value = "/method=deleteUserDefined", method = { RequestMethod.POST })
    @ApiOperation(value = "删除用户自定义属性信息")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "recId", value = "主键id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> deleteUserDefined(Long recId) {
        log.debug("==[/defined/method=deleteUserDefined]==>参数：recId={}", recId);
        Boolean b = userInfoService.deleteUserDefined(recId);
        if (b) {
            return ResultInfo.createSuccessResult("删除用户自定义属性信息成功");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5005);
    }

}

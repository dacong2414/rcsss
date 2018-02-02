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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambition.rcsss.common.HttpSend2C;
import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.LoginService;
import com.ambition.rcsss.service.UserInfoService;
import com.google.common.collect.Range;

/**
 *
 * @author ambition
 * @version $Id: UserinfoController.java, v 0.1 2017年8月16日 上午10:34:21 ambition Exp $
 */
@RestController
@RequestMapping("/user")
@Api(tags = "UserInfoController", description = "用户基本信息控制类")
@Slf4j
public class UserInfoController {
    @Autowired
    private LoginService    loginService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/method=addOrModUserInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "新增或修改用户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", value = "用户名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "uId", value = "用户ID新增传0", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "description", value = "用户描述", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "loginPwd", value = "密码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "roleId", value = "客户端类型", required = false, paramType = "query", dataType = "int[]"),
            @ApiImplicitParam(name = "groupId", value = "用户分组名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "userType", value = "普通用户，还是管理员", required = true, paramType = "query", dataType = "int") })
    @NeedResubmitCheck
    @HttpSend2C
    public ResultInfo<String> addOrModUserInfo(Long uId, String title, String description,
                                               String loginName, String loginPwd, Long[] roleId,
                                               Long groupId, Long userType) {
        log.debug(
            "==[/user/method=addOrModUserInfo]==>参数：uId={},title={},description={},loginName={},loginPwd={},roleId={},groupId={},fieldId={},",
            title, uId, description, loginName, loginPwd, roleId, groupId);
        if (uId == null) {
            throw new ProcessException(CodeEnum.ERROR_5037);
        }
        if (title == null || !Range.openClosed(0, 20).contains(title.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5038);
        }
        if (description == null || !Range.openClosed(0, 200).contains(description.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5039);
        }
        if (loginName == null || !Range.openClosed(0, 20).contains(loginName.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5040);
        }
        if (uId.equals(0L)
            && (loginPwd == null || !Range.openClosed(5, 20).contains(loginPwd.trim().length()))) {//验证新增
            throw new ProcessException(CodeEnum.ERROR_5041);
        }
        if (roleId == null) {
            throw new ProcessException(CodeEnum.ERROR_5042);
        }
        if (groupId == null) {
            throw new ProcessException(CodeEnum.ERROR_5043);
        }
        userInfoService.addOrModUserInfo(uId, title, description, loginName, loginPwd, roleId,
            groupId, userType);
        return ResultInfo.createSuccessResult("新增或者修改成功");

    }

    @RequestMapping(value = "/method=delUserInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "uId", value = "用户ID", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> delUserInfo(Long uId) {
        log.debug("==[/user/method=delUserInfo]==>参数：uId={}", uId);
        userInfoService.delUserInfo(uId);
        return ResultInfo.createSuccessResult("删除用户成功");

    }

    @RequestMapping(value = "/method=existBoolean", method = { RequestMethod.POST })
    @ApiOperation(value = "登录名是否存在")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "uId", value = "用户id新增时传0", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> existBoolean(String loginName, Long uId) {
        log.debug("==[/user/method=existBoolean]==>参数：loginName={}，uId={}", loginName, uId);
        Boolean b = userInfoService.existBoolean(loginName, uId);
        if (b) {
            return ResultInfo.createSuccessResult("登录名称可用");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5001);
    }

    @RequestMapping(value = "/method=getLogonInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "获取单个用户信息 传uId")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "uId", value = "用户id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> getLogonInfo(Long uId) {
        log.debug("==[/user/method=getLogonInfo]==>参数：uId={}", uId);
        String string = loginService.getLogonInfo(uId);
        return ResultInfo.createSuccessResult(string);
    }

    @RequestMapping(value = "/method=getCurrentLogonInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "获取当前登录人的信息")
    public ResultInfo<LogonInfo> getCurrentLogonInfo() {
        LogonInfo logonInfo = loginService.getCurrentLogonInfos();
        return ResultInfo.createSuccessResult(logonInfo);
    }

    @RequestMapping(value = "/method=getAllUsers", method = { RequestMethod.POST })
    @ApiOperation(value = "获取所有用户信息-->做分组可见用")
    public ResultInfo<List<Object[]>> getAllUsers() {
        List<Object[]> listDB = loginService.getAllUsers();
        return ResultInfo.createSuccessResult(listDB);
    }

    @RequestMapping(value = "/method=modLogonInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "修改获取单个用户信息 传uId")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "uId", value = "用户id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "title", value = "用户名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "简介", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "loginPwd", value = "密码", required = true, paramType = "query", dataType = "string") })
    public ResultInfo<String> modLogonInfo(Long uId, String title, String description,
                                           String loginName, String loginPwd) {
        log.debug(
            "==[/user/method=modLogonInfo]==>参数：uId={},title={},description={},loginName={},loginPwd={}",
            uId, title, description, loginName, loginPwd);
        if (uId == null) {
            throw new ProcessException(CodeEnum.ERROR_5037);
        }
        if (title == null || !Range.openClosed(0, 20).contains(title.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5038);
        }
        if (description == null || !Range.openClosed(0, 200).contains(description.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5039);
        }
        if (loginName == null || !Range.openClosed(0, 20).contains(loginName.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5040);
        }
        if (uId.equals(0L)
            && (loginPwd == null || !Range.openClosed(5, 20).contains(loginPwd.trim().length()))) {
            throw new ProcessException(CodeEnum.ERROR_5041);
        }
        Boolean b = loginService.modLogonInfo(uId, title, description, loginName, loginPwd);
        if (b) {
            return ResultInfo.createSuccessResult("修改成功");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5004);
    }
}

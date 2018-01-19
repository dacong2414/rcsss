/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
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

import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.MeetingInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.MeetingService;

/**
 * 会议controller
 *
 * @author ambition
 * @version $Id: DoctorController.java, v 0.1 2017年7月3日 下午5:27:02 ambition Exp $
 */

@RestController
@RequestMapping("/meeting")
@Api(tags = "MeetingController", description = "会议基本信息-->角色为meetingClient的用户可以新增会议信息，对会议的删除只有管理员和自己可以")
@Slf4j
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    /**
     * 新增或修改会议（recId=0）
     *
     * @param groupInfo
     * @return Long
     */
    @RequestMapping(value = "/method=modOrAddMeeting", method = RequestMethod.POST)
    @ApiOperation(value = "新增或修改会议信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "createUserId", value = "创建者用户id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "recId", value = "会议ID新增传0", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "meetingName", value = "会议名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "meetingPassWord", value = "会议密码", required = true, paramType = "query", dataType = "string") })
    @NeedResubmitCheck
    public ResultInfo<String> modOrAddmeeting(MeetingInfo meetingInfo) {
        log.debug("==[/meeting/method=modOrAddMeeting]==>参数：meetingInfo={}", meetingInfo);
        if (meetingInfo == null) {
            throw new ProcessException(CodeEnum.ERROR_5021);
        }
        meetingInfo.check();
        Boolean b = meetingService.modOrAddMeeting(meetingInfo);
        if (b) {
            return ResultInfo.createSuccessResult("新增或修改会议信息成功");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5006);
    }

    /**
     * 删除会议
     *
     * @param recId
     * @return Long
     */

    @RequestMapping(value = "/method=deleteMeeting", method = RequestMethod.POST)
    @ApiOperation(value = "删除会议信息")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "recId", value = "会议id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> deleteMeeting(Long recId) {
        log.debug("==[/meeting/method=deleteMeeting]==>参数：recId={}", recId);
        Boolean b = meetingService.deleteMeeting(recId);
        if (b) {
            return ResultInfo.createSuccessResult("删除成功");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5005);
    }

    /**
     * 获取所有meetingClient的用户
     *
     * @param recId
     * @return Long
     */

    @RequestMapping(value = "/method=getMeetingClient", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有meetingClient的用户")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "roleName", value = "会议客户端名称", required = true, paramType = "query", dataType = "string") })
    public ResultInfo<List<Object[]>> getMeetingClient(String roleName) {
        log.debug("==[/meeting/method=getMeetingClient]==>参数：roleName={}", roleName);
        List<Object[]> list = meetingService.getMeetingClient(roleName);
        log.debug("==[/meeting/method=getMeetingClient]==>获取结果：list={}", list);
        return ResultInfo.createSuccessResult(list);
    }

    /**
     * meetingName是否存在
     *
     * @param recId
     * @return Long
     */

    @RequestMapping(value = "/method=isExist", method = RequestMethod.POST)
    @ApiOperation(value = "meetingName是否存在")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "meetingName", value = "会议名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "recId", value = "会议id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> isExist(String meetingName, Long recId) {
        log.debug("==[/meeting/method=isExist]==>参数：meetingName={}，recId={}", meetingName, recId);
        Boolean b = meetingService.isExist(meetingName, recId);
        if (b) {
            return ResultInfo.createSuccessResult("名称可用");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5001);
    }

    /**
     * 获取meetingInfo用于回显
     *
     * @param recId
     * @return Long
     */

    @RequestMapping(value = "/method=getMeetingInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取meetingInfo")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "recId", value = "会议id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<MeetingInfo> getMeetingInfo(Long recId) {
        log.debug("==[/meeting/method=isExist]==>参数：recId={}", recId);
        MeetingInfo meetingInfo = meetingService.getMeetingInfo(recId);

        return ResultInfo.createSuccessResult(meetingInfo);
    }

}

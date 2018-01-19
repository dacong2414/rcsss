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

import com.ambition.rcsss.common.HttpSend2C;
import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.GroupInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.GroupInfoService;

/**
 * 分组信息
 * @author ambition
 * @version $Id: UserinfoController.java, v 0.1 2017年8月16日 上午10:34:21 ambition Exp $
 */
@RestController
@RequestMapping("/group")
@Api(tags = "GroupInfoController", description = "用户分组信息控制类")
@Slf4j
public class GroupInfoController {
    @Autowired
    private GroupInfoService groupInfoService;

    @RequestMapping(value = "/method=addOrModGroupInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "新增或修改用户分组信息")
    @NeedResubmitCheck
    @HttpSend2C
    public ResultInfo<String> addOrModGroupInfo(@RequestBody GroupInfo groupInfo) {
        log.debug("==[/group/method=addOrModGroupInfo]==>参数：grooupInfo={}", groupInfo);
        if (groupInfo == null) {
            throw new ProcessException(CodeEnum.ERROR_5018);
        }
        groupInfo.check();
        groupInfoService.addOrModGroupInfo(groupInfo);
        return ResultInfo.createSuccessResult("新增或者修改成功");
    }

    @RequestMapping(value = "/method=delGroupInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "删除用户分组")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "groupId", value = "用户分组ID", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> delGroupInfo(Long groupId) {
        log.debug("==[/group/method=delGroupInfo]==>参数：groupId={}", groupId);
        Boolean b = groupInfoService.delGroupInfo(groupId);
        if (b) {
            return ResultInfo.createSuccessResult("删除用户分组成功");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5008);
    }

    @RequestMapping(value = "/method=getGroupInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "获取用户分组")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "groupId", value = "用户分组ID", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<GroupInfo> getGroupInfo(Long groupId) {
        log.debug("==[/group/method=getGroupInfo]==>参数：groupId={}", groupId);
        GroupInfo groupInfo = groupInfoService.getGroupInfo(groupId);
        return ResultInfo.createSuccessResult(groupInfo);
    }

    @RequestMapping(value = "/method=getGroupInfos", method = { RequestMethod.POST })
    @ApiOperation(value = "获取所有用户分组信息 用于做新增修改用户是的下拉框选项   域Id已经通过session获取")
    public ResultInfo<List<GroupInfo>> getGroupInfos() throws Exception {
        log.debug("==[/group/method=getGroupInfos]==>参数：{}");
        return ResultInfo.createSuccessResult(groupInfoService.getGroupInfosByFieldId());
    }

    @RequestMapping(value = "/method=getGroupInfoByFgroupId", method = RequestMethod.POST)
    @ApiOperation(value = "查找父节点")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "fGroupId", value = "父节点id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<List<GroupInfo>> getGroupInfos(Long fGroupId) throws Exception {
        log.debug("==[/group/method=getGroupInfoByFgroupId]==>参数：fGroupId={}", fGroupId);
        List<GroupInfo> groupInfoList = groupInfoService.getGroupInfoByFgroupId(fGroupId);
        log.debug("==[/group/method=getGroupInfoByFgroupId]==>获取结果：groupInfoList={}", groupInfoList);
        return ResultInfo.createSuccessResult(groupInfoList);
    }

    @RequestMapping(value = "/method=getGroupInfoBygroupId", method = RequestMethod.POST)
    @ApiOperation(value = "查找子节点")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "groupId", value = "节点id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<List<GroupInfo>> getGroupInfoList(Long groupId) throws Exception {
        log.debug("==[/group/method=getGroupInfoBygroupId]==>参数：groupId={}", groupId);
        List<GroupInfo> groupInfoList = groupInfoService.getGroupInfosBygroupId(groupId);
        log.debug("==[/group/method=getGroupInfoBygroupId]==>获取结果：groupInfoList={}", groupInfoList);
        return ResultInfo.createSuccessResult(groupInfoList);
    }
}

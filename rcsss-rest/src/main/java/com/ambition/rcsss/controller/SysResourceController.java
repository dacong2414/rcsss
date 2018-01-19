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

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.SysResources;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.SysResourcesService;

/**
 *  资源管理控制类
 * Created by wxh on 2017/9/13.
 */
@RestController
@RequestMapping("/resource")
@Api(tags = "SysResourceController")
@Slf4j
public class SysResourceController {
    @Resource
    private SysResourcesService sysResourcesService;

    @PostMapping("/insert/sysResource")
    @ApiOperation(value = "新增资源", notes = "新增资源")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "sysResources", value = "资源信息对象,必填", required = true, paramType = "body", dataType = "SysResources") })
    @NeedResubmitCheck
    public ResultInfo<String> insertSysResource(@RequestBody SysResources sysResources) {
        log.debug("==[/resource/insert/sysResource]==>参数：sysResources={}", sysResources);
        // 请求参数非空校验
        if (sysResources == null) {
            throw new ProcessException(CodeEnum.ERROR_40013);
        }
        // 各个字段数据校验
        sysResources.checkSysResource();
        sysResourcesService.insertSysResource(sysResources);
        log.debug("==[/resource/insert/sysResource]==新增资源成功");
        return ResultInfo.createSuccessResult("新增资源成功");
    }

    @PostMapping("/update/sysResource")
    @ApiOperation(value = "修改资源", notes = "修改资源")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "sysResources", value = "资源信息对象,必填", required = true, paramType = "body", dataType = "SysResources") })
    @NeedResubmitCheck
    public ResultInfo<String> updateSysResource(@RequestBody SysResources sysResources) {
        log.debug("==[/resource/update/sysResource]==>参数：sysResources={}", sysResources);
        // 请求参数非空校验
        if (sysResources == null) {
            throw new ProcessException(CodeEnum.ERROR_40013);
        }
        // 各个字段数据校验
        sysResources.checkSysResource();
        sysResourcesService.updateSysResource(sysResources);
        log.debug("==[/resource/update/sysResource]==修改资源成功");
        return ResultInfo.createSuccessResult("修改资源成功");
    }

    @PostMapping("/get/sysResource")
    @ApiOperation(value = "获取单条资源信息", notes = "获取单条资源信息")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "resourceId", value = "资源ID,必填", required = true, paramType = "query", dataType = "Long") })
    public ResultInfo<SysResources> getSysResource(Long resourceId) {
        log.debug("==[/resource/get/sysResource]==>参数：resourceId={}", resourceId);
        // 请求参数非空校验
        if (resourceId == null) {
            throw new ProcessException(CodeEnum.ERROR_40025);
        }
        // 获取单条资源信息
        SysResources resourcesDB = sysResourcesService.getSysResourceByResourceId(resourceId);
        if (resourcesDB == null) {
            throw new ProcessException(CodeEnum.ERROR_40025);
        }
        log.debug("<==[/resource/get/sysRole]=={}", resourcesDB);
        return ResultInfo.createSuccessResult(resourcesDB);
    }

    @PostMapping("/delete/sysResource")
    @ApiOperation(value = "删除单条资源信息", notes = "删除单条资源信息")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "resourceId", value = "资源ID,必填", required = true, paramType = "query", dataType = "Long") })
    @NeedResubmitCheck
    public ResultInfo<String> deleteSysResource(Long resourceId) {
        log.debug("==[/resource/delete/sysResource]==>参数：resourceId={}", resourceId);
        // 请求参数非空校验
        if (resourceId == null) {
            throw new ProcessException(CodeEnum.ERROR_40026);
        }
        // 删除单条资源信息
        sysResourcesService.deleteSysResource(resourceId);
        log.debug("==[/resource/delete/sysResource]==删除资源信息成功");
        return ResultInfo.createSuccessResult("删除资源信息成功");
    }

    @PostMapping("/check/resourceName")
    @ApiOperation(value = "校验资源名称是否存在", notes = "校验资源名称是否存在")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "resourceName", value = "资源名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "resourceId", value = "资源ID", required = true, paramType = "query", dataType = "Long") })
    public ResultInfo<String> checkResourceName(String resourceName, Long resourceId) {
        log.debug("==[/resource/check/resourceName]==>参数：resourceName={},resourceId={}",
            resourceName, resourceId);
        // 请求参数非空校验
        if (resourceName == null || resourceId == null) {
            throw new ProcessException(CodeEnum.ERROR_40016);
        }
        // 校验资源名称
        boolean flag = sysResourcesService.checkResourceName(resourceName, resourceId);
        if (!flag) {
            throw new ProcessException(CodeEnum.ERROR_40029);
        }
        log.debug("==[/resource/check/resourceName]==资源名称校验已通过");
        return ResultInfo.createSuccessResult("资源名称可以使用");
    }

    @PostMapping("/get/allResources")
    @ApiOperation(value = "获取所有需要权限验证的url资源")
    public ResultInfo<List<SysResources>> getAllResources() {
        List<SysResources> sysResourcesListDB = sysResourcesService.getAllUrlResources();
        log.debug("==[/get/allResources]==获取所有需要权限验证的url资源" + sysResourcesListDB);
        return ResultInfo.createSuccessResult(sysResourcesListDB);
    }
}

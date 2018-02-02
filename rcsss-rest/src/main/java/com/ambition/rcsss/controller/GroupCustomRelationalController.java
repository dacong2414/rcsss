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

import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.GroupCustomRelational;
import com.ambition.rcsss.service.GroupInfoService;

/**
 * 组的自定义关系表   7
 *
 * @author ambition
 * @version $Id: GroupCustomRelationalController.java, v 0.1 2017年11月13日 上午10:54:13 ambition Exp $
 */
@RestController
@RequestMapping("/relational")
@Api(tags = "GroupCustomRelationalController", description = "组的自定义关系表")
@Slf4j
public class GroupCustomRelationalController {
    @Autowired
    private GroupInfoService groupInfoService;

    @RequestMapping(value = "/method=modOrAddGroupCustomRelational", method = { RequestMethod.POST })
    @ApiOperation(value = "新增或修改组自定义关系")
    public ResultInfo<String> modOrAddGroupCustomRelational(@RequestBody List<GroupCustomRelational> listCustomRelationals) {
        log.debug(
            "==[/relational/method=modOrAddGroupCustomRelational]==>参数：listCustomRelationals={}",
            listCustomRelationals);
        groupInfoService.modOrAddGroupCustomRelational(listCustomRelationals);
        return ResultInfo.createSuccessResult("新增或修改组自定义关系成功");

    }

    @RequestMapping(value = "/method=deleteGroupCustomRelational", method = { RequestMethod.POST })
    @ApiOperation(value = "删除自定义关系")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "leftId", value = "自定义左边对应id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "leftType", value = "自定义左边对应类型", required = true, paramType = "query", dataType = "String") })
    public ResultInfo<String> deleteGroupCustomRelational(Long leftId, String leftType) {
        log.debug("==[/relational/method=deleteGroupCustomRelational]==>参数：leftId={}，leftType={}",
            leftId, leftType);
        groupInfoService.deleteGroupCustomRelational(leftId, leftType);
        return ResultInfo.createSuccessResult("新增或修改组自定义关系成功");

    }

    @RequestMapping(value = "/method=getGroupCustomRelationals", method = { RequestMethod.POST })
    @ApiOperation(value = "获取自定义关系")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "leftId", value = "自定义左边对应id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "leftType", value = "自定义左边对应类型", required = true, paramType = "query", dataType = "String") })
    public ResultInfo<List<GroupCustomRelational>> getGroupCustomRelationals(Long leftId,
                                                                             String leftType) {
        log.debug("==[/relational/method=getGroupCustomRelationals]==>参数：leftId={}，leftType={}",
            leftId, leftType);
        List<GroupCustomRelational> listDB = groupInfoService.getGroupCustomRelationals(leftId,
            leftType);
        return ResultInfo.createSuccessResult(listDB);

    }

    @RequestMapping(value = "/method=setFlag", method = { RequestMethod.POST })
    @ApiOperation(value = "存flag 在flag/flag.xml里面")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "flag", value = "全局配置的标志（例如：allPersonSee，allPersonNoSee，sameGroupRecursion，sameGroupNoRecursion，）", required = false, paramType = "query", dataType = "string") })
    public ResultInfo<String> setFlag(String flag) {
        groupInfoService.setFlag(flag);
        return ResultInfo.createSuccessResult("flag已经存入");
    }

    @RequestMapping(value = "/method=getFlag", method = { RequestMethod.POST })
    @ApiOperation(value = "取flag")
    public ResultInfo<String> getFlag() {
        String flag = groupInfoService.getFlag();
        return ResultInfo.createSuccessResult(flag);
    }
}

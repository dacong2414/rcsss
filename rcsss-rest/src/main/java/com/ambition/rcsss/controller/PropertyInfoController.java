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
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.PropertyInfo;
import com.ambition.rcsss.model.entity.RegularLinkage;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.PropertyInfoService;

/**
 * 基本属性 、采集卡、的控制（联动规则配置）
 * @author ambition
 * @version $Id: UserinfoController.java, v 0.1 2017年8月16日 上午10:34:21 ambition Exp $
 */
@RestController
@RequestMapping("/property")
@Api(tags = "PropertyInfoController", description = "属性信息控制类（属性的新增修改、包括属性扩展、联动-->例如我们改变采集卡名称改变对应的'视频输入源类型'和采集卡类型相应的改变）")
@Slf4j
public class PropertyInfoController {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyInfoController.class);
    @Autowired
    private PropertyInfoService propertyInfoService;

    @RequestMapping(value = "/method=addOrModPropertyInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "新增或修改属性信息")
    @NeedResubmitCheck
    public ResultInfo<String> addOrModPropertyInfo(@RequestBody List<PropertyInfo> propertyInfos) {
        log.debug("==[/property/method=addOrModPropertyInfo]==>参数：propertyInfos={}", propertyInfos);
        if (propertyInfos == null) {
            throw new ProcessException(CodeEnum.ERROR_5023);
        }
        propertyInfoService.addOrModPropertyInfo(propertyInfos);
        return ResultInfo.createSuccessResult("新增或修改属性信息成功");

    }

    @RequestMapping(value = "/method=delPropertyInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "删除属性")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "propertyId", value = "属性ID", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> delUserInfo(Long propertyId) {
        log.debug("==[/property/method=delPropertyInfo]==>参数：propertyId={}", propertyId);
        propertyInfoService.delPropertyInfo(propertyId);
        return ResultInfo.createSuccessResult("删除属性成功");

    }

    @RequestMapping(value = "/method=existBoolean", method = { RequestMethod.POST })
    @ApiOperation(value = "属性英文名称是否存在")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "propertyNameEn", value = "属性英文名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "propertyId", value = "属性ID", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> existBoolean(String propertyNameEn, Long propertyId) {
        log.debug("==[/property/method=existBoolean]==>参数：propertyNameEn={},propertyId={}",
            propertyNameEn, propertyId);
        Boolean b = propertyInfoService.existBoolean(propertyNameEn, propertyId);
        if (b) {
            return ResultInfo.createSuccessResult("属性英文名称可用");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5001);
    }

    @RequestMapping(value = "/method=getPropertyInfo", method = { RequestMethod.POST })
    @ApiOperation(value = "获取属性信息用于回显")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "propertyId", value = "属性ID", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<PropertyInfo> getPropertyInfo(Long propertyId) {
        log.debug("==[/property/method=getPropertyInfo]==>参数：propertyId={}", propertyId);
        PropertyInfo propertyInfo = propertyInfoService.getPropertyInfo(propertyId);
        log.debug("==[/property/method=getPropertyInfo]==>返回结果：PropertyInfo={}", propertyInfo);
        return ResultInfo.createSuccessResult(propertyInfo);
    }

    @RequestMapping(value = "/method=getPropertyInfoAll", method = { RequestMethod.POST })
    @ApiOperation(value = "获取所有属性信息用于client_info回显,不包括失效属性")
    public ResultInfo<Map<String, Object>> getPropertyInfoAll() {
        log.debug("==[/property/method=getPropertyInfoAll]==>参数：");
        Map<String, Object> propertyInfoList = propertyInfoService.getPropertyInfoAll();
        log.debug("==[/property/method=getPropertyInfoAll]==>返回结果：propertyInfoList=",
            propertyInfoList);
        return ResultInfo.createSuccessResult(propertyInfoList);
    }

    @RequestMapping(value = "/method=addOrModRegularLinkage", method = { RequestMethod.POST })
    @ApiOperation(value = "新增或修改子联动项（传ffunction=0的functionId）和子联动项）")
    @NeedResubmitCheck
    public ResultInfo<String> addOrModRegularLinkage(@RequestBody List<RegularLinkage> regularLinkageList) {
        log.debug("==[/property/method=addOrModRegularLinkage]==>参数：regularLinkageList={},",
            regularLinkageList);
        propertyInfoService.addOrModRegularLinkage(regularLinkageList);
        return ResultInfo.createSuccessResult("新增或修改子联动信息成功");
    }

    @RequestMapping(value = "/method=deleteRegularLinkage", method = { RequestMethod.POST })
    @ApiOperation(value = "删除联动项（传ffunction=0的functionId）")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "functionId", value = "联动id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> deleteRegularLinkage(Long functionId) {
        log.debug("==[/property/method=deleteRegularLinkage]==>参数：functionId={}", functionId);
        propertyInfoService.deleteRegularLinkage(functionId);
        return ResultInfo.createSuccessResult("删除联动信息成功");

    }

    @RequestMapping(value = "/method=getRegularLinkages", method = { RequestMethod.POST })
    @ApiOperation(value = "获取联动项（传ffunction=0的functionId）")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "functionId", value = "联动id", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<List<RegularLinkage>> getRegularLinkages(Long functionId) {
        log.debug("==[/property/method=getRegularLinkages]==>参数：functionId={}", functionId);
        return ResultInfo.createSuccessResult(propertyInfoService.getRegularLinkages(functionId));

    }

    /*@RequestMapping(value = "/method=existTagType", method = { RequestMethod.POST })
    @ApiOperation(value = "标签分类名称是否存在")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "propertyNameEn", value = "属性英文名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "propertyId", value = "属性ID", required = true, paramType = "query", dataType = "int") })
    public ResultInfo<String> existTagType(String tagTppe, Long propertyId) {
        log.debug("==[/property/method=existBoolean]==>参数：propertyNameEn={},propertyId={}",
            tagTppe, propertyId);
        Boolean b = propertyInfoService.existTagType(tagTppe, propertyId);
        if (b) {
            return ResultInfo.createSuccessResult("标签分类名称可用");
        }
        return ResultInfo.createResult(CodeEnum.ERROR_5001);
    }*/
}

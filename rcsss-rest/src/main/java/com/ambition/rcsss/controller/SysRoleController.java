/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.controller;

import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.SysRoles;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.SysRolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理控制类
 * Created by wxh on 2017/9/13.
 */
@RestController
@RequestMapping("/sysRole")
@Api(tags = "SysRolesController")
@Slf4j
public class SysRoleController {

    @Resource
    private SysRolesService sysRolesService;

    @PostMapping("/insert/sysRole")
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "sysRoles", value = "角色信息对象,必填", required = true, paramType = "body", dataType = "SysRoles") })
    @NeedResubmitCheck
    public ResultInfo<String> insertSysRole(@RequestBody SysRoles sysRoles) {
        log.debug("==[/sysRole/insert/sysRole]==>参数：sysRoles={}", sysRoles);
        // 请求参数非空校验
        if (sysRoles == null) {
            throw new ProcessException(CodeEnum.ERROR_40003);
        }
        // 各个字段数据校验
        sysRoles.checkSysRole();
        sysRolesService.insertSysRole(sysRoles);
        log.debug("<==[/sysRole/insert/sysRole]==新增角色成功");
        return ResultInfo.createSuccessResult("新增角色成功");
    }

    @PostMapping("/update/sysRole")
    @ApiOperation(value = "修改角色", notes = "修改角色")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "sysRoles", value = "角色信息对象,必填", required = true, paramType = "body", dataType = "SysRoles") })
    @NeedResubmitCheck
    public ResultInfo<String> updateSysRole(@RequestBody SysRoles sysRoles) {
        log.debug("==[/sysRole/update/sysRole]==>参数：sysRoles={}", sysRoles);
        // 请求参数非空校验
        if (sysRoles == null) {
            throw new ProcessException(CodeEnum.ERROR_40003);
        }
        // 各个字段数据校验
        sysRoles.checkSysRole();
        sysRolesService.updateSysRole(sysRoles);
        log.debug("<==[/sysRole/update/sysRole]==修改角色成功");
        return ResultInfo.createSuccessResult("修改角色成功");
    }

    @PostMapping("/get/sysRole")
    @ApiOperation(value = "获取单条角色信息", notes = "获取单条角色信息")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "roleId", value = "角色ID,必填", required = true, paramType = "query", dataType = "Long") })
    public ResultInfo<SysRoles> getSysRole(Long roleId) {
        log.debug("==[/sysRole/get/sysRole]==>参数：roleId={}", roleId);
        // 请求参数非空校验
        if (roleId == null) {
            throw new ProcessException(CodeEnum.ERROR_40009);
        }
        // 获取单条角色信息
        SysRoles roleDB = sysRolesService.getSysRoleByRoleId(roleId);
        if (roleDB == null) {
            throw new ProcessException(CodeEnum.ERROR_40009);
        }
        log.debug("<==[/sysRole/get/sysRole]=={}", roleDB);
        return ResultInfo.createSuccessResult(roleDB);
    }

    @PostMapping("/delete/sysRole")
    @ApiOperation(value = "删除单条角色信息", notes = "删除单条角色信息")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "roleId", value = "角色ID,必填", required = true, paramType = "query", dataType = "Long") })
    @NeedResubmitCheck
    public ResultInfo<String> deleteSysRole(Long roleId) {
        log.debug("==[/sysRole/delete/sysRole]==>参数：roleId={}", roleId);
        // 请求参数非空校验
        if (roleId == null) {
            throw new ProcessException(CodeEnum.ERROR_40010);
        }
        // 删除单条角色信息
        sysRolesService.deleteSysRole(roleId);
        log.debug("==[/sysRole/delete/sysRole]==删除角色信息成功");
        return ResultInfo.createSuccessResult("删除角色信息成功");
    }

    @PostMapping("/update/ckFlag")
    @ApiOperation(value = "修改角色资源关系的选中状态", notes = "修改角色资源关系的选中状态")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "roleId", value = "角色ID,必填", required = true, paramType = "query", dataType = "Long"),
                                 @ApiImplicitParam(name = "resourceId", value = "资源ID,必填", required = true, paramType = "query", dataType = "Long")})
    public ResultInfo<String> updateCkFlag(Long roleId, Long resourceId) {
        log.debug("==[/sysRole/update/ckFlag]==>参数：roleId={},resourceId={}", roleId,
            resourceId);
        // 请求参数非空校验
        if (roleId == null || resourceId == null ) {
            throw new ProcessException(CodeEnum.ERROR_40012);
        }
        Long flag = sysRolesService.insertSysRoleResource(roleId, resourceId);
        if(IGlobalConstant.SELECTED.equals(flag)){
            return ResultInfo.createSuccessResult("操作成功，角色ID:" + roleId + " 绑定 资源ID:" + resourceId);
        }
        log.debug("==[/sysRole/update/ckFlag]==修改角色资源关系的选中状态成功");
        return ResultInfo.createSuccessResult("操作成功，角色ID:" + roleId + " 解绑 资源ID:" + resourceId);
    }

    @PostMapping("/check/sysRoleName")
    @ApiOperation(value = "校验角色名称是否存在", notes = "校验角色名称是否存在")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, paramType = "query", dataType = "String"),
                                 @ApiImplicitParam(name = "roleId", value = "角色ID",required = true,paramType = "query",dataType = "Long") })
    public ResultInfo<String> checkRoleName(String roleName,Long roleId) {
        log.debug("==[/sysRole/check/sysRoleName]==>参数：roleName={},roleId={}", roleName,roleId);
        // 请求参数非空校验
        if (roleName == null || roleId == null) {
            throw new ProcessException(CodeEnum.ERROR_40001);
        }
        // 校验角色名称
        boolean flag = sysRolesService.checkRoleName(roleName,roleId);
        if (!flag) {
            throw new ProcessException(CodeEnum.ERROR_40028);
        }
        log.debug("==[/sysRole/check/sysRoleName]==角色名称校验已通过");
        return ResultInfo.createSuccessResult("角色名称可以使用");
    }

    @PostMapping("/get/sysRoleList")
    @ApiOperation(value = "获取所有角色", notes = "获取所有角色")
    public ResultInfo<List<SysRoles>> getSysRoleList() {
        return ResultInfo.createSuccessResult(sysRolesService.getSysRoleList());
    }

}

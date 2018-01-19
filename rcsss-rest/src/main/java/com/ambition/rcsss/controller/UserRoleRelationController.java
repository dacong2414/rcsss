package com.ambition.rcsss.controller;

import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.SysRolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户角色关系管理信息控制类
 * Created by wxh on 2017/9/13.
 */
@RestController
@RequestMapping("/userRoleRelation")
@Api(tags = "UserRoleRelationController")
@Slf4j
public class UserRoleRelationController {
    @Resource
    private SysRolesService sysRolesService;

    @PostMapping("/update/ckFlag")
    @ApiOperation(value = "修改用户角色关系的选中状态", notes = "修改用户角色关系的选中状态")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "uId", value = "用户ID,必填", required = true, paramType = "query", dataType = "Long"),
                                 @ApiImplicitParam(name = "roleId", value = "角色ID,必填", required = true, paramType = "query", dataType = "Long") })
    public ResultInfo<String> updateCkFlag(Long uId, Long roleId) {
        log.debug("==[/userRoleRelation/update/ckFlag]==>参数：uId={},roleId={}", uId,
            roleId);
        if (uId == null || roleId == null) {
            throw new ProcessException(CodeEnum.ERROR_40011);
        }
        Long flag = sysRolesService.insertSysUserRole(uId,roleId);
        if(IGlobalConstant.SELECTED.equals(flag)){
            return ResultInfo.createSuccessResult("操作成功，用户ID:" + uId + " 绑定 角色ID:" + roleId);
        }
        log.debug("<==[/userRoleRelation/update/ckFlag]==修改用户角色关系的选中状态成功");
        return ResultInfo.createSuccessResult("操作成功，用户ID:" + uId + " 解绑 角色ID:" + roleId);
    }
}

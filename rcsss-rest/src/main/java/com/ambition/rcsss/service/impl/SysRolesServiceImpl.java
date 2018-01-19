package com.ambition.rcsss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.SysRolesDao;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.SysRoles;
import com.ambition.rcsss.model.entity.SysRolesResources;
import com.ambition.rcsss.model.entity.SysUsersRoles;
import com.ambition.rcsss.service.SysRolesService;

/**
 *  角色服务实现
 * @author wxh
 */
@Service
public class SysRolesServiceImpl extends BaseService implements SysRolesService {
    @Resource
    private SysRolesDao     sysRolesDao;

    @Resource
    private MysqlDaoSupport mysqlDaoSupport;

    @Override
    public void insertSysRole(SysRoles sysRoles) {
        // 插入角色信息
        mysqlDaoSupport.save(sysRoles);
    }

    @Override
    public void updateSysRole(SysRoles sysRoles) {
        // 当前域信息
        SysRoles roleDB = sysRolesDao.getSysRoleByRoleId(sysRoles.getRoleId());
        // 系统资源类型为1，才可以修改
        if (IGlobalConstant.SYS_FLAG_NOT_DEFAULT.equals(roleDB.getSysFlag())) {
            roleDB.setRoleName(sysRoles.getRoleName());
            roleDB.setRoleDesc(sysRoles.getRoleDesc());
            roleDB.setUseFlag(sysRoles.getUseFlag());
            roleDB.setRoleIssys(sysRoles.getRoleIssys());
            roleDB.setSysFlag(sysRoles.getSysFlag());
            // 更新角色信息
            mysqlDaoSupport.update(roleDB);
        }

    }

    @Override
    public SysRoles getSysRoleByRoleId(Long roleId) {
        return sysRolesDao.getSysRoleByRoleId(roleId);
    }

    @Override
    public Long insertSysUserRole(Long uId, Long roleId) {
        SysUsersRoles sysUsersRoles = sysRolesDao.getSysUserRoleByUidAndRoleId(uId, roleId);
        if (sysUsersRoles == null) {
            sysUsersRoles = new SysUsersRoles();
            sysUsersRoles.setUserId(uId);
            sysUsersRoles.setRoleId(roleId);
            // 插入用户角色关系信息
            mysqlDaoSupport.save(sysUsersRoles);
            return IGlobalConstant.SELECTED;
        }
        sysRolesDao.deleteSysUserRole(uId, roleId);
        return IGlobalConstant.UNSELECTED;
    }

    @Override
    public void deleteSysUserRole(Long uId, Long roleId) {
        sysRolesDao.deleteSysUserRole(uId, roleId);
    }

    @Override
    public void deleteSysRole(Long roleId) {
        // 删除角色资源关系关联
        sysRolesDao.deleteSysRoleResourceByRoleId(roleId);
        // 删除用户角色关系关联
        sysRolesDao.deleteSysUserRoleByRoleId(roleId);
        // 删除角色信息
        sysRolesDao.deleteSysRole(roleId);
    }

    @Override
    public Long insertSysRoleResource(Long roleId, Long resourceId) {
        SysRolesResources sysRolesResources = sysRolesDao.getSysRoleResourceByRoleIdAndResourceId(
            roleId, resourceId);
        if (sysRolesResources == null) {
            sysRolesResources = new SysRolesResources();
            sysRolesResources.setRoleId(roleId);
            sysRolesResources.setResourceId(resourceId);
            // 插入角色资源关系信息
            mysqlDaoSupport.save(sysRolesResources);
            return IGlobalConstant.SELECTED;
        }
        sysRolesDao.deleteSysRoleResource(roleId, resourceId);
        return IGlobalConstant.UNSELECTED;

    }

    @Override
    public void deleteSysRoleResource(Long roleId, Long resourceId) {
        sysRolesDao.deleteSysRoleResource(roleId, resourceId);
    }

    @Override
    public boolean checkRoleName(String roleName, Long roleId) {
        SysRoles roleDB = sysRolesDao.getSysRoleByRoleName(roleName);
        if (roleDB != null && !roleDB.getRoleId().equals(roleId)) {
            // 角色名称重名，不可以使用
            return false;
        }
        // 角色名称没有重名可以使用
        return true;
    }

    /** 
     * @return
     * @see com.ambition.rcsss.service.SysRolesService#getSysRoleList()
     */
    @Override
    public List<SysRoles> getSysRoleList() {
        return sysRolesDao.getSysRoleList(SysRoles.USE_FLAG_YES);
    }
}

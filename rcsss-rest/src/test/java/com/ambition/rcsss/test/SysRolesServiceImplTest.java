package com.ambition.rcsss.test;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.SysResources;
import com.ambition.rcsss.model.entity.SysRoles;
import com.ambition.rcsss.model.entity.SysRolesResources;
import com.ambition.rcsss.model.entity.SysUsersRoles;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.service.SysRolesService;

/**
 *  角色服务实现的单元测试
 * Created by wxh on 2017/9/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
@Transactional
@Slf4j
public class SysRolesServiceImplTest {
    @Resource
    private SysRolesService sysRolesService;
    @Resource
    private MysqlDaoSupport mysqlDaoSupport;
    @Resource
    private HttpSession     session;

    /**
     *  初始化登录信息、域信息存入session中
     */
    private void initToSession() {
        log.debug(">>>    初始化数据      <<<");
        // 域信息
        // 用户信息
        UserInfo userInfo = InitData.getUserInfo();
        mysqlDaoSupport.save(userInfo);
        // 登录信息
        LogonInfo logonInfo = InitData.getLogonInfo("sysRolesService");
        logonInfo.setuId(userInfo.getuId());
        logonInfo.setUserInfo(userInfo);
        mysqlDaoSupport.save(logonInfo);
        session.setAttribute(IGlobalConstant.CURRENT_USER, logonInfo);
        log.debug(">>>    数据初始化完成      <<<");
    }

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        initToSession();
    }

    /**
     * 测试新增角色信息
     * 测试目标方法：insertSysRole(SysRoles sysRoles)
     */
    @Test
    public void testInsertSysRole() {
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        // 插入角色信息
        sysRolesService.insertSysRole(sysRole);
        // 判断数据是否插入成功
        Assert.assertNotNull(sysRole.getRoleId());
    }

    /**
     * 测试修改角色信息
     * 测试目标方法：updateSysRole(SysRoles sysRoles)
     */
    @Test
    public void testUpdateSysRole() {
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        // 插入角色信息
        mysqlDaoSupport.save(sysRole);
        sysRole.setRoleDesc("院内客户端描述update");
        // 更新角色信息
        sysRolesService.updateSysRole(sysRole);
        // 判断数据是否修改成功
        SysRoles sysRoleEntity = mysqlDaoSupport.get(SysRoles.class, sysRole.getRoleId());
        Assert.assertEquals("院内客户端描述update", sysRoleEntity.getRoleDesc());
    }

    /**
     * 测试根据角色ID获取单条角色信息
     * 测试目标方法：getSysRoleByRoleId(Long roleId)
     */
    @Test
    public void testGetSysRoleByRoleId() {
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        // 插入角色信息
        mysqlDaoSupport.save(sysRole);
        // 获取单条角色信息
        SysRoles sysRoleEntity = sysRolesService.getSysRoleByRoleId(sysRole.getRoleId());
        // 判断数据是否插入成功
        Assert.assertEquals("院内客户端", sysRoleEntity.getRoleName());
    }

    /**
     * 测试新增用户角色关系
     * 测试目标方法：insertSysUserRole(Long uId, Long roleId)
     */
    @Test
    public void testInsertSysUserRole() {
        // 当前域信息
        // 初始化用户信息
        UserInfo userInfo = InitData.getUserInfo();
        mysqlDaoSupport.save(userInfo);
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        mysqlDaoSupport.save(sysRole);
        // 插入用户角色关系信息
        sysRolesService.insertSysUserRole(userInfo.getuId(), sysRole.getRoleId());
        // 判断数据是否掺入成功
        Long count = mysqlDaoSupport.sqlExecuteCount(
            "sys_users_roles WHERE user_id=? AND role_id=?", null, new Object[] {
                    userInfo.getuId(), sysRole.getRoleId() }, null);
        Assert.assertTrue(count == 1);
    }

    /**
     * 测试删除用户角色关系
     * 测试目标方法：deleteSysUserRole(Long uId, Long roleId)
     */
    @Test
    public void testDeleteSysUserRole() {
        // 当前域信息
        // 初始化用户信息
        UserInfo userInfo = InitData.getUserInfo();
        mysqlDaoSupport.save(userInfo);
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        mysqlDaoSupport.save(sysRole);
        // 初始化用户角色关系信息
        SysUsersRoles sysUserRole = InitData.getSysUserRole(userInfo.getuId(), sysRole.getRoleId());
        mysqlDaoSupport.save(sysUserRole);
        // 删除用户角色关系信息
        sysRolesService.deleteSysUserRole(userInfo.getuId(), sysRole.getRoleId());
        // 判断数据是否删除成功
        Long count = mysqlDaoSupport.sqlExecuteCount(
            "sys_users_roles WHERE user_id=? AND role_id=?", null, new Object[] {
                    userInfo.getuId(), sysRole.getRoleId() }, null);
        Assert.assertTrue(count == 0);
    }

    /**
     * 测试删除角色信息
     * 测试目标方法：deleteSysRole(Long roleId)
     */
    @Test
    public void testDeleteSysRole() {
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        mysqlDaoSupport.save(sysRole);
        // 删除角色信息
        sysRolesService.deleteSysRole(sysRole.getRoleId());
        // 判断数据是否删除成功
        Long count = mysqlDaoSupport.sqlExecuteCount("sys_roles WHERE role_id=?", null,
            new Object[] { sysRole.getRoleId() }, null);
        Assert.assertTrue(count == 0);
    }

    /**
     * 测试新增角色资源关系
     * 测试目标方法：insertSysRoleResource(Long roleId, Long resourceId)
     */
    @Test
    public void testInsertSysRoleResource() {
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        mysqlDaoSupport.save(sysRole);
        // 初始化资源信息
        SysResources sysResource = InitData.getSysResource("刷新缓存");
        mysqlDaoSupport.save(sysResource);
        // 插入角色资源关系信息
        sysRolesService.insertSysRoleResource(sysRole.getRoleId(), sysResource.getResourceId());
        // 判断数据是否插入成功
        Long count = mysqlDaoSupport.sqlExecuteCount(
            "sys_roles_resources where role_id=? and resource_id=?", null,
            new Object[] { sysRole.getRoleId(), sysResource.getResourceId() }, null);
        Assert.assertTrue(count == 1);
    }

    /**
     * 测试删除角色资源关系
     * 测试目标方法：deleteSysRoleResource(Long roleId, Long resourceId)
     */
    @Test
    public void testDeleteSysRoleResource() {
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        mysqlDaoSupport.save(sysRole);
        // 初始化资源信息
        SysResources sysResource = InitData.getSysResource("刷新缓存");
        mysqlDaoSupport.save(sysResource);
        // 初始化角色资源关系信息
        SysRolesResources sysRoleResource = InitData.getSysRoleResource(sysRole.getRoleId(),
            sysResource.getResourceId());
        mysqlDaoSupport.save(sysRoleResource);
        // 删除角色资源关系信息
        sysRolesService.deleteSysRoleResource(sysRole.getRoleId(), sysResource.getResourceId());
        // 判断数据是否删除成功
        Long count = mysqlDaoSupport.sqlExecuteCount(
            "sys_roles_resources where role_id=? and resource_id=?", null,
            new Object[] { sysRole.getRoleId(), sysResource.getResourceId() }, null);
        Assert.assertTrue(count == 0);
    }

    /**
     * 测试校验角色名称是否存在
     * 测试目标方法：checkRoleName(String roleName)
     */
    @Test
    public void testCheckRoleName() {
        // 初始化角色信息
        SysRoles sysRole = InitData.getSysRole("院内客户端");
        mysqlDaoSupport.save(sysRole);
        // 判断角色名称是否存在
        String roleName = "院内客户端check";
        boolean flag = sysRolesService.checkRoleName(roleName, sysRole.getRoleId());
        Assert.assertTrue(flag == true);
    }
}

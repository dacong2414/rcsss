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
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.service.SysResourcesService;

/**
 *  资源服务实现的单元测试
 * Created by wxh on 2017/9/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
@Transactional
@Slf4j
public class SysResourcesServiceImplTest {
    @Resource
    private SysResourcesService sysResourcesService;
    @Resource
    private MysqlDaoSupport     mysqlDaoSupport;
    @Resource
    private HttpSession         session;

    /**
     *  初始化登录信息、域信息存入session中
     */
    private void initToSession() {
        log.debug(">>>    初始化数据      <<<");
        // 用户信息
        UserInfo userInfo = InitData.getUserInfo();
        mysqlDaoSupport.save(userInfo);
        // 登录信息
        LogonInfo logonInfo = InitData.getLogonInfo("sysResourcesService");
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
     * 测试新增资源信息
     * 测试目标方法：insertSysResource(SysResources sysResources)
     */
    @Test
    public void testInsertSysResource() {
        // 初始化资源信息
        SysResources sysResource = InitData.getSysResource("刷新缓存");
        // 插入资源信息
        sysResourcesService.insertSysResource(sysResource);
        // 判断数据是否插入成功
        Assert.assertNotNull(sysResource.getResourceId());
    }

    /**
     * 测试修改资源信息
     * 测试目标方法：updateSysResource(SysResources sysResources)
     */
    @Test
    public void testUpdateSysResource() {
        // 初始化资源信息
        SysResources sysResource = InitData.getSysResource("刷新缓存");
        // 插入资源信息
        mysqlDaoSupport.save(sysResource);
        sysResource.setResourceName("刷新缓存update");
        // 更新资源信息
        sysResourcesService.updateSysResource(sysResource);
        // 判断数据是否修改成功
        SysResources sysResourceEntity = mysqlDaoSupport.get(SysResources.class,
            sysResource.getResourceId());
        Assert.assertEquals("刷新缓存update", sysResourceEntity.getResourceName());
    }

    /**
     * 测试根据资源ID获取单条资源信息
     * 测试目标方法：getSysResourceByResourceId(Long resourceId) 
     */
    @Test
    public void testGetSysResourceByResourceId() {
        // 初始化资源信息
        SysResources sysResource = InitData.getSysResource("刷新缓存");
        // 插入资源信息
        mysqlDaoSupport.save(sysResource);
        // 获取单条资源信息
        SysResources sysResourceEntity = sysResourcesService.getSysResourceByResourceId(sysResource
            .getResourceId());
        // 判断数据是否插入成功
        Assert.assertEquals("刷新缓存", sysResourceEntity.getResourceName());
    }

    /**
     * 测试删除资源信息
     * 测试目标方法：deleteSysResource(Long resourceId)
     */
    @Test
    public void testDeleteSysResource() {
        // 初始化资源信息
        SysResources sysResource = InitData.getSysResource("刷新缓存");
        // 插入资源信息
        mysqlDaoSupport.save(sysResource);
        // 删除资源信息
        sysResourcesService.deleteSysResource(sysResource.getResourceId());
        // 判断数据是否删除成功
        Long count = mysqlDaoSupport.sqlExecuteCount("sys_resources where resource_id=? ", null,
            new Object[] { sysResource.getResourceId() }, null);
        Assert.assertTrue(count == 0);
    }

    /**
     * 测试校验资源名称是否存在
     * 测试目标方法：checkResourceName(String resourceName, Long resourceId)
     */
    @Test
    public void testCheckResourceName() {
        // 初始化资源信息
        SysResources sysResource = InitData.getSysResource("刷新缓存");
        // 插入资源信息
        mysqlDaoSupport.save(sysResource);
        // 判断资源名称是否存在
        String resourceName = "刷新缓存check";
        boolean flag = sysResourcesService.checkResourceName(resourceName,
            sysResource.getResourceId());
        Assert.assertTrue(flag == true);
    }
}

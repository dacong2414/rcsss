package com.ambition.rcsss.test;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.service.UserInfoService;

/**
* LoginServiceImpl Tester.
*
* @author <Authors name>
* @since <pre> 26, 2017</pre>
* @version 1.0
*/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
@Transactional
public class UserInfoServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(UserInfoServiceTest.class);

    @Resource
    private UserInfoService     userInfoService;

    LogonInfo                   logonInfo;
    UserInfo                    userInfo;

    @Resource
    MysqlDaoSupport             mysqlDaoSupport;
    @Resource
    private HttpSession         session;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        initToSession();
    }

    /**
     * 初始化往session中存放数据
     */
    private void initToSession() {
        LOG.debug(">>>>初始化数据<<<<");
        userInfo = InitData.getUserInfo();
        mysqlDaoSupport.save(userInfo);
        logonInfo = InitData.getLogonInfo();
        logonInfo.setuId(userInfo.getuId());
        logonInfo.setUserInfo(userInfo);
        mysqlDaoSupport.save(logonInfo);
        session.setAttribute(IGlobalConstant.CURRENT_USER, logonInfo);
    }

    /**
    * Method: getLoginInfoBySession()
    */
    @Test
    public void testAddOrModUserInfo() throws Exception {
        Long[] roleIdAttr = { 1L };
        Boolean firstBoolean = userInfoService.addOrModUserInfo(0L, "admin11", "描述", "loginName",
            "xxx", roleIdAttr, 1L);
        Assert.assertEquals(true, firstBoolean);
        Boolean secondBoolean = userInfoService.addOrModUserInfo(userInfo.getuId(), "admin22",
            "描述修改", "loginName22", "xxx修改", roleIdAttr, 1L);
        Assert.assertEquals(true, secondBoolean);
    }

    @Test
    public void testDelUserInfo() throws Exception {
        userInfoService.delUserInfo(userInfo.getuId());
    }

    @Test
    public void testExistBoolean() throws Exception {
        Boolean firstBoolean = userInfoService.existBoolean("loginName", 30L);
        Assert.assertEquals(false, firstBoolean);
        Boolean secondBoolean = userInfoService.existBoolean("xxx", userInfo.getuId());
        Assert.assertEquals(true, secondBoolean);
    }

}

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
import com.ambition.rcsss.service.LoginService;

/**
* LoginServiceImpl Tester.
*
* @author <Authors name>
* @since <pre> 26, 2017</pre>
* @version 1.0
*/
@RunWith(SpringRunner.class)
@Rollback
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServiceTest.class);

    @Resource
    private LoginService        loginService;

    LogonInfo                   logonInfo;

    @Resource
    MysqlDaoSupport             mysqlDaoSupport;
    @Resource
    private HttpSession         session;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        initToSession();
    }

    private void initToSession() {
        LOG.debug(">>>>初始化数据<<<<");
        UserInfo userInfo = InitData.getUserInfo();
        mysqlDaoSupport.save(userInfo);
        logonInfo = InitData.getLogonInfo();
        logonInfo.setuId(userInfo.getuId());
        logonInfo.setUserInfo(userInfo);
        mysqlDaoSupport.save(logonInfo);
        session.setAttribute(IGlobalConstant.CURRENT_USER, logonInfo);
    }

    @Test
    public void testGetLoginInfoBySession() throws Exception {

        LogonInfo logonInfoDB = loginService.getLogonInfo(logonInfo.getLoginName());
        Assert.assertNotNull(logonInfoDB);
    }

    @Test
    public void testGetLogonInfo() throws Exception {

        String string = loginService.getLogonInfo(logonInfo.getuId());
        Assert.assertNotNull(string);
    }

    @Test
    public void testModLogonInfo() throws Exception {
        Boolean firstBoolean = loginService.modLogonInfo(logonInfo.getuId(), "xxx", "xxx", "xxx",
            "xxx");
        Assert.assertEquals(true, firstBoolean);
    }
}

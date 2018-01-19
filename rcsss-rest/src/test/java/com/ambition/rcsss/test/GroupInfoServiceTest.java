package com.ambition.rcsss.test;

import java.util.List;

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
import com.ambition.rcsss.model.entity.GroupInfo;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.service.GroupInfoService;

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
public class GroupInfoServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(GroupInfoServiceTest.class);

    @Resource
    private GroupInfoService    groupInfoService;

    LogonInfo                   logonInfo;
    GroupInfo                   groupInfo;

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
        UserInfo userInfo = InitData.getUserInfo();
        mysqlDaoSupport.save(userInfo);
        logonInfo = InitData.getLogonInfo();
        logonInfo.setuId(userInfo.getuId());
        logonInfo.setUserInfo(userInfo);
        mysqlDaoSupport.save(logonInfo);
        session.setAttribute(IGlobalConstant.CURRENT_USER, logonInfo);
        groupInfo = InitData.getGroupInfo();
        mysqlDaoSupport.save(groupInfo);

    }

    /**
    * Method: getLoginInfoBySession()
    */
    @Test
    public void testAddOrModGroupInfo() throws Exception {

        Boolean firstBoolean = groupInfoService.addOrModGroupInfo(groupInfo);
        Assert.assertEquals(true, firstBoolean);
        groupInfo.setContent("修改内容");
        groupInfo.setfGroupId(0L);
        groupInfo.setGroupName("修改组名");
        Boolean secondBoolean = groupInfoService.addOrModGroupInfo(groupInfo);
        Assert.assertEquals(true, secondBoolean);

    }

    @Test
    public void testDelGroupInfo() throws Exception {

        Boolean firstBoolean = groupInfoService.delGroupInfo(groupInfo.getGroupId());
        Assert.assertEquals(true, firstBoolean);

    }

    @Test
    public void testGetGroupInfosByFieldId() throws Exception {
        mysqlDaoSupport.save(groupInfo);
        List<GroupInfo> listDB = groupInfoService.getGroupInfosByFieldId();
        Assert.assertNotNull(listDB);
    }

    @Test
    public void testGetGroupInfoByFgroupId() throws Exception {
        GroupInfo groupInfo2 = InitData.getGroupInfo();
        groupInfo2.setContent("璧山区");
        groupInfo2.setfGroupId(groupInfo.getGroupId());
        groupInfo2.setGroupId(2L);
        groupInfo2.setGroupName("璧山区");
        mysqlDaoSupport.save(groupInfo2);
        List<GroupInfo> groupInfoDB = groupInfoService.getGroupInfoByFgroupId(groupInfo2
            .getfGroupId());
        Assert.assertNotNull(groupInfoDB);
    }

    @Test
    public void testGetGroupInfosBygroupId() throws Exception {
        GroupInfo groupInfo = InitData.getGroupInfo();
        GroupInfo groupInfo2 = InitData.getGroupInfo();
        groupInfo2.setContent("璧山区");
        groupInfo2.setfGroupId(groupInfo.getGroupId());
        groupInfo2.setGroupId(2L);
        groupInfo2.setGroupName("璧山区");
        mysqlDaoSupport.save(groupInfo2);
        List<GroupInfo> listDB = groupInfoService.getGroupInfosBygroupId(groupInfo.getGroupId());
        Assert.assertNotNull(listDB);
    }
}

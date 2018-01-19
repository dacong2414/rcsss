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
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.PropertyInfo;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.service.PropertyInfoService;

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
public class PropertyInfoServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyInfoServiceTest.class);

    @Resource
    private PropertyInfoService propertyInfoService;

    LogonInfo                   logonInfo;
    List<PropertyInfo>          propertyInfoList;
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
        propertyInfoList = InitData.getPropertyInfoList();
    }

    /**
    * Method: getLoginInfoBySession()
    */
    /*  @Test
      public void testAddOrModPropertyInfo() throws Exception {

          Boolean booleanFirst = propertyInfoService.addOrModPropertyInfo(propertyInfoList);
          for (PropertyInfo propertyInfo : propertyInfoList) {
              propertyInfo.setCustomizeFlag(PropertyInfo.CUSTOMIZE_FLAG_DISABLE);
              propertyInfo.setDefaultValue("defaultValue修改");
              propertyInfo.setDisableFlag(PropertyInfo.DISABLE_FALG_DISABLE);
              propertyInfo.setDisplayOrder(3L);
              propertyInfo.setFieldId(1L);
              propertyInfo.setGlobalFlag(PropertyInfo.GLOBAL_FLAG_PRIVATE_PUBLIC);
              propertyInfo.setHtmlType(PropertyInfo.HTML_TYPE_TEXT);
              propertyInfo.setPropertyDesc("属性描述修改");
              propertyInfo.setPropertyId(1L);
              propertyInfo.setPropertyNameCn("采集卡修改");
              propertyInfo.setPropertyNameEn("card11");
          }
          Assert.assertEquals(true, booleanFirst);
          Boolean second = propertyInfoService.addOrModPropertyInfo(propertyInfoList);
          Assert.assertEquals(true, second);

      }
    */
    @Test
    public void testDelPropertyInfo() throws Exception {
        PropertyInfo propertyInfo = InitData.getPropertyInfo();
        mysqlDaoSupport.save(propertyInfo);
        Boolean firstBoolean = propertyInfoService.delPropertyInfo(propertyInfo.getPropertyId());
        Assert.assertEquals(true, firstBoolean);

    }

    @Test
    public void testExistBoolean() throws Exception {
        PropertyInfo propertyInfo = InitData.getPropertyInfo();
        mysqlDaoSupport.save(propertyInfo);
        Boolean thirdBoolean = propertyInfoService
            .existBoolean("xxx", propertyInfo.getPropertyId());
        Assert.assertEquals(true, thirdBoolean);

    }

}

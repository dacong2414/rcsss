package com.ambition.rcsss.test;

import java.util.ArrayList;

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
import com.ambition.rcsss.model.entity.ConsultDetail;
import com.ambition.rcsss.model.entity.ConsultTotal;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.model.pojo.sendmessage2c.consult.SuperSoundConsultDetailVo;
import com.ambition.rcsss.service.CountSuperSoundConsultService;
import com.ambition.rcsss.utils.ObjectUtil;

/**
 * 统计超声会诊服务实现的单元测试
 * Created by wxh on 2017/9/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
@Transactional
@Slf4j
public class CountSuperSoundConsultServiceImplTest {
    @Resource
    private CountSuperSoundConsultService countSuperSoundConsultService;
    @Resource
    private MysqlDaoSupport               mysqlDaoSupport;
    @Resource
    private HttpSession                   session;

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
        LogonInfo logonInfo = InitData.getLogonInfo("countSuperSoundConsultService");
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
     * 测试根据会诊总表主键ID获取统计超声会诊详情集合信息
     * 测试目标方法：ArrayList<SuperSoundConsultDetailVo> listSuperSoundConsultDetail(Long linkId)
     */
    @Test
    public void testListSuperSoundConsultDetail() {
        // 当前登录信息
        LogonInfo currentLogonInfo = ObjectUtil.typeConversion(session
            .getAttribute(IGlobalConstant.CURRENT_USER));
        // 当前域信息
        // 初始化用户信息
        UserInfo userInfo = InitData.getUserInfo();
        mysqlDaoSupport.save(userInfo);
        // 初始化会诊总表信息
        ConsultTotal consultTotal = InitData.getConsultTotal();
        consultTotal.setuId(currentLogonInfo.getuId());
        mysqlDaoSupport.save(consultTotal);
        // 初始化会诊详情信息
        ConsultDetail consultDetail = InitData.getConsultDetail();
        consultDetail.setLinkId(consultTotal.getRecId());
        consultDetail.setuId(userInfo.getuId());
        consultDetail.setInviterId(currentLogonInfo.getuId());
        mysqlDaoSupport.save(consultDetail);
        // 获取统计超声会诊详情集合信息
        ArrayList<SuperSoundConsultDetailVo> superSoundConsultDetailVos = countSuperSoundConsultService
            .listSuperSoundConsultDetail(consultTotal.getRecId());
        // 判断数据是否插入成功
        Assert.assertTrue(superSoundConsultDetailVos.size() == 1);
    }
}

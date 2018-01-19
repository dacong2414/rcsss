package com.ambition.rcsss.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ambition.rcsss.dao.BaseConfigDao;
import com.ambition.rcsss.dao.ConsultTotalDao;
import com.ambition.rcsss.dao.DoctorInfoDao;
import com.ambition.rcsss.dao.HClientConfigDao;
import com.ambition.rcsss.dao.LogonInfoDao;
import com.ambition.rcsss.dao.MacAddressDao;
import com.ambition.rcsss.dao.MeetingInfoDao;
import com.ambition.rcsss.dao.MonitorJobDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.PatientInfoDao;
import com.ambition.rcsss.dao.SysResourcesDao;
import com.ambition.rcsss.dao.SysRolesDao;
import com.ambition.rcsss.dao.UpdateConfigDao;
import com.ambition.rcsss.dao.UserGroupDao;
import com.ambition.rcsss.dao.UserInfoDao;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.ClientProperty;
import com.ambition.rcsss.model.entity.ConsultDetail;
import com.ambition.rcsss.model.entity.ConsultTotal;
import com.ambition.rcsss.model.entity.DeleteBackUp;
import com.ambition.rcsss.model.entity.DoctorInfo;
import com.ambition.rcsss.model.entity.GroupInfo;
import com.ambition.rcsss.model.entity.HttpSendFailedResend;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.MacAddress;
import com.ambition.rcsss.model.entity.MeetingInfo;
import com.ambition.rcsss.model.entity.MonitorClientMapping;
import com.ambition.rcsss.model.entity.PatientInfo;
import com.ambition.rcsss.model.entity.ReportInfo;
import com.ambition.rcsss.model.entity.SysResources;
import com.ambition.rcsss.model.entity.SysRoles;
import com.ambition.rcsss.model.entity.UpdateConfig;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.model.entity.VideoInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteBMVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteCCVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteCMEETINGVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteDELVRVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteEMVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteGDCVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteGUAVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteINFVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteIVVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteLDVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteLVVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMACGHCVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMACVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMERVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMONITORSVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteQLVVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteQUVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteSQRVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteVRBVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteVREVo;
import com.ambition.rcsss.service.SendMessage2CService;
import com.ambition.rcsss.utils.DateUtils;
import com.ambition.rcsss.utils.DigestPass;
import com.ambition.rcsss.utils.StringUtils;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoServiceImpl.java, v 0.1 2017年8月17日 上午10:11:45 ambition Exp $
 */
@Service
public class SendMessage2CServiceImpl extends BaseService implements SendMessage2CService {
    @Autowired
    UserInfoDao      userInfoDao;
    @Autowired
    DoctorInfoDao    doctorInfoDao;
    @Autowired
    LogonInfoDao     logonInfoDao;
    @Autowired
    UserGroupDao     userGroupDao;
    @Autowired
    MeetingInfoDao   meetingInfoDao;
    @Autowired
    SysRolesDao      sysRolesDao;
    @Autowired
    SysResourcesDao  sysResourcesDao;
    @Autowired
    ConsultTotalDao  consultTotalDao;
    @Autowired
    MysqlDaoSupport  mysqlDaoSupport;
    @Autowired
    UpdateConfigDao  updateConfigDao;
    @Autowired
    BaseConfigDao    baseConfigDao;
    @Autowired
    MacAddressDao    macAddressDao;
    @Autowired
    HClientConfigDao hClientConfigDao;
    @Autowired
    PatientInfoDao   patientInfoDao;
    @Autowired
    MonitorJobDao    monitorJobDao;

    /** 
     * @param jsonObject
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeLD(com.alibaba.fastjson.JSONObject)
     */
    @Override
    public ResultInfo<Map<String, Object>> login(ExecuteLDVo executeLDVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String loginName = executeLDVo.getLoginName();
        String loginPwd = executeLDVo.getPassword();
        LogonInfo logonInfo = logonInfoDao.getLoginInfoByName(loginName);
        if (logonInfo == null) {
            throw new ProcessException(CodeEnum.ERROR_5047);
        } else if (logonInfo.getDisableFlag().equals(IGlobalConstant.ENABLED)) {
            if (logonInfo.getLoginPwd().equals(DigestPass.getDigestPassWord(loginPwd))) {
                //账号处于激活状态且密码正确
                UserInfo userInfo = userInfoDao.getUserInfoByUID(logonInfo.getuId());
                map.put("userInfo", userInfo);
                List<SysResources> sysResourcesListDB = sysResourcesDao
                    .getUmstModuleResourceByUID(logonInfo.getuId());
                map.put("loadmodule", sysResourcesListDB);
            }
        } else {
            throw new ProcessException(CodeEnum.ERROR_5048);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param jsonObject
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeSQR(com.alibaba.fastjson.JSONObject)
     */
    @Override
    public ResultInfo<Map<String, Object>> getUserInfo(ExecuteSQRVo executeSQRVo) {
        Long uId = executeSQRVo.getuId();
        Map<String, Object> map = new HashMap<String, Object>();
        UserInfo userInfo = userInfoDao.getUserInfoByUID(uId);
        if (userInfo == null) {
            throw new ProcessException(CodeEnum.ERROR_5047);
        }
        map.put("userInfo", userInfo);
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param jsonObject
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeQU(com.alibaba.fastjson.JSONObject)
     */
    @Override
    public ResultInfo<Map<String, Object>> getUserInfosByRoleName(ExecuteQUVo executeQUVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> userInfos = new ArrayList<>();
        String roleName = executeQUVo.getRoleName();
        List<?> userInfo = userGroupDao.getUesrInfoByRoleName(roleName);
        if (userInfo.size() > 0) {
            for (Object obj : userInfo) {
                Map<String, Object> mapUser = new HashMap<String, Object>();
                Object[] userObjects = (Object[]) obj;
                mapUser.put("uId", userObjects[0]);
                mapUser.put("title", userObjects[1]);
                mapUser.put("description", userObjects[2]);
                mapUser.put("roleName", userObjects[3]);
                mapUser.put("groupId", userObjects[4]);
                userInfos.add(mapUser);
            }
        }
        if (userInfos.size() == 0) {
            throw new ProcessException(CodeEnum.ERROR_5052);
        } else {
            map.put("userInfos", userInfos);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param jsonObject
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeBM(com.alibaba.fastjson.JSONObject)
     */
    @Override
    public ResultInfo<Map<String, Object>> joinSession(ExecuteBMVo executeBMVo) {
        Calendar calendar = Calendar.getInstance();
        Map<String, Object> map = new HashMap<String, Object>();
        Long uId = executeBMVo.getuId();
        Long recvUId = executeBMVo.getRecvUId();
        String billNum = executeBMVo.getBillNum();
        ConsultTotal consultTotalDB = consultTotalDao.getByUIdAndBillNum(recvUId, billNum);
        ConsultDetail consultDetail;
        if (consultTotalDB != null) {//如果存在 是断线重连    在会诊详情表增加相关记录
            //这里要判断是否为断线重连        开始会诊时间与异常中断的时间差
            Long recId = consultTotalDB.getRecId();
            consultDetail = new ConsultDetail();
            consultDetail.setGmtCreate(calendar.getTime());
            consultDetail.setLinkId(recId);
            consultDetail.setOpra(ConsultDetail.OPRA_BREAK_RECONNECTION);
            consultDetail.setuId(uId);//这里
            mysqlDaoSupport.save(consultDetail);
            consultDetail = new ConsultDetail();
            consultDetail.setGmtCreate(calendar.getTime());
            consultDetail.setLinkId(recId);
            consultDetail.setOpra(ConsultDetail.OPRA_BREAK_RECONNECTION);
            consultDetail.setuId(recvUId);//这里
            mysqlDaoSupport.save(consultDetail);
            map.put("recId", recId);
        }
        if (consultTotalDB == null) {//加入新会话操作
            ConsultTotal consultTotal = new ConsultTotal();
            consultTotal.setuId(uId);
            consultTotal.setBillNum(billNum);
            consultTotal.setGmtBegin(calendar.getTime());
            mysqlDaoSupport.save(consultTotal);

            long recId = consultTotal.getRecId();//获取会话id 
            consultDetail = new ConsultDetail();
            consultDetail.setGmtCreate(calendar.getTime());
            consultDetail.setLinkId(recId);
            consultDetail.setOpra(ConsultDetail.OPRA_JOIN_SESSION);
            consultDetail.setuId(recvUId);// 被动接受邀请 //当前操作人
            consultDetail.setInviterId(uId);//邀请人的id.
            mysqlDaoSupport.save(consultDetail);
            map.put("recId", recId);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param jsonObject
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeIV(com.alibaba.fastjson.JSONObject)
     */
    @Override
    public ResultInfo<String> inviteExpert(ExecuteIVVo executeIVVo) {
        Calendar calendar = Calendar.getInstance();
        Long uId = executeIVVo.getuId();
        Long invitedId = executeIVVo.getInviterId();
        Long recId = executeIVVo.getRecId();
        ConsultTotal consultTotal = consultTotalDao.getByRecId(recId);//根据传过来的recId在总表里面查找
        if (consultTotal != null) {//说明找到了记录 向会诊详情表里插入数据
            ConsultDetail consultDetail = new ConsultDetail();
            consultDetail.setGmtCreate(calendar.getTime());
            consultDetail.setInviterId(invitedId);//
            consultDetail.setLinkId(recId);
            consultDetail.setOpra(ConsultDetail.OPRA_JOIN_SESSION);
            consultDetail.setuId(uId);
            mysqlDaoSupport.save(consultDetail);
        } else {
            throw new ProcessException(CodeEnum.ERROR_5049);
        }
        return ResultInfo.createSuccessResult("成功");

    }

    /** 
     * @param executeIVVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeQLV(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteIVVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> getNewVersion(ExecuteQLVVo executeQLVVo) {
        String sysInfo = executeQLVVo.getSysInfo();
        String updateType = executeQLVVo.getUpdateType();
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(sysInfo) && !StringUtils.isEmpty(updateType)) {//获取这个系统的最新数据  根据updateType（hclient）  sysInfo
            List<UpdateConfig> updateConfigListDB = updateConfigDao
                .getUpdateConfigBysysInfoAndupdateType(updateType, sysInfo);
            if (updateConfigListDB != null && updateConfigListDB.size() > 0) {
                map.put("updateConfig", updateConfigListDB.get(0));
            } else {
                throw new ProcessException(CodeEnum.ERROR_5050);
            }
        } else {
            throw new ProcessException(CodeEnum.ERROR_5050);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeGDCVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeGDC(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteGDCVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> getBaseConfigAndCommonConfig(ExecuteGDCVo executeGDCVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object[]> baseConfigAndCommonConfig = baseConfigDao.getCommonConfig();
        if (baseConfigAndCommonConfig.size() == 0) {
            throw new ProcessException(CodeEnum.ERROR_5051);
        } else {//获取所有公共服务器配置
            Map<String, Object> mapConfig = new HashMap<String, Object>();
            for (Object[] objects : baseConfigAndCommonConfig) {
                mapConfig.put((String) objects[0], objects[1]);
            }
            map.put("config", mapConfig);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeMERVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeMER(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMERVo)
     */
    @Override
    public ResultInfo<String> exceptionInterrupt(ExecuteMERVo executeMERVo) {
        Calendar calendar = Calendar.getInstance();
        Long uId = executeMERVo.getuId();
        Long recId = executeMERVo.getRecId();
        ConsultTotal consultTotal = consultTotalDao.getByRecId(recId);
        if (consultTotal != null) {//有记录 向会诊详情表出入数据
            ConsultDetail consultDetail = new ConsultDetail();
            consultDetail.setGmtCreate(calendar.getTime());
            consultDetail.setLinkId(recId);
            consultDetail.setOpra(ConsultDetail.OPRA_EXCEPTION_INTERRUPT);
            consultDetail.setuId(uId);
            mysqlDaoSupport.save(consultDetail);
        } else {
            throw new ProcessException(CodeEnum.ERROR_5049);
        }
        return ResultInfo.createSuccessResult("成功");
    }

    /** 
     * @param executeLVVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeLV(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteLVVo)
     */
    @Override
    public ResultInfo<String> leaveSession(ExecuteLVVo executeLVVo) {
        Calendar calendar = Calendar.getInstance();
        Long uId = executeLVVo.getuId();
        Long recId = executeLVVo.getRecId();
        ConsultTotal consultTotalDB = consultTotalDao.getByRecId(recId);
        if (consultTotalDB != null) {
            ConsultDetail consultDetail = new ConsultDetail();
            consultDetail.setGmtCreate(calendar.getTime());
            consultDetail.setLinkId(recId);
            consultDetail.setOpra(ConsultDetail.OPRA_LEAVE_SESSION);
            consultDetail.setuId(uId);
            mysqlDaoSupport.save(consultDetail);
        } else {
            throw new ProcessException(CodeEnum.ERROR_5049);
        }
        return ResultInfo.createSuccessResult("成功");
    }

    /** 
     * @param executeEMVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeEM(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteEMVo)
     */
    @Override
    public ResultInfo<String> endSession(ExecuteEMVo executeEMVo) {
        Calendar calendar = Calendar.getInstance();
        Long recId = executeEMVo.getRecId();
        ConsultTotal consultTotalDB = consultTotalDao.getByRecId(recId);
        if (consultTotalDB != null) {//找到数据更行结束时间
            consultTotalDB.setGmtEnd(calendar.getTime());
            mysqlDaoSupport.update(consultTotalDB);
        } else {
            throw new ProcessException(CodeEnum.ERROR_5049);
        }
        return ResultInfo.createSuccessResult("成功");
    }

    /** 
     * @param executeINFVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeINF(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteINFVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> forceUpdate(ExecuteINFVo executeINFVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object[]> listDB = updateConfigDao.getSysByFieldId();
        if (null != listDB) {//有数据
            map.put("sysInfo", listDB);
        } else {//没有数据
            throw new ProcessException(CodeEnum.ERROR_5052);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeMACVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeMAC(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMACVo)
     */
    @Override
    public ResultInfo<String> saveMAC(ExecuteMACVo executeMACVo) {
        Calendar calendar = Calendar.getInstance();
        String macStr = executeMACVo.getMacaddress();
        String loginName = executeMACVo.getLoginName();
        String zipName = executeMACVo.getZipname();
        LogonInfo logDB = logonInfoDao.getLoginInfoByName(loginName);//通过登录名得到  这个人的相关信息
        if ("".equals(macStr) || "".equals(loginName) || "".equals(zipName) || null == logDB) {//其中有一个为空就返回
            throw new ProcessException(CodeEnum.ERROR_5053);
        } else {//传的数据不为空时 并能找到logDB相应的值
            MacAddress macDB = macAddressDao.getMacAddressByMAC(macStr);//通过mac地址获得  这条信息
            if (null == macDB) { //新增
                macDB = new MacAddress();
                macDB.setGmtCreate(calendar.getTime());
                macDB.setMacAddress(macStr);
                macDB.setUserName(userInfoDao.getUserInfoByUID(logDB.getuId()).getTitle());
                macDB.setZipName(zipName);
                mysqlDaoSupport.save(macDB);
            } else {//修改
                macDB.setGmtMod(calendar.getTime());
                macDB.setUserName(userInfoDao.getUserInfoByUID(logDB.getuId()).getTitle());//通过uid 获取userinfo中的title；
                macDB.setZipName(zipName);
                mysqlDaoSupport.update(macDB);
            }
        }
        return ResultInfo.createSuccessResult("成功");

    }

    /** 
     * @param executeCCVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeCC(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteCCVo)
     */
    @Override
    public ResultInfo<String> updateShowConfig(ExecuteCCVo executeCCVo) {
        String macAdress = executeCCVo.getMacAdress();
        Map<String, String> showConfig = executeCCVo.getShowConfig();
        if (showConfig != null && showConfig.size() > 0) {
            for (Map.Entry<String, String> entry : showConfig.entrySet()) {
                ClientProperty clientPropertyDB = hClientConfigDao
                    .getClientPropertyByMacAdressAndPropertyNameEn(macAdress, entry.getKey());
                if (clientPropertyDB != null) {
                    clientPropertyDB.setPropertyValue(entry.getValue());
                    mysqlDaoSupport.update(clientPropertyDB);
                }
            }
        } else {
            throw new ProcessException(CodeEnum.ERROR_5052);
        }
        return ResultInfo.createSuccessResult("成功");
    }

    /** 
     * @param executeGGLVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeGGL(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteGGLVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> getGroupInfos() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<GroupInfo> groupInfosDB = userGroupDao.getGroupInfoByFieldId();
        if (groupInfosDB.size() > 0) {
            for (int i = 0; i < groupInfosDB.size(); i++) {
                if ("中国".equals(groupInfosDB.get(i).getGroupName())) {
                    groupInfosDB.remove(i);
                }
            }
            map.put("groupInfos", groupInfosDB);
        } else {
            throw new ProcessException(CodeEnum.ERROR_5052);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeVRBVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeVRB(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteVRBVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> savePatientVideo(ExecuteVRBVo executeVRBVo) {
        Calendar calendar = Calendar.getInstance();
        String patientId = executeVRBVo.getPatientId();//获取患者Id
        String reportId = executeVRBVo.getReportId();//报告Id
        String checkBeginTime = executeVRBVo.getCheckBeginTime();//检查开始时间
        String fileStoragePath = executeVRBVo.getFileStoragePath();//文件存储位置
        String sex = executeVRBVo.getSex();//性别
        String age = executeVRBVo.getAge();//年龄
        String name = executeVRBVo.getName();//姓名
        String checkPosition = executeVRBVo.getCheckPosition();//检查部位
        Map<String, Object> map = new HashMap<String, Object>();
        PatientInfo patientInfo = patientInfoDao.getPatientInfoByPatientId(patientId);
        if (StringUtils.isEmpty(patientId) || StringUtils.isEmpty(reportId)) {
            throw new ProcessException(CodeEnum.ERROR_5054);
        } else {
            //如果没患者数据
            if (patientInfo == null) {
                patientInfo = new PatientInfo();
                patientInfo.setPatientId(patientId);
                patientInfo.setAge(age);
                patientInfo.setPatientName(name);
                patientInfo.setSex(sex);
                patientInfo.setGmtCreate(calendar.getTime());
                mysqlDaoSupport.save(patientInfo);
            }

            //查询报告
            ReportInfo reportInfo = patientInfoDao.getReportInfoByReportId(reportId);
            //如果查到没有报告 
            if (reportInfo == null) {
                reportInfo = new ReportInfo();
                reportInfo.setCheckBeginTime(DateUtils.changeStrToDate(checkBeginTime,
                    "yyyy-MM-dd HH:mm:ss"));
                reportInfo.setCheckPosition(checkPosition);
                reportInfo.setGmtCreate(calendar.getTime());
                reportInfo.setPatientId(patientInfo.getPatientId());
                reportInfo.setReportId(reportId);
                mysqlDaoSupport.save(reportInfo);
            }
            //存视频
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setFileStoragePath(fileStoragePath);
            videoInfo.setGmtCreate(calendar.getTime());
            videoInfo.setReportId(reportInfo.getReportId());
            videoInfo.setVideoTime(0L);
            mysqlDaoSupport.save(videoInfo);
            map.put("videoRecordId", videoInfo.getVideoId().toString());//传一个视频id过去c++那边要 解析字符串
        }

        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeVREVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeVRE(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteVREVo)
     */
    @Override
    public ResultInfo<String> endPatientVideo(ExecuteVREVo executeVREVo) {
        String patientId = executeVREVo.getPatientId();//获取患者Id
        String reportId = executeVREVo.getReportId();//报告Id
        String checkEndTime = executeVREVo.getCheckEndTime();//检车结束时间
        Long videoLength = executeVREVo.getVideoLength();//视频时长
        Long videoId = executeVREVo.getVideoId();//视频Id
        if (StringUtils.isEmpty(patientId) || StringUtils.isEmpty(reportId)) {
            throw new ProcessException(CodeEnum.ERROR_5055);
        } else {
            //验证是否有患者
            PatientInfo patientInfo = patientInfoDao.getPatientInfoByPatientId(patientId);
            //查询报告
            ReportInfo reportInfo = patientInfoDao.getReportInfoByReportId(reportId);
            VideoInfo videoInfo = patientInfoDao.getVideoInfoByVideoId(videoId);
            //如果患者数据、报告数据、视频数据不存在！！
            if (patientInfo == null || reportInfo == null || videoInfo == null) {
                throw new ProcessException(CodeEnum.ERROR_5052);
            } else {
                //更新结束时间
                reportInfo.setCheckEndTime(DateUtils.changeStrToDate(checkEndTime,
                    "yyyy-MM-dd HH:mm:ss"));
                mysqlDaoSupport.update(reportInfo);
                //设置视频时长
                videoInfo.setVideoTime(videoLength);
                mysqlDaoSupport.update(videoInfo);
            }
        }

        return ResultInfo.createSuccessResult("成功");
    }

    /** 
     * @param executeDELVRVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeDELVR(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteDELVRVo)
     */
    @Override
    public ResultInfo<String> deleteReport(ExecuteDELVRVo executeDELVRVo) {
        Calendar calendar = Calendar.getInstance();
        String reportId = executeDELVRVo.getReportId();
        //查询报告
        ReportInfo reportInfo = patientInfoDao.getReportInfoByReportId(reportId);
        if (reportInfo != null) {
            //删除前把数据复制到另一张表里面
            DeleteBackUp deleteBackup = new DeleteBackUp();
            deleteBackup.setCheckBeginTime(reportInfo.getCheckBeginTime());
            deleteBackup.setCheckEndTime(reportInfo.getCheckEndTime());
            deleteBackup.setCheckPosition(reportInfo.getCheckPosition());
            deleteBackup.setDeleteTime(calendar.getTime());
            deleteBackup.setGmtCreate(calendar.getTime());
            deleteBackup.setPatientId(reportInfo.getPatientId());
            deleteBackup.setReportId(reportInfo.getReportId());
            mysqlDaoSupport.save(deleteBackup);
            //删除报告
            mysqlDaoSupport.delete(reportInfo);
            //删除视频
            patientInfoDao.deleteVideo(reportId);
        } else {
            throw new ProcessException(CodeEnum.ERROR_5052);
        }
        return ResultInfo.createSuccessResult("成功");
    }

    /** 
     * @param executeMACGHCVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeMACGHC(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMACGHCVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> getHClientConfigAndShowConfigAndCommonConfigByMAC(ExecuteMACGHCVo executeMACGHCVo) {
        String macAdress = executeMACGHCVo.getMacAdress();
        List<Object[]> hClientConfigDB = hClientConfigDao.getHClientConfigByMacAddress(macAdress
            .toUpperCase().trim());
        List<Object[]> commonConfig = baseConfigDao.getCommonConfig();
        Map<String, Object> map = new HashMap<String, Object>();
        if (hClientConfigDB == null) {
            throw new ProcessException(CodeEnum.ERROR_5052);
        } else {
            Map<String, String> hClientConfigAndShowConfig = new HashMap<String, String>();
            if (hClientConfigDB.size() > 0) {
                for (Object[] obj : hClientConfigDB) {
                    hClientConfigAndShowConfig.put(obj[0].toString(), obj[1].toString());
                }
            }
            map.put("hClientConfigAndShowConfig", hClientConfigAndShowConfig);
            map.put("commonConfig", commonConfig);//包括了 基础配置 和公共配置
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeMONITORSVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeMONITORS(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMONITORSVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> getMonitorsAndclients(ExecuteMONITORSVo executeMONITORSVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long uId = executeMONITORSVo.getuId();
        List<MonitorClientMapping> monitorsList = baseConfigDao.getMonitorsByuId(uId,
            SysRoles.ROLE_NAME_MONITOR_DCLIENT);
        List<MonitorClientMapping> clientList = baseConfigDao.getClientByuId(uId,
            SysRoles.ROLE_NAME_INNER_CLIENT);
        String monitorString = "";
        if (monitorsList.size() > 0) {
            for (MonitorClientMapping mcm : monitorsList) {
                monitorString += mcm.getMonitorUid() + ",";
            }
            monitorString = monitorString.substring(0, monitorString.length() - 1);
        }
        String clientString = "";
        if (clientList.size() > 0) {
            for (MonitorClientMapping mcm : clientList) {
                clientString += mcm.getClientUid() + ",";
            }
            clientString = clientString.substring(0, clientString.length() - 1);
        }

        if (monitorsList.size() == 0 && clientList.size() == 0) {
            throw new ProcessException(CodeEnum.ERROR_5052);
        } else {
            map.put("monitors", monitorString);
            map.put("clients", clientString);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeGUAVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeGUA(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteGUAVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> getDoctorInfos(ExecuteGUAVo executeGUAVo) {
        String hospitalName = executeGUAVo.getHospitalName();
        Map<String, Object> map = new HashMap<String, Object>();
        List<DoctorInfo> listUserInfoDB = doctorInfoDao.getDoctorByHospitalName(hospitalName);
        if (listUserInfoDB.size() > 0) {
            map.put("doctorList", listUserInfoDB);
        } else {
            throw new ProcessException(CodeEnum.ERROR_5052);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeMEETING(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> getMeetingInfos(ExecuteGDCVo executeGDCVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<MeetingInfo> listMeetingInfoDB = meetingInfoDao.getMeetingInfoAll();
        if (listMeetingInfoDB.size() > 0) {
            map.put("meetingInfoList", listMeetingInfoDB);
        } else {
            throw new ProcessException(CodeEnum.ERROR_5052);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @param executeCMEETINGVo
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#executeCMEETING(com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteCMEETINGVo)
     */
    @Override
    public ResultInfo<Map<String, Object>> saveOrUpdateMeeting(ExecuteCMEETINGVo executeCMEETINGVo) {
        Calendar calendar = Calendar.getInstance();
        String meetingName = executeCMEETINGVo.getMeetingName();
        Long createUserId = executeCMEETINGVo.getCreateUserId();
        String meetingPassWord = executeCMEETINGVo.getMeetingPassWord();
        Map<String, Object> map = new HashMap<String, Object>();
        MeetingInfo meetingInfoDB = meetingInfoDao.getMeetingInfoByMeetingName(meetingName);
        if (meetingInfoDB != null) {//修改
            meetingInfoDB.setCreateUserId(createUserId);
            meetingInfoDB.setGmtMod(calendar.getTime());
            meetingInfoDB.setMeetingPassWord(meetingPassWord);
        } else {
            meetingInfoDB = new MeetingInfo();
            meetingInfoDB.setCreateUserId(createUserId);
            meetingInfoDB.setGmtCreate(calendar.getTime());
            meetingInfoDB.setMeetingName(meetingName);
            meetingInfoDB.setMeetingPassWord(meetingPassWord);
        }
        mysqlDaoSupport.saveOrUpdate(meetingInfoDB);
        map.put("meetingInfo", meetingInfoDB);
        return ResultInfo.createSuccessResult(map);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#getUserInfoList()
     */
    @Override
    public ResultInfo<Map<String, Object>> getUserInfoList() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object[]> userInfoListDB = userInfoDao.getUserInfoList();
        List<Map<String, Object>> userInfoList = new ArrayList<>();
        if (userInfoListDB.size() > 0) {
            for (Object[] userObjects : userInfoListDB) {
                Map<String, Object> mapUser = new HashMap<String, Object>();
                mapUser.put("uId", userObjects[0]);
                mapUser.put("title", userObjects[1]);
                mapUser.put("description", userObjects[2]);
                mapUser.put("roleName", userObjects[3]);
                mapUser.put("groupId", userObjects[4]);
                userInfoList.add(mapUser);
            }
        }
        if (userInfoList.size() == 0) {
            throw new ProcessException(CodeEnum.ERROR_5052);
        } else {
            map.put("userInfoList", userInfoList);
        }
        return ResultInfo.createSuccessResult(map);
    }

    /**
     * http发送消息
     *
     * @param requestUrl
     * @param requestMethod
     * @param data
     * @throws Exception
     */
    @Override
    @Async("myExecutor")
    public JSONObject httpRequest(String requestUrl, String requestMethod, String data)
                                                                                       throws Exception {
        URL url = new URL(requestUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 设置通用的请求属性
        httpURLConnection.setRequestProperty("Accept", "*/*");
        httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
        httpURLConnection.setRequestProperty("connection", "keep-alive");
        //httpURLConnection.setRequestProperty("Content-Length", data.getBytes("UTF-8").length + "");
        // 发送POST请求必须设置如下两行
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        //设置cookie
        //httpURLConnection.setRequestProperty("Cookie", cookie);
        // 设置请求方式（GET/POST）
        httpURLConnection.setRequestMethod(requestMethod);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream outputStream = httpURLConnection.getOutputStream();
        // 注意编码格式，防止中文乱码
        outputStream.write(data.getBytes("UTF-8"));
        outputStream.close();
        int code = httpURLConnection.getResponseCode();
        JSONObject jsonObject = null;
        StringBuffer buffer = null;
        if (code == 200) {
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpURLConnection.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        }
        return jsonObject;
    }

    /** 
     * @param obj
     * @see com.ambition.rcsss.service.SendMessage2CService#save(java.lang.Object)
     */
    @Override
    public void save(Object obj) {
        mysqlDaoSupport.save(obj);
    }

    /** 
     * @param flag
     * @return
     * @see com.ambition.rcsss.service.SendMessage2CService#getHttpSendFailedResends(java.lang.Long)
     */
    @Override
    public List<HttpSendFailedResend> getHttpSendFailedResends(Long flag) {
        return monitorJobDao.getHttpSendFailedResends(flag);
    }

    /** 
     * @param obj
     * @see com.ambition.rcsss.service.SendMessage2CService#update(java.lang.Object)
     */
    @Override
    public void update(Object obj) {
        mysqlDaoSupport.update(obj);
    }

}

package com.ambition.rcsss.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.HttpSendFailedResend;
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

/**
 * http与c++通信用的服务类
 *
 * @author ambition
 * @version $Id: SendMessage2CService.java, v 0.1 2017年8月22日 下午5:16:44 ambition Exp $
 */
public interface SendMessage2CService {

    /**
     * 处理登录
     * @param jsonObject
     * @return
     */
    ResultInfo<Map<String, Object>> exeLogin(ExecuteLDVo executeLDVo);

    /**
     * 获取单个用户的信息
     * @param jsonObject
     * @return
     */
    ResultInfo<Map<String, Object>> getUserInfo(ExecuteSQRVo executeSQRVo);

    /**
     * 获取对应类型的用户列表
     * @param executeQUVo
     * @return
     */
    ResultInfo<Map<String, Object>> getUserInfosByRoleName(ExecuteQUVo executeQUVo);

    /**
     * 加入会话或断线重连
     * @param executeBMVo
     * @return
     */
    ResultInfo<Map<String, Object>> joinSession(ExecuteBMVo executeBMVo);

    /**
     * 邀请专家
     * @param executeIVVo
     * @return
     */
    ResultInfo<String> inviteExpert(ExecuteIVVo executeIVVo);

    /**
     * 获取服务器上存在的最新版本号
     * @param executeQLVVo
     * @return
     */
    ResultInfo<Map<String, Object>> getNewVersion(ExecuteQLVVo executeQLVVo);

    /**
     * 获取所有公共服务器配置(dao层待完成)
     * @param executeGDCVo
     * @return
     */
    ResultInfo<Map<String, Object>> getBaseConfigAndCommonConfig(ExecuteGDCVo executeGDCVo);

    /**
     * 异常中断
     * @param executeMERVo
     * @return
     */
    ResultInfo<String> exceptionInterrupt(ExecuteMERVo executeMERVo);

    /**
     * 离开会话
     * @param executeLVVo
     * @return
     */
    ResultInfo<String> leaveSession(ExecuteLVVo executeLVVo);

    /**
     * 结束会话
     * @param executeEMVo
     * @return
     */
    ResultInfo<String> endSession(ExecuteEMVo executeEMVo);

    /**
     * 处理强制更新   用json串发送：用update_type 和sys_info来去重
     * @param executeINFVo
     * @return
     */
    ResultInfo<Map<String, Object>> forceUpdate(ExecuteINFVo executeINFVo);

    /**
     * 保存mac地址 有的话覆盖  没有就新增
     * @param executeMACVo
     * @return
     */
    ResultInfo<String> saveMAC(ExecuteMACVo executeMACVo);

    /**
     * CC 修改诊室端颜色空间配置
     * @param executeCCVo
     * @return
     */
    ResultInfo<String> updateShowConfig(ExecuteCCVo executeCCVo);

    /**
     * 获取所有节点列表
     *
     * @param executeGGLVo
     * @return
     */
    ResultInfo<Map<String, Object>> getGroupInfos();

    /**
     * 开始录像记录保存患者的报告和视频信息 （报告Id可能重复传）
     * @param executeVRB
     * @return
     */
    ResultInfo<Map<String, Object>> savePatientVideo(ExecuteVRBVo executeVRB);

    /**
     * 结束录像记录保存患者的报告和视频信息
     *
     * @param executeVREVo
     * @return
     */
    ResultInfo<String> endPatientVideo(ExecuteVREVo executeVREVo);

    /**
     * 删除报告
     * @param executeDELVRVo
     * @return
     */
    ResultInfo<String> deleteReport(ExecuteDELVRVo executeDELVRVo);

    /**
     * 通过mac地址获取GHC
     * @param executeMACGHCVo
     * @return
     */
    ResultInfo<Map<String, Object>> getHClientConfigAndShowConfigAndCommonConfigByMAC(ExecuteMACGHCVo executeMACGHCVo);

    /**
     * 获取监控关系
     * @param executeMONITORSVo
     * @return
     */
    ResultInfo<Map<String, Object>> getMonitorsAndclients(ExecuteMONITORSVo executeMONITORSVo);

    /**
     *获取该医院的医生信息
     * @param executeGUAVo
     * @return
     */
    ResultInfo<Map<String, Object>> getDoctorInfos(ExecuteGUAVo executeGUAVo);

    /**
     *  获取web配置的所有会议
     * @param executeVo
     * @return
     */
    ResultInfo<Map<String, Object>> getMeetingInfos();

    /**
     *存储c++端新增修改的 会议组
     * @param executeCMEETINGVo
     * @return
     */
    ResultInfo<Map<String, Object>> saveOrUpdateMeeting(ExecuteCMEETINGVo executeCMEETINGVo);

    /**
     * 主动发送http请求推送消息
     *
     * @param requestUrl
     * @param requestMethod
     * @param data
     * @return
     */
    public JSONObject httpRequest(String requestUrl, String requestMethod, String data)
                                                                                       throws Exception;

    /**
     * 保存到数据库
     * @param jsonObject
     * @return
     */
    void save(Object obj);

    /**
     *  取发送失败的
     *
     * @param flag
     * @return
     */

    List<HttpSendFailedResend> getHttpSendFailedResends(Long flag);

    /**
     *更新对象
     * @param httpSendFailedResend
     */
    void update(Object obj);

    /**
     *  获取所有用户(除去管理员)
     *
     * @return
     */
    ResultInfo<Map<String, Object>> getUserInfoList();

    /**
     * 获取所有的监控关系
     * @return
     */
    ResultInfo<Map<String, Object>> getMonitorRelationList();

}

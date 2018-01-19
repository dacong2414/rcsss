/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambition.rcsss.common.CheckSignControl;
import com.ambition.rcsss.common.monitor.UseTable;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.model.pojo.sendmessage2c.CustomRelationalVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteBMVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteCCVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteCMEETINGVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteEMVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteGDCVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteGUAVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteINFVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteIVVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteLDVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteLVVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMACGHCVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMERVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteMONITORSVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteQLVVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.ExecuteSQRVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.consult.HeartBeatVo;
import com.ambition.rcsss.service.GroupInfoService;
import com.ambition.rcsss.service.MonitorJobService;
import com.ambition.rcsss.service.SendMessage2CService;

/**
 *用http和c++通信
 * @author ambition
 * @version $Id: UserinfoController.java, v 0.1 2017年8月16日 上午10:34:21 ambition Exp $
 */
@Slf4j
@RestController
@RequestMapping("/message")
@Api(tags = "SendMessage2CController", description = "用http和c++通信")
public class SendMessage2CController {
    @Autowired
    private SendMessage2CService sendMessage2CService;
    @Autowired
    private MonitorJobService    monitorJobService;
    @Autowired
    private GroupInfoService     groupInfoService;

    /**
     * 登录验证
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/login", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "登录验证 ->客户端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "user_info", "sys_resources" })
    public ResultInfo<Map<String, Object>> login(@RequestBody ExecuteLDVo executeLDVo) {
        return sendMessage2CService.login(executeLDVo);

    }

    /**
     * 获取单个用户信息
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取单个用户信息->服务端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "user_info" })
    public ResultInfo<Map<String, Object>> getUserInfo(@RequestBody ExecuteSQRVo executeSQRVo) {
        return sendMessage2CService.getUserInfo(executeSQRVo);

    }

    /**
     * 获取对应类型的用户列表
     *
     * @param jsonObject
     * @return
     */
    /*  @RequestMapping(value = "/getUserInfosByRoleName", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
      @ApiOperation(value = " 获取对应类型的用户列表")
      @CheckSignControl(description = "head验证")
      public ResultInfo<Map<String, Object>> getUserInfosByRoleName(@RequestBody ExecuteQUVo executeQUVo) {
          return sendMessage2CService.getUserInfosByRoleName(executeQUVo);

      }*/

    /**
     * 获取所有用户
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getUserInfoList", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取所有用户(除去admin这一条数据)->服务端调用")
    //@CheckSignControl(description = "head验证")
    public ResultInfo<Map<String, Object>> getUserInfoList() {
        return sendMessage2CService.getUserInfoList();

    }

    /**
     * 加入会话    和断线重连
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/joinSession", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "加入会话 和断线重连->服务端调用->计费功能记录会诊信息")
    @CheckSignControl(description = "head验证")
    public ResultInfo<Map<String, Object>> joinSession(@RequestBody ExecuteBMVo executeBMVo) {
        return sendMessage2CService.joinSession(executeBMVo);

    }

    /**
     * 邀请专家
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/inviteExpert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "邀请专家->服务端调用->计费功能记录会诊信息")
    @CheckSignControl(description = "head验证")
    public ResultInfo<String> inviteExpert(@RequestBody ExecuteIVVo executeIVVo) {
        return sendMessage2CService.inviteExpert(executeIVVo);

    }

    /**
     * 异常中断
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/exceptionInterrupt", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "异常中断->服务端调用->计费功能记录会诊信息")
    @CheckSignControl(description = "head验证")
    public ResultInfo<String> exceptionInterrupt(@RequestBody ExecuteMERVo executeMERVo) {
        return sendMessage2CService.exceptionInterrupt(executeMERVo);

    }

    /**
     * 离开会话
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/leaveSession", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "离开会话->服务端调用->计费功能记录会诊信息")
    @CheckSignControl(description = "head验证")
    public ResultInfo<String> leaveSession(@RequestBody ExecuteLVVo ExecuteLVVo) {
        return sendMessage2CService.leaveSession(ExecuteLVVo);

    }

    /**
     * 结束会话
     *
     * @param jsonObject
     * @return
     */

    @RequestMapping(value = "/endSession", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "结束会话->服务端调用->计费功能记录会诊信息")
    @CheckSignControl(description = "head验证")
    public ResultInfo<String> endSession(@RequestBody ExecuteEMVo executeEMVo) {
        return sendMessage2CService.endSession(executeEMVo);

    }

    /**
     * 获取服务器上存在的最新版本号
     *
     * @param jsonObject
     * @return
     */

    @RequestMapping(value = "/getNewVersion", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取服务器上存在的最新版本号->服务端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "update_config" })
    public ResultInfo<Map<String, Object>> getNewVersion(@RequestBody ExecuteQLVVo executeQLVVo) {
        return sendMessage2CService.getNewVersion(executeQLVVo);

    }

    /**
     * 获取所有公共服务器配置
     *
     * @param jsonObject
     * @return
     */

    @RequestMapping(value = "/getCommonConfig", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取服获取所有公共服务器配置->客户端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "property_info" })
    public ResultInfo<Map<String, Object>> getBaseConfigAndCommonConfig(@RequestBody ExecuteGDCVo executeGDCVo) {
        return sendMessage2CService.getBaseConfigAndCommonConfig(executeGDCVo);

    }

    /**
     * 处理强制更新   用json串发送：用update_type 和sys_info来去重
     *
     * @param jsonObject
     * @return
     */

    @RequestMapping(value = "/forceUpdate", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "处理强制更新->服务端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "update_config" })
    public ResultInfo<Map<String, Object>> forceUpdate(@RequestBody ExecuteINFVo executeINFVo) {
        return sendMessage2CService.forceUpdate(executeINFVo);

    }

    /**
     * 保存mac地址 有的话覆盖  没有就新增
     *
     * @param jsonObject
     * @return
     */

    /* @RequestMapping(value = "/saveMAC", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
     @ApiOperation(value = "保存mac地址 有的话覆盖  没有就新增")
     @CheckSignControl(description = "head验证")
     public ResultInfo<String> saveMAC(@RequestBody ExecuteMACVo executeMACVo) {
         return sendMessage2CService.saveMAC(executeMACVo);

     }*/

    /**
     * CC 修改诊室端颜色空间配置
     * 
     *
     * @param jsonObject
     * @return
     */

    @RequestMapping(value = "/updateShowConfig", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "CC 修改诊室端颜色空间配置 {\"  macAdress\": \"string\",\"  showConfig\": {\"cardName\":\"111\",\"cardName11\":\"222\"} } ->服务端调用")
    @CheckSignControl(description = "head验证")
    public ResultInfo<String> updateShowConfig(@RequestBody ExecuteCCVo executeCCVo) {
        return sendMessage2CService.updateShowConfig(executeCCVo);

    }

    /**
     * 获取所有节点列表
     *
     * @param jsonObject
     * @return
     */

    @RequestMapping(value = "/getGroupInfos", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取所有节点列表->服务端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "group_info" })
    public ResultInfo<Map<String, Object>> getGroupInfos() {
        return sendMessage2CService.getGroupInfos();

    }

    /**
     * 开始录像记录保存患者的报告和视频信息 （报告Id可能重复传）
     *
     * @param jsonObject
     * @return
     */

    /* @RequestMapping(value = "/savePatientVideo", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
     @ApiOperation(value = "开始录像记录保存患者的报告和视频信息")
     @CheckSignControl(description = "head验证")
     public ResultInfo<Map<String, Object>> savePatientVideo(@RequestBody ExecuteVRBVo executeVRBVo) {
         return sendMessage2CService.savePatientVideo(executeVRBVo);

     }
    */
    /**
     * 结束录像记录保存患者的报告和视频信息
     *
     * @param jsonObject
     * @return
     */

    /* @RequestMapping(value = "/endPatientVideo", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
     @ApiOperation(value = "结束录像记录保存患者的报告和视频信息")
     @CheckSignControl(description = "head验证")
     public ResultInfo<String> endPatientVideo(@RequestBody ExecuteVREVo executeVREVo) {
         return sendMessage2CService.endPatientVideo(executeVREVo);

     }*/

    /**
     * 删除报告
     *
     * @param jsonObject
     * @return
     */
    /*  @RequestMapping(value = "/deleteReport", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
      @ApiOperation(value = "删除报告->服务端调用")
      @CheckSignControl(description = "head验证")
      public ResultInfo<String> deleteReport(@RequestBody ExecuteDELVRVo executeDELVRVo) {
          return sendMessage2CService.deleteReport(executeDELVRVo);

      }*/

    /**
     * 通过mac地址获取GHC
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getClientConfigByMAC", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "通过mac地址获取 hClientConfigAndShowConfig和commonConfig   ->客户端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "property_info", "client_property" })
    public ResultInfo<Map<String, Object>> getHClientConfigAndShowConfigAndCommonConfigByMAC(@RequestBody ExecuteMACGHCVo executeMACGHCVo) {
        return sendMessage2CService
            .getHClientConfigAndShowConfigAndCommonConfigByMAC(executeMACGHCVo);

    }

    /**
     * 获取监控关系
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getMonitorsAndclients", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取监控关系->服务端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "monitor_client_mapping" })
    public ResultInfo<Map<String, Object>> getMonitorsAndclients(@RequestBody ExecuteMONITORSVo executeMONITORSVo) {
        return sendMessage2CService.getMonitorsAndclients(executeMONITORSVo);

    }

    /**
     * 获取该医院的医生信息
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getDoctorInfos", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取该医院的医生信息->服务端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "doctor_info" })
    public ResultInfo<Map<String, Object>> getDoctorInfos(@RequestBody ExecuteGUAVo executeGUAVo) {
        return sendMessage2CService.getDoctorInfos(executeGUAVo);

    }

    /**
     * 获取web配置的所有会议
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getMeetingInfos", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取web配置的所有会议->服务端调用")
    @CheckSignControl(description = "head验证")
    @UseTable(tables = { "meeting_info" })
    public ResultInfo<Map<String, Object>> getMeetingInfos(@RequestBody ExecuteGDCVo executeVo) {
        return sendMessage2CService.getMeetingInfos(executeVo);

    }

    /**
     * 存储c++端新增修改的 会议组
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateMeeting", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "存储c++端新增修改的 会议组->服务端调用")
    @CheckSignControl(description = "head验证")
    public ResultInfo<Map<String, Object>> saveOrUpdateMeeting(@RequestBody ExecuteCMEETINGVo executeCMEETINGVo) {
        return sendMessage2CService.saveOrUpdateMeeting(executeCMEETINGVo);

    }

    /**
     * 
     * 如果自定义配置了 传uId过来查出能够看到的人或组里的人
     * @param flag
     * @param uId
     * @return
     */
    @RequestMapping(value = "/getCustomRelationalUids", method = { RequestMethod.POST })
    @ApiOperation(value = "如果自定义配置了 传uId过来查出能够看到的人或组里的人->uId 自定义左边对应id  flag 全局配置的标志（例如：allPersonSee）->服务端调用")
    @CheckSignControl(description = "head验证")
    public ResultInfo<Map<String, Object>> getCustomRelationalUids(@RequestBody CustomRelationalVo customRelationalVo) {
        return groupInfoService.getCustomRelationalUids(customRelationalVo);

    }

    @PostMapping(value = "/getHeartBeat")
    @ApiOperation(value = "C端心跳获取服务端接口状态->服务端调用")
    @CheckSignControl(description = "head验证")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "beat", value = "客户端信息,必填", required = true, paramType = "body", dataType = "HeartBeatVo") })
    public ResultInfo<HeartBeatVo> getHeartBeat(@RequestBody HeartBeatVo beat) {
        log.debug("==[/getHeartBeat]==>心跳请求：mac={},time={}", beat.getClientMac(),
            new Date(beat.getClientTime()));
        if (StringUtils.isEmpty(beat.getClientMac()) || beat.getClientTime() == null) {
            throw new ProcessException(CodeEnum.ERROR_5046);
        }
        //获取接口状态数据
        beat.setApis(monitorJobService.getApiStatus());
        //响应时间
        beat.setServerTime(System.currentTimeMillis());
        log.debug("<==[/getHeartBeat==心跳返回：ServerTime={},接口数量={}", beat.getServerTime(), beat
            .getApis().size());
        return ResultInfo.createSuccessResult(beat);
    }

    /*@GetMapping(value = "/getUrlsDesc")
    @ApiOperation(value = "获取接口信息集合")
    public PageResultInfo<UrlDescVo> getUrlsDesc() {
        log.debug("==[/getUrlsDesc]==>，无参数");
        List<UrlDescVo> list = monitorJobService.getUrls();
        log.debug("<==[/getUrlsDesc]==接口集合：数量={}", list.size());
        return PageResultInfo.createSuccessResult(list, 1, list.size(), Long.valueOf(list.size()));
    }*/
}

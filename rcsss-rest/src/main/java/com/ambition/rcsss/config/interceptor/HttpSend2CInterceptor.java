package com.ambition.rcsss.config.interceptor;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ambition.rcsss.model.entity.HttpSendFailedResend;
import com.ambition.rcsss.service.SendMessage2CService;
import com.ambition.rcsss.service.SpringSecurityService;

/**
 * 主动推送 重发（目前没用主动推送）
 *
 * @author ambition
 * @version $Id: HttpSend2CInterceptor.java, v 0.1 2018年1月9日 上午10:56:32 ambition Exp $
 */
@Aspect
@Order(6)
@Component
@Slf4j
public class HttpSend2CInterceptor {
    @Autowired
    HttpServletRequest    request;
    @Autowired
    SpringSecurityService springSecurityService;
    @Autowired
    SendMessage2CService  sendMessage2CService;
    private String        url    = "";          //http://192.168.2.49:8080/group/method=addOrModGroupInfo  
    private String        method = "POST";

    /**
     * http发送 (异步发送)
     *
     * @param joinPoint
     * @param rvt
     * @throws Exception
     */
    @Async("myExecutor")
    @AfterReturning(pointcut = "(@annotation(com.ambition.rcsss.common.HttpSend2C))", returning = "rvt")
    public void doAfter(JoinPoint joinPoint, Object rvt) {
        String data = JSONObject.toJSONString(rvt);
        try {
            log.error("http主动发送给c++数据====>" + data);
            JSONObject jsonObject = sendMessage2CService.httpRequest(url, method, data);
            log.error("http主动发送给c++数据成功 ,c++返回数据====>" + jsonObject.toJSONString());
        } catch (Exception e) {
            HttpSendFailedResend httpSendFailedResend = new HttpSendFailedResend();
            httpSendFailedResend.setData(data);
            httpSendFailedResend.setFlag(HttpSendFailedResend.FAIL);
            httpSendFailedResend.setGmtCreate(Calendar.getInstance().getTime());
            sendMessage2CService.save(httpSendFailedResend);
            log.error("http主动发送给c++数据失败：======" + data);
        }
    }

    /**
     * 重发
     * @throws InterruptedException 
     */
    /*@SuppressWarnings("static-access")
    @Scheduled(fixedRate = 60000)
    public void resend() throws InterruptedException {
        List<HttpSendFailedResend> httpSendFailedResendDBs = sendMessage2CService
            .getHttpSendFailedResends(HttpSendFailedResend.FAIL);
        for (HttpSendFailedResend httpSendFailedResend : httpSendFailedResendDBs) {
            String data = httpSendFailedResend.getData();
            Thread.currentThread().sleep(100);
            try {
                log.error("重发 http主动发送给c++数据====>" + data);
                JSONObject jsonObject = sendMessage2CService.httpRequest(url, method, data);
                httpSendFailedResend.setFlag(HttpSendFailedResend.SUCCESS);
                log.error("重发  http主动发送给c++数据成功 ,c++返回数据====>" + jsonObject.toJSONString());
            } catch (Exception e) {
                httpSendFailedResend.setFlag(HttpSendFailedResend.FAIL);
                log.error("重发 http主动发送给c++数据失败：======" + data);
            }
            sendMessage2CService.update(httpSendFailedResend);
        }

    }*/
}
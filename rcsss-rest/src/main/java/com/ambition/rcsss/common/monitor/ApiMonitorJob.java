package com.ambition.rcsss.common.monitor;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ambition.rcsss.service.MonitorJobService;

/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.common.monitor, v 0.1 2017/11/22 11:08 hhu Exp $$
 */
@Slf4j
@Component
public class ApiMonitorJob {
    @Resource
    private MonitorJobService monitorJobService;

    /**
     * 监控任务
     */
   // @Scheduled(fixedRate = 10 * 1000)
    public void check() {
        long startTime = System.currentTimeMillis();
        log.debug("接口状态监控任务:开始……");
        //1、更新已有部分表信息
        monitorJobService.updateTableStats();
        //2、检测新增表部分
        monitorJobService.addNewTable();
        log.debug("接口状态监控任务:完成，共花费{}ms", (System.currentTimeMillis() - startTime));
    }

    /**
     * 统计任务
     */
  //  @Scheduled(initialDelay = 5000, fixedRate = 10 * 1000)
    public void compute() {
        long startTime = System.currentTimeMillis();
        log.debug("接口状态计算任务:开始……");
        monitorJobService.updateApiDb();
        log.debug("接口状态计算任务:完成，共花费{}ms", (System.currentTimeMillis() - startTime));
    }
}
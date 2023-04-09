package com.ambition.rcsss.service.impl;

import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.ambition.rcsss.common.monitor.UseTable;
import com.ambition.rcsss.dao.MonitorJobDao;
import com.ambition.rcsss.model.entity.RcsssTableStatsEntity;
import com.ambition.rcsss.model.pojo.sendmessage2c.consult.ApiStatusVo;
import com.ambition.rcsss.model.pojo.sendmessage2c.consult.UrlDescVo;
import com.ambition.rcsss.service.MonitorJobService;
import com.ambition.rcsss.utils.Sha256Utils;

/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.service.impl, v 0.1 2017/11/22 16:25 hhu Exp $$
 */
@Slf4j
@Service
public class MonitorJobServiceImpl extends BaseService implements MonitorJobService {
    private final List<UrlDescVo>                  urlDescList  = new CopyOnWriteArrayList<>();
    @Resource
    private MonitorJobDao                          monitorJobDao;
    @Resource
    private RequestMappingHandlerMapping           requestMappingHandlerMapping;
    private ConcurrentHashMap<String, ApiStatusVo> apiStatusMap = new ConcurrentHashMap<String, ApiStatusVo>();
    private Map<String, String[]>                  apiTableMap  = new HashMap<>();
    private volatile boolean                       isFinish     = false;

    @Override
    public void updateTableStats() {
       /* List<RcsssTableStatsEntity> dbTableList = monitorJobDao
            .findAll(RcsssTableStatsEntity.class);
        for (RcsssTableStatsEntity table : dbTableList) {
            RcsssTableStatsEntity nowTable = monitorJobDao.getTableStatsNow(table.getTableName());
            //1、判断表结构是否更新
            if (nowTable.getLastColsUpdate() != null
                && table.getLastColsUpdate().before(nowTable.getLastColsUpdate())) {
                table.setLastColsUpdate(nowTable.getLastColsUpdate());
                table.setCounter(table.getCounter() + 1);
                log.debug("接口监控任务，表{}结构发生变化", table.getTableName());
            }
            //2、判断表数据是否做更新操作
            if (nowTable.getLastRowUpdate() != null
                && table.getLastRowUpdate().before(nowTable.getLastRowUpdate())) {
                table.setLastRowUpdate(nowTable.getLastRowUpdate());
                table.setCounter(table.getCounter() + 1);
                log.debug("接口监控任务，表{}数据内容发生更新", table.getTableName());
            }
            //3、判断表数据是否做增删操作
            if (!table.getLastNumRows().equals(nowTable.getLastNumRows())) {
                table.setLastNumRows(nowTable.getLastNumRows());
                table.setCounter(table.getCounter() + 1);
                log.debug("接口监控任务，表{}数据发生新增或删除", table.getTableName());
            }
            monitorJobDao.update(table);
        }*/
    }

    @Override
    public void addNewTable() {
        /*log.debug("接口监控任务:开始扫描是否有新增含有gmt_mod列的表");
        monitorJobDao.insertNewTable();*/
    }

    @Override
    public void updateApiDb() {
        /*List<RcsssTableStatsEntity> statsEntityList = monitorJobDao
            .findAll(RcsssTableStatsEntity.class);
        log.debug("接口状态计算任务:统计出表和更新次数结构集合");
        Map<String, Long> tableCounterMap = new HashMap<>(statsEntityList.size());
        for (RcsssTableStatsEntity entity : statsEntityList) {
            tableCounterMap.put(entity.getTableName(), entity.getCounter());
        }
        //统计出spring中接口和表对应关系
        makeApiTableRelation();
        apiCompute(tableCounterMap);*/
    }

    @Override
    public List<ApiStatusVo> getApiStatus() {
       /* //遍历存到List返回
        List<ApiStatusVo> list = new ArrayList<>(apiStatusMap.size());
        for (ApiStatusVo apiStatusVo : apiStatusMap.values()) {
            list.add(apiStatusVo);
        }
        return list;*/
        return  new ArrayList<>();
    }

    @Override
    public String getApiStatusByApi(String apiKey) {
        /*ApiStatusVo apiStatusVo = apiStatusMap.get(apiKey);
        return StringUtils.isNotEmpty(apiStatusVo.getStatus()) ? apiStatusVo.getStatus() : "";*/
        return "";
    }

    @Override
    public List<UrlDescVo> getUrls() {
        return urlDescList;
    }

    /**
     * 循环找出程序中配置的api - table关系
     */
    private void makeApiTableRelation() {
        log.debug("接口状态计算任务:开始收集接口和表关系……");
        //依赖spring requestMappingHandlerMapping寻找存储的url  method关系
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
            .getHandlerMethods();
        //遍历解析
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod method = entry.getValue();
            //url请求路径
            String[] urls = new String[1];
            info.getPatternsCondition().getPatterns().toArray(urls);
            //接口url为空或者apiTableMap中已经含有对应的接口信息，就不在需要收集
            if (StringUtils.isEmpty(urls[0]) || apiTableMap.containsKey(urls[0])) {
                continue;
            }
            //找到UseTable注解
            UseTable useTable = method.getMethod().getAnnotation(UseTable.class);
            if (useTable != null) {
                apiTableMap.put(urls[0], useTable.tables());
            }

            if (!isFinish && StringUtils.isNotEmpty(urls[0])) {
                UrlDescVo urlDescVo = new UrlDescVo();
                urlDescVo.setUrl(urls[0]);
                ApiOperation apiOperation = method.getMethod().getAnnotation(ApiOperation.class);
                urlDescVo.setDesc("");
                if (apiOperation != null) {
                    urlDescVo.setDesc(apiOperation.value());
                }
                urlDescList.add(urlDescVo);
            }
        }
        isFinish = true;
    }

    /**
     * 计算出接口状态值
     *
     * @param tableCounterMap 表更新次数集合
     */
    private void apiCompute(Map<String, Long> tableCounterMap) {
        if (tableCounterMap == null) {
            log.error("接口状态计算任务:没有提供表更新数据记录集合!");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String[]> entry : apiTableMap.entrySet()) {
            builder.setLength(0);
            for (String table : entry.getValue()) {
                Long count = tableCounterMap.get(table);
                //没获取到表修改统计信息
                if (count == null) {
                    log.error("接口状态计算任务:未获取到表{}更新统计信息", table);
                    count = 0L;
                }
                builder.append(table).append("-").append(count).append("|");
            }
            ApiStatusVo statusVo = new ApiStatusVo(entry.getKey(),
                Sha256Utils.encryptSha256(builder.toString()));
            apiStatusMap.put(entry.getKey(), statusVo);
        }
    }
}
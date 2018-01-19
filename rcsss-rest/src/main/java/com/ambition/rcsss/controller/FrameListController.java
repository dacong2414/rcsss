package com.ambition.rcsss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.FrameListService;

/**
 * 列表框架
 *
 * @author ambition
 * @version $Id: FrameListController.java, v 0.1 2017年10月24日 上午10:35:01 ambition Exp $
 */
@RestController
@RequestMapping("/frame")
@Api(tags = "FrameListController", description = "列表框架类")
@Slf4j
public class FrameListController {
    @Autowired
    FrameListService frameListService;

    @RequestMapping(value = "/get/frameList", method = { RequestMethod.POST })
    @ApiOperation(value = "获取列表数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "frameName", value = "框架xml名称,必填", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "curPage", value = "当前页码,必填", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "queryObj", value = "查询条件{'u_id':1}参数是json串必须在xml设置queryType", required = false, paramType = "query", dataType = "body"),
            @ApiImplicitParam(name = "arguments", value = "参数数组如 1,'admin' 是字符串记得加单引号", required = false, paramType = "query", dataType = "String") })
    public ResultInfo<HashMap<String, Object>> getDataList(String frameName, Integer curPage,
                                                           String queryObj, String[] arguments) {
        log.debug("==[/frame/get/frameList]==>参数：", frameName, curPage, queryObj, arguments);
        if (frameName == null || curPage == null) {
            throw new ProcessException(CodeEnum.ERROR_5017);
        }
        return ResultInfo.createSuccessResult(frameListService.getDataList(frameName.trim(),
            curPage, queryObj, arguments));
    }
}

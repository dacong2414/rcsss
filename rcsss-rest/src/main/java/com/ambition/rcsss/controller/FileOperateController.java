/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ambition.rcsss.common.HttpSend2C;
import com.ambition.rcsss.common.NeedResubmitCheck;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.UpdateConfig;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.UpdateConfigService;

/**
 * 文件管理(上传更新、下载)
 * @author ambition
 * @version $Id: UserinfoController.java, v 0.1 2017年8月16日 上午10:34:21 ambition Exp $
 */
@RestController
@RequestMapping("/file")
@Api(tags = "FileOperateController", description = "文件管理(上传更新、下载)")
@Slf4j
public class FileOperateController {
    @Autowired
    UpdateConfigService updateConfigService;

    @RequestMapping(value = "/method=getLastVersion", method = { RequestMethod.POST })
    @ApiOperation(value = "获取最新的版本")
    @ApiImplicitParams(value = {})
    public ResultInfo<UpdateConfig> toUpload() {
        log.debug("==[/file/method=getLastVersion]==>参数：");
        UpdateConfig updateConfig = updateConfigService.getNewestUpdateConfig();
        log.debug("==[/file/method=getLastVersion]==>获取数据：" + updateConfig);
        return ResultInfo.createSuccessResult(updateConfig);

    }

    @RequestMapping(value = "/method=upload", method = { RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ApiOperation(value = "上传文件")
    @NeedResubmitCheck
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "file", value = "上传文件", required = true, paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "forceType", value = "强制更新类型", required = true, paramType = "query", dataType = "int") })
    @HttpSend2C
    public ResultInfo<String> upload(UpdateConfig updateConfig, MultipartFile file) {
        log.debug("==[/file/method=upload]==>参数：", updateConfig, file);
        if (updateConfig == null || file == null) {
            throw new ProcessException(CodeEnum.ERROR_5015);
        }
        updateConfigService.addUpdateConfig(updateConfig, file);
        return ResultInfo.createSuccessResult("上传文件成功");
    }

    @RequestMapping(value = "/method=download", method = { RequestMethod.GET })
    @ApiOperation(value = "下载文件")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType = "query", dataType = "string") })
    public void download(String fileName) throws Exception {
        log.debug("==[/file/method=download]==>参数：", fileName);
        if (fileName == null) {
            throw new ProcessException(CodeEnum.ERROR_5016);
        }
        updateConfigService.download(fileName);
    }
}

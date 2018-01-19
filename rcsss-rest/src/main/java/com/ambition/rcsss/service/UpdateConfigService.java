/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.service;

import org.springframework.web.multipart.MultipartFile;

import com.ambition.rcsss.model.entity.UpdateConfig;

/**
 * 更新文件服务（自动更新）
 * @author ambition
 * @version $Id: UpdateConfigService.java, v 0.1 2017年8月31日 下午2:41:56 ambition Exp $
 */
public interface UpdateConfigService {

    /**
     * 获取最新文件配置
     * @return
     */
    UpdateConfig getNewestUpdateConfig();

    /**
     *  处理上传文件
     * @param updateConfig
     * @param  file
     */
    Boolean addUpdateConfig(UpdateConfig updateConfig, MultipartFile file);

    /**
     * 下载文件
     * @param fileName
     */
    void download(String fileName);

}

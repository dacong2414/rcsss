package com.ambition.rcsss.service.impl;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ambition.rcsss.common.frame.FileOperateUtil;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.UpdateConfigDao;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.entity.UpdateConfig;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.UpdateConfigService;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoServiceImpl.java, v 0.1 2017年8月17日 上午10:11:45 ambition Exp $
 */
@Service
public class UpdateConfigServiceImpl extends BaseService implements UpdateConfigService {
    @Autowired
    UpdateConfigDao     updateConfigDao;
    @Autowired
    MysqlDaoSupport     mysqlDaoSupport;
    @Autowired
    HttpServletRequest  request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    FileOperateUtil     fileOperateUtil;

    /** 
     * @return
     * @see com.ambition.rcsss.service.UpdateConfigService#getLastUpdateConfig()
     */
    @Override
    public UpdateConfig getNewestUpdateConfig() {
        return updateConfigDao.getNewestUpdateConfig();
    }

    /** 
     * @param updateConfig
     * @return
     * @see com.ambition.rcsss.service.UpdateConfigService#addUpdateConfig(com.ambition.rcsss.model.entity.UpdateConfig)
     */
    @Override
    public Boolean addUpdateConfig(UpdateConfig updateConfig, MultipartFile file) {
        Calendar calendar = Calendar.getInstance();
        String fName = file.getOriginalFilename();
        String string = fName.substring(0, (fName.length() - 4));
        String[] updateVersion = string.split("_");
        if (updateVersion.length != 3) {
            return null;
        }
        //设置文件配置
        UpdateConfig updateConf = new UpdateConfig();
        updateConf.setForceType(updateConfig.getForceType());
        updateConf.setSysInfo(updateVersion[1]);
        updateConf.setGmtCreate(calendar.getTime());
        updateConf.setUpdateType(updateVersion[0]);
        updateConf.setUpdateVersion(updateVersion[2]);
        try {
            fileOperateUtil.upload(file, updateConf);
        } catch (Exception e) {
            throw new ProcessException(CodeEnum.ERROR_30002);
        }
        mysqlDaoSupport.save(updateConf);
        return true;
    }

    /** 
     * @param fileName
     * @see com.ambition.rcsss.service.UpdateConfigService#download(java.lang.String)
     */
    @Override
    public void download(String fileName) {
        try {
            fileOperateUtil.download(request, response, fileName);
        } catch (Exception e) {
            throw new ProcessException(CodeEnum.ERROR_30003);
        }
    }
}

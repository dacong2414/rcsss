package com.ambition.rcsss.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.SysResourcesDao;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.SysResources;
import com.ambition.rcsss.service.SysResourcesService;

/**
 * 资源服务实现
 * @author wxh
 * @version $Id: SysResourcesServiceImpl.java, v 0.1 2017年8月17日 上午10:11:45 ambition Exp $
 */
@Service
public class SysResourcesServiceImpl extends BaseService implements SysResourcesService {
    @Resource
    private SysResourcesDao sysResourcesDao;

    @Resource
    private MysqlDaoSupport mysqlDaoSupport;

    @Override
    public void insertSysResource(SysResources sysResources) {
        // 插入资源信息
        mysqlDaoSupport.save(sysResources);
    }

    @Override
    public void updateSysResource(SysResources sysResources) {
        Calendar calendar = Calendar.getInstance();
        SysResources resourceDB = sysResourcesDao.getSysResourceByResourceId(sysResources
            .getResourceId());
        // 系统资源类型为0，才可以修改
        if (IGlobalConstant.SYS_FLAG_NOT_DEFAULT.equals(resourceDB.getSysFlag())) {
            resourceDB.setResourceType(sysResources.getResourceType());
            resourceDB.setResourceName(sysResources.getResourceName());
            resourceDB.setResourceDesc(sysResources.getResourceDesc());
            resourceDB.setResourcePath(sysResources.getResourcePath());
            resourceDB.setPriority(sysResources.getPriority());
            resourceDB.setUseFlag(sysResources.getUseFlag());
            resourceDB.setSysFlag(sysResources.getSysFlag());
            resourceDB.setGmtMod(calendar.getTime());
            // 更新资源信息
            mysqlDaoSupport.update(resourceDB);
        }

    }

    @Override
    public SysResources getSysResourceByResourceId(Long resourceId) {
        return sysResourcesDao.getSysResourceByResourceId(resourceId);
    }

    @Override
    public void deleteSysResource(Long resourceId) {
        // 删除角色资源关系关联
        sysResourcesDao.deleteSysRoleResourceByResourcId(resourceId);
        // 删除资源信息
        sysResourcesDao.deleteSysResourceByResourceIdAndField(resourceId);
    }

    @Override
    public boolean checkResourceName(String resourceName, Long resourceId) {
        SysResources resourceDB = sysResourcesDao.getSysResourceByResourceName(resourceName);
        if (resourceDB != null && !resourceDB.getResourceId().equals(resourceId)) {
            // 不是自己，资源名称重名不可以使用
            return false;
        }
        return true;
    }

    /** 
     * @return
     * @see com.ambition.rcsss.service.SysResourcesService#getAllResources()
     */
    @Override
    public List<SysResources> getAllUrlResources() {
        return sysResourcesDao.getUrlResources();
    }
}

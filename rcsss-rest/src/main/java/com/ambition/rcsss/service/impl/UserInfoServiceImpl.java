package com.ambition.rcsss.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambition.rcsss.dao.BaseConfigDao;
import com.ambition.rcsss.dao.LogonInfoDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.PropertyInfoDao;
import com.ambition.rcsss.dao.SysResourcesDao;
import com.ambition.rcsss.dao.SysRolesDao;
import com.ambition.rcsss.dao.UserGroupDao;
import com.ambition.rcsss.dao.UserInfoDao;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.GroupCustomRelational;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.MonitorClientMapping;
import com.ambition.rcsss.model.entity.PropertyInfo;
import com.ambition.rcsss.model.entity.SysRoles;
import com.ambition.rcsss.model.entity.SysUsersRoles;
import com.ambition.rcsss.model.entity.UserDefined;
import com.ambition.rcsss.model.entity.UserGroup;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.service.UserInfoService;
import com.ambition.rcsss.utils.DigestPass;
import com.ambition.rcsss.utils.StringUtils;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoServiceImpl.java, v 0.1 2017年8月17日 上午10:11:45 ambition Exp $
 */
@Service
public class UserInfoServiceImpl extends BaseService implements UserInfoService {
    @Autowired
    UserInfoDao     userInfoDao;
    @Autowired
    LogonInfoDao    logonInfoDao;
    @Autowired
    UserGroupDao    userGroupDao;
    @Autowired
    PropertyInfoDao propertyInfoDao;
    @Autowired
    SysRolesDao     sysRolesDao;
    @Autowired
    SysResourcesDao sysResourcesDao;
    @Autowired
    MysqlDaoSupport mysqlDaoSupport;
    @Autowired
    BaseConfigDao   baseConfigDao;

    /** 
     * @param title
     * @param description
     * @param loginName
     * @param loginPwd
     * @param roleId
     * @param groupFullName
     * @return
     * @see com.ambition.rcsss.service.UserInfoService#addUserInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
     */
    @Override
    public Boolean addOrModUserInfo(Long uId, String title, String description, String loginName,
                                    String loginPwd, Long[] roleId, Long groupId, Long userType) {
        Calendar calendar = Calendar.getInstance();
        UserInfo userInfo = userInfoDao.getUserInfoByUID(uId);
        LogonInfo logonInfo = logonInfoDao.getLoginInfoByUID(uId);
        if (userInfo == null && logonInfo == null) {
            userInfo = new UserInfo();
            userInfo.setDescription(description);
            userInfo.setTitle(title);
            userInfo.setDisableFlag(IGlobalConstant.ENABLED);
            userInfo.setGmtCreate(calendar.getTime());
            userInfo.setUserType(userType);
            mysqlDaoSupport.save(userInfo);
            logonInfo = new LogonInfo();
            logonInfo.setuId(userInfo.getuId());
            logonInfo.setLoginName(loginName);
            logonInfo.setLoginPwd(DigestPass.getDigestPassWord((loginPwd)));
            logonInfo.setDisableFlag(IGlobalConstant.ENABLED);
            logonInfo.setGmtCreate(calendar.getTime());
            mysqlDaoSupport.save(logonInfo);
            UserGroup userGroup = new UserGroup();
            userGroup.setuId(userInfo.getuId());
            userGroup.setGroupId(groupId);
            userGroup.setGmtCreate(calendar.getTime());
            mysqlDaoSupport.save(userGroup);
            if (roleId != null && roleId.length > 0) {//一个用户对应多个role
                for (int i = 0; i < roleId.length; i++) {
                    SysUsersRoles sysUsersRoles = new SysUsersRoles();
                    sysUsersRoles.setRoleId(roleId[i]);
                    sysUsersRoles.setUserId(userInfo.getuId());
                    mysqlDaoSupport.save(sysUsersRoles);
                }
            }
        } else {
            userInfo.setDescription(description);
            userInfo.setTitle(title);
            userInfo.setDisableFlag(IGlobalConstant.ENABLED);
            userInfo.setGmtMod(calendar.getTime());
            userInfo.setUserType(userType);
            mysqlDaoSupport.update(userInfo);
            logonInfo.setLoginName(loginName);
            if (!StringUtils.isEmpty(loginPwd)) {
                logonInfo.setLoginPwd(DigestPass.getDigestPassWord((loginPwd)));
            }
            logonInfo.setDisableFlag(IGlobalConstant.ENABLED);
            logonInfo.setGmtMod(calendar.getTime());
            mysqlDaoSupport.update(logonInfo);
            UserGroup userGroup = userGroupDao.getUserGroupByUid(uId);
            if (userGroup != null) {
                userGroup.setGroupId(groupId);
            }
            List<SysUsersRoles> sysRolesListDB = userInfoDao.getUserAndRoleRelationByUID(uId);
            if (sysRolesListDB.size() > 0) {
                for (SysUsersRoles sysUsersRoles : sysRolesListDB) {
                    mysqlDaoSupport.delete(sysUsersRoles);
                }
            }
            if (roleId != null && roleId.length > 0) {//一个用户对应多个role
                for (int i = 0; i < roleId.length; i++) {
                    SysUsersRoles sysUsersRoles = new SysUsersRoles();
                    sysUsersRoles.setRoleId(roleId[i]);
                    sysUsersRoles.setUserId(userInfo.getuId());
                    mysqlDaoSupport.save(sysUsersRoles);
                }
            }
        }
        return true;
    }

    /** 
     * @param uId
     * @return
     * @see com.ambition.rcsss.service.UserInfoService#delUserInfo(java.lang.Long)
     */
    @Override
    public Boolean delUserInfo(Long uId) {
        Calendar calendar = Calendar.getInstance();
        UserInfo userInfo = userInfoDao.getUserInfoByUID(uId);
        LogonInfo logonInfo = logonInfoDao.getLoginInfoByUID(uId);
        UserGroup userGroup = userGroupDao.getUserGroupByUid(uId);
        List<SysUsersRoles> sysRolesListDB = userInfoDao.getUserAndRoleRelationByUID(uId);

        List<MonitorClientMapping> monitorsList = baseConfigDao.getMonitorsByuId(uId,
            SysRoles.ROLE_NAME_MONITOR_DCLIENT);
        List<MonitorClientMapping> clientList = baseConfigDao.getClientByuId(uId,
            SysRoles.ROLE_NAME_INNER_CLIENT);

        if (monitorsList.size() > 0) { //删除监控关系
            for (MonitorClientMapping monitorClientMapping : monitorsList) {
                mysqlDaoSupport.delete(monitorClientMapping);
            }
        }
        if (clientList.size() > 0) { //删除监控关系
            for (MonitorClientMapping monitorClientMapping : clientList) {
                mysqlDaoSupport.delete(monitorClientMapping);
            }
        }
        //删除组的自定义配置
        List<GroupCustomRelational> listGroupCustomRelationalsLeft = userGroupDao
            .getGroupCustomRelationalByLeftIdAndleftType(uId, GroupCustomRelational.LEFT_TYPE_UID);
        //删除组的自定义配置
        List<GroupCustomRelational> listGroupCustomRelationalsRight = userGroupDao
            .getGroupCustomRelationalByRightIdAndRightType(uId,
                GroupCustomRelational.RIGHT_TYPE_UID);
        if (listGroupCustomRelationalsLeft.size() > 0) { //删除左边
            for (GroupCustomRelational groupCustomRelational : listGroupCustomRelationalsLeft) {
                mysqlDaoSupport.delete(groupCustomRelational);
            }
        }
        if (listGroupCustomRelationalsRight.size() > 0) { //删除右边
            for (GroupCustomRelational groupCustomRelational : listGroupCustomRelationalsRight) {
                mysqlDaoSupport.delete(groupCustomRelational);
            }
        }
        if (userInfo != null) {
            userInfo.setDisableFlag(IGlobalConstant.DISABLED);
            userInfo.setGmtMod(calendar.getTime());
            mysqlDaoSupport.update(userInfo);
        }
        if (logonInfo != null) {
            logonInfo.setLoginName(logonInfo.getLoginName() + "##del##"
                                   + calendar.getTimeInMillis());
            logonInfo.setDisableFlag(IGlobalConstant.DISABLED);
            logonInfo.setGmtMod(calendar.getTime());
            mysqlDaoSupport.update(logonInfo);
        }
        if (userGroup != null) {
            mysqlDaoSupport.delete(userGroup);
        }
        if (sysRolesListDB.size() > 0) {
            for (SysUsersRoles sysUsersRoles : sysRolesListDB) {
                mysqlDaoSupport.delete(sysUsersRoles);
            }
        }
        return true;
    }

    /** 
     * @param uId
     * @param loginName
     * @return
     * @see com.ambition.rcsss.service.UserInfoService#existBoolean(java.lang.String)
     */
    @Override
    public Boolean existBoolean(String loginName, Long uId) {
        LogonInfo logonInfoDB = logonInfoDao.getLoginInfoByName(loginName);
        if (logonInfoDB != null && !logonInfoDB.getuId().equals(uId)) {//不是自己，而且登录名重复
            return false;
        }
        return true;
    }

    /** 
     * @param userDefined
     * @return
     * @see com.ambition.rcsss.service.UserInfoService#addOrModUserDefined(com.ambition.rcsss.model.entity.UserDefined)
     */
    @Override
    public Boolean addOrModUserDefined(UserDefined userDefined) {
        PropertyInfo properInfoDB = propertyInfoDao.getProperInfoByPropertyIdAndFieldId(userDefined
            .getPropertyId());
        if (properInfoDB == null) {//没有属性不能作为自定义属性
            return false;
        }
        UserDefined userDefinedDB = userInfoDao.getUserDefinedByRecId(userDefined.getRecId());
        if (userDefinedDB == null) {
            userDefinedDB = new UserDefined();//新增属性 
            userDefinedDB.setPropertyId(userDefined.getPropertyId());
            userDefinedDB.setPropertyValue(userDefined.getPropertyValue());
            userDefinedDB.setuId(userDefined.getuId());

        } else {
            userDefinedDB.setPropertyId(userDefined.getPropertyId());
            userDefinedDB.setPropertyValue(userDefined.getPropertyValue());
            userDefinedDB.setuId(userDefined.getuId());
        }
        mysqlDaoSupport.saveOrUpdate(userDefinedDB);
        return true;
    }

    /** 
     * @param uId
     * @return
     * @see com.ambition.rcsss.service.UserInfoService#getUserDefinedListByuId(java.lang.Long)
     */
    @Override
    public List<UserDefined> getUserDefinedListByuId(Long uId) {
        return userInfoDao.getUserDefinedListByuId(uId);
    }

    /** 
     * @param recId
     * @return
     * @see com.ambition.rcsss.service.UserInfoService#deleteUserDefined(java.lang.Long)
     */
    @Override
    public Boolean deleteUserDefined(Long recId) {
        UserDefined userDefinedDB = userInfoDao.getUserDefinedByRecId(recId);
        if (userDefinedDB != null) {
            mysqlDaoSupport.delete(userDefinedDB);
            return true;
        }
        return false;
    }

    /** 
     * @param uId
     * @param propertyId
     * @return
     * @see com.ambition.rcsss.service.UserInfoService#getUserDefinedListByuIdAndPropertyId(java.lang.Long, java.lang.Long)
     */
    @Override
    public UserDefined getUserDefinedListByuIdAndPropertyId(Long uId, Long propertyId) {
        return userInfoDao.getUserDefinedListByuIdAndPropertyId(uId, propertyId);
    }
}

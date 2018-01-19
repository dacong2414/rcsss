package com.ambition.rcsss.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ambition.rcsss.dao.LogonInfoDao;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.SysRolesDao;
import com.ambition.rcsss.dao.UserGroupDao;
import com.ambition.rcsss.dao.UserInfoDao;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.SysRoles;
import com.ambition.rcsss.model.entity.UserGroup;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.service.LoginService;
import com.ambition.rcsss.service.SpringSecurityService;
import com.ambition.rcsss.utils.DigestPass;
import com.ambition.rcsss.utils.StringUtils;

@Service
public class LoginServiceImpl extends BaseService implements LoginService {
    @Autowired
    LogonInfoDao          logonInfoDao;
    @Autowired
    UserInfoDao           userInfoDao;
    @Autowired
    MysqlDaoSupport       mysqlDaoSupport;
    @Autowired
    SysRolesDao           sysRolesDao;
    @Autowired
    UserGroupDao          userGroupDao;
    @Autowired
    SpringSecurityService springSecurityService;

    /** 
     * @param loginName
     * @return
     * @see com.ambition.rcsss.service.LoginService#getLogonInfo(java.lang.String)
     */
    @Override
    public LogonInfo getLogonInfo(String loginName) {
        return logonInfoDao.getLoginInfoByName(loginName);
    }

    /** 
     * @param uId
     * @return
     * @see com.ambition.rcsss.service.LoginService#getLogonInfo(java.lang.Long)
     */
    @Override
    public String getLogonInfo(Long uId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        UserInfo userInfoDB = userInfoDao.getUserInfoByUID(uId);
        LogonInfo logonInfoDB = logonInfoDao.getLoginInfoByUID(uId);
        List<SysRoles> sysRolesListDB = sysRolesDao.getSysRoleByUid(uId);
        UserGroup userGroupDB = userGroupDao.getUserGroupByUid(uId);
        if (userInfoDB != null && logonInfoDB != null && sysRolesListDB != null
            && userGroupDB != null) {
            logonInfoDB.setUserInfo(userInfoDB);
            map.put("logonInfo", logonInfoDB);
            map.put("sysRolesList", sysRolesListDB);
            map.put("userGroup", userGroupDB);
        }
        return JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);

    }

    /** 
     * @param uId
     * @param title
     * @param description
     * @param loginName
     * @param loginPwd
     * @return
     * @see com.ambition.rcsss.service.LoginService#modLogonInfo(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Boolean modLogonInfo(Long uId, String title, String description, String loginName,
                                String loginPwd) {
        UserInfo userInfoDB = userInfoDao.getUserInfoByUID(uId);
        LogonInfo logonInfoDB = logonInfoDao.getLoginInfoByUID(uId);
        if (userInfoDB != null && logonInfoDB != null) {
            logonInfoDB.setLoginName(loginName);
            if (!StringUtils.isEmpty(loginPwd)) {
                logonInfoDB.setLoginPwd(DigestPass.getDigestPassWord((loginPwd)));
            }
            userInfoDB.setDescription(description);
            userInfoDB.setTitle(title);
            mysqlDaoSupport.update(logonInfoDB);
            mysqlDaoSupport.update(userInfoDB);
            //防止修改登录人信息后  重新登录
            springSecurityService.initData(logonInfoDB);
            return true;
        }
        return false;
    }

    /** 
     * @return
     * @see com.ambition.rcsss.service.LoginService#getCurrentLogonInfos()
     */
    @Override
    public LogonInfo getCurrentLogonInfos() {
        return getCurrentLoginInfo();
    }

    /** 
     * @return
     * @see com.ambition.rcsss.service.LoginService#getAllUsers()
     */
    @Override
    public List<Object[]> getAllUsers() {
        return userInfoDao.getAllUsers();
    }

}

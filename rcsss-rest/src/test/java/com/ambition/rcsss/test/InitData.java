package com.ambition.rcsss.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.ConsultDetail;
import com.ambition.rcsss.model.entity.ConsultTotal;
import com.ambition.rcsss.model.entity.GroupInfo;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.entity.PropertyExtend;
import com.ambition.rcsss.model.entity.PropertyInfo;
import com.ambition.rcsss.model.entity.SysResources;
import com.ambition.rcsss.model.entity.SysRoles;
import com.ambition.rcsss.model.entity.SysRolesResources;
import com.ambition.rcsss.model.entity.SysUsersRoles;
import com.ambition.rcsss.model.entity.UserInfo;
import com.ambition.rcsss.utils.DigestPass;

/**
 * 
 *
 * @author ambition
 * @version $Id: InitData.java, v 0.1 2017年9月18日 上午10:30:30 ambition Exp $
 */
public class InitData {
    public static LogonInfo getLogonInfo() {
        Calendar calendar = Calendar.getInstance();

        LogonInfo logonInfo = new LogonInfo();
        logonInfo.setDisableFlag(IGlobalConstant.ENABLED);
        logonInfo.setGmtCreate(calendar.getTime());
        logonInfo.setLoginName("loginName");
        logonInfo.setLoginPwd(DigestPass.getDigestPassWord(("123456")));
        logonInfo.setUserInfo(getUserInfo());
        return logonInfo;
    }

    public static UserInfo getUserInfo() {
        Calendar calendar = Calendar.getInstance();
        Set<String> auths = new HashSet<String>();
        auths.add("testAuths1");
        auths.add("testAuths2");
        UserInfo userInfo = new UserInfo();
        userInfo.setDescription("des");
        userInfo.setAuths(auths);
        userInfo.setDisableFlag(IGlobalConstant.ENABLED);
        userInfo.setEmail("1025753999@qq.com");
        userInfo.setTitle("title");
        userInfo.setUserType(UserInfo.USERTYPE_COMMON_USER);
        userInfo.setGmtCreate(calendar.getTime());
        return userInfo;
    }

    public static GroupInfo getGroupInfo() {
        Calendar calendar = Calendar.getInstance();
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setContent("分组内容");
        groupInfo.setfGroupId(0L);
        groupInfo.setGmtCreate(calendar.getTime());
        groupInfo.setGroupId(1L);
        groupInfo.setGroupName("重庆");
        return groupInfo;
    }

    /*property_id：属性id
    property_name_en：属性英文名称（唯一）
    property_name_cn：属性中午名称
    property_desc：属性描述
    default_value：默认值
    disable_flag：使用标志0 失效 1生效
    global_flag：是否公共属性配置  0私有配置 1是公共配置 2.都是
    customize_flag：能否用户自定义 0 不能 1能
    display_order：显示优先级
    html_type：radio,select,checkbox,text*/
    public static List<PropertyInfo> getPropertyInfoList() {
        PropertyInfo propertyInfo = new PropertyInfo();
        List<PropertyExtend> list = new ArrayList<PropertyExtend>();
        List<PropertyInfo> listPropertyInfo = new ArrayList<PropertyInfo>();
        PropertyExtend propertyExtend = new PropertyExtend();
        propertyExtend.setPropertyId(1L);
        propertyExtend.setRecId(1L);
        propertyExtend.setShowName("CardName 采集卡名称1");
        propertyExtend.setShowValue("CY3014 USB1");
        PropertyExtend propertyExtend2 = new PropertyExtend();
        propertyExtend2.setPropertyId(1L);
        propertyExtend2.setRecId(1L);
        propertyExtend2.setShowName("CardName 采集卡名称1");
        propertyExtend2.setShowValue("QP0203 PCI1");
        list.add(propertyExtend);
        list.add(propertyExtend2);
        propertyInfo.setCustomizeFlag(PropertyInfo.CUSTOMIZE_FLAG_ENABLE);
        propertyInfo.setDefaultValue("defaultValue");
        propertyInfo.setDisableFlag(PropertyInfo.DISABLE_FALG_ENABLE);
        propertyInfo.setDisplayOrder(1L);
        propertyInfo.setGlobalFlag(PropertyInfo.GLOBAL_FLAG_PRIVATE);
        propertyInfo.setHtmlType(PropertyInfo.HTML_TYPE_CHECKBOX);
        propertyInfo.setPropertyDesc("属性描述");
        propertyInfo.setPropertyExtends(list);
        propertyInfo.setPropertyId(1L);
        propertyInfo.setPropertyNameCn("采集卡");
        propertyInfo.setPropertyNameEn("card33");
        listPropertyInfo.add(propertyInfo);
        return listPropertyInfo;
    }

    public static PropertyInfo getPropertyInfo() {
        PropertyInfo propertyInfo = new PropertyInfo();
        List<PropertyExtend> list = new ArrayList<PropertyExtend>();
        PropertyExtend propertyExtend = new PropertyExtend();
        propertyExtend.setPropertyId(1L);
        propertyExtend.setRecId(1L);
        propertyExtend.setShowName("CardName 采集卡名称2");
        propertyExtend.setShowValue("CY3014 USB2");
        PropertyExtend propertyExtend2 = new PropertyExtend();
        propertyExtend2.setPropertyId(1L);
        propertyExtend2.setRecId(1L);
        propertyExtend2.setShowName("CardName 采集卡名称2");
        propertyExtend2.setShowValue("QP0203 PCI2");
        list.add(propertyExtend);
        list.add(propertyExtend2);
        propertyInfo.setCustomizeFlag(PropertyInfo.CUSTOMIZE_FLAG_ENABLE);
        propertyInfo.setDefaultValue("defaultValue");
        propertyInfo.setDisableFlag(PropertyInfo.DISABLE_FALG_ENABLE);
        propertyInfo.setDisplayOrder(1L);
        propertyInfo.setGlobalFlag(PropertyInfo.GLOBAL_FLAG_PRIVATE);
        propertyInfo.setHtmlType(PropertyInfo.HTML_TYPE_CHECKBOX);
        propertyInfo.setPropertyDesc("属性描述");
        propertyInfo.setPropertyExtends(list);
        propertyInfo.setPropertyId(1L);
        propertyInfo.setPropertyNameCn("采集卡");
        propertyInfo.setPropertyNameEn("cardxxxxxxxxxxxxxxx");
        return propertyInfo;
    }

    /**
     * 初始化登录信息
     * @param  loginName 登录名称
     * @return LogonInfo 登录信息对象
     */
    public static LogonInfo getLogonInfo(String loginName) {
        LogonInfo logonInfo = new LogonInfo();
        logonInfo.setLoginName(loginName);
        logonInfo.setLoginPwd("123456");
        logonInfo.setDisableFlag(IGlobalConstant.ENABLED);
        logonInfo.setGmtCreate(Calendar.getInstance().getTime());
        return logonInfo;
    }

    /**
     *  初始化角色信息
     *  @param  roleName 角色名称
     * @return SysRoles 角色信息对象
     */
    public static SysRoles getSysRole(String roleName) {
        SysRoles sysRole = new SysRoles();
        sysRole.setRoleName(roleName);
        sysRole.setRoleDesc("院内客户端描述");
        sysRole.setUseFlag(IGlobalConstant.ENABLED);
        sysRole.setRoleIssys(IGlobalConstant.ROLE_TYPE_ISSYS);
        sysRole.setSysFlag(IGlobalConstant.SYS_FLAG_NOT_DEFAULT);
        return sysRole;
    }

    /**
     *  初始化用户角色关系信息
     * @param uId 用户ID
     * @param roleId 角色ID
     * @return SysUsersRoles 用户角色关系信息对象
     */
    public static SysUsersRoles getSysUserRole(Long uId, Long roleId) {
        SysUsersRoles sysUsersRole = new SysUsersRoles();
        sysUsersRole.setUserId(uId);
        sysUsersRole.setRoleId(roleId);
        return sysUsersRole;
    }

    /**
     *  初始化资源信息
     *  @param  resourceName 资源名称
     * @return SysResources 资源信息对象
     */
    public static SysResources getSysResource(String resourceName) {
        SysResources sysResource = new SysResources();
        sysResource.setResourceType("URL");
        sysResource.setResourceName(resourceName);
        sysResource.setResourceDesc("刷新缓存描述");
        sysResource.setResourcePath("/query/method=refrush*");
        sysResource.setPriority("优先级高");
        sysResource.setUseFlag(IGlobalConstant.ENABLED);
        sysResource.setSysFlag(IGlobalConstant.SYS_FLAG_NOT_DEFAULT);
        return sysResource;
    }

    /**
     *  初始化角色资源关系信息
     *  @param  roleId  角色ID
     *  @param  resourceId 资源ID
     * @return SysRolesResources 角色资源关系信息对象
     */
    public static SysRolesResources getSysRoleResource(Long roleId, Long resourceId) {
        SysRolesResources sysRolesResource = new SysRolesResources();
        sysRolesResource.setRoleId(roleId);
        sysRolesResource.setResourceId(resourceId);
        return sysRolesResource;
    }

    /**
     * 初始化会诊总表信息
     * @return ConsultTotal 会诊总表信息对象
     */
    public static ConsultTotal getConsultTotal() {
        ConsultTotal consultTotal = new ConsultTotal();
        consultTotal.setBillNum("25412545854455");
        consultTotal.setGmtBegin(Calendar.getInstance().getTime());
        consultTotal.setGmtEnd(getNextDay(Calendar.getInstance().getTime()));
        return consultTotal;
    }

    /**
     *  初始化会诊详情信息
     * @return ConsultDetail 会诊详情对象
     */
    public static ConsultDetail getConsultDetail() {
        ConsultDetail consultDetail = new ConsultDetail();
        consultDetail.setOpra(IGlobalConstant.CONSULT_OPERATION_TYPE_ADD);
        consultDetail.setGmtCreate(Calendar.getInstance().getTime());
        return consultDetail;

    }

    /**
     *  获取传入参数的下一天的日期
     * @param date 日期对象
     * @return Date 日期对象
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        date = calendar.getTime();
        return date;
    }
}

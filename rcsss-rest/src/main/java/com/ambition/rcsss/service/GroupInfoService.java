package com.ambition.rcsss.service;

import java.util.List;
import java.util.Map;

import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.GroupCustomRelational;
import com.ambition.rcsss.model.entity.GroupInfo;
import com.ambition.rcsss.model.pojo.sendmessage2c.CustomRelationalVo;

/**
 * 分组服务
 *
 * @author ambition
 * @version $Id: UserInfoService.java, v 0.1 2017年8月17日 上午10:05:05 ambition Exp $
 */
public interface GroupInfoService {

    /**
     * 新增或者修改groupInfo 用户分组信息
     * @param grooupInfo
     */
    public Boolean addOrModGroupInfo(GroupInfo grooupInfo);

    /**
     * 删除用户分组信息
     * @param groupId
     */
    public Boolean delGroupInfo(Long groupId);

    /**
     * 通过域Id获取
     * @param fieldId
     * @return
     */
    public List<GroupInfo> getGroupInfosByFieldId();

    /**
     * 查找父节点    父节点的父节点
     * @param fGroupId
     * @return
     */
    public List<GroupInfo> getGroupInfoByFgroupId(Long fGroupId);

    /**
     * 查找子节点
     * @param groupId
     * @return
     */
    public List<GroupInfo> getGroupInfosBygroupId(Long groupId);

    /**
     * 获取分组信息
     * @param groupId
     * @return
     */
    public GroupInfo getGroupInfo(Long groupId);

    /**
     * 新增或修改组自定义关系
     * @param listCustomRelationals
     */
    public Boolean modOrAddGroupCustomRelational(List<GroupCustomRelational> listCustomRelationals);

    /**
     * 删除自定义关系
     * @param leftId
     * @param leftType
     */
    public Boolean deleteGroupCustomRelational(Long leftId, String leftType);

    /**
     * 获取自定义关系
     * @param leftId
     * @param leftType
     * @return
     */
    public List<GroupCustomRelational> getGroupCustomRelationals(Long leftId, String leftType);

    /**
     * 设计1.有一个全局的定义:所有人可见、通组可见递归下级、同组可见不递归下级、所有人不可见  （四个只能选一个）
     *      2.自定义配置：（1）人对人    直接和上面的取并集
     *                  （2）人对组    （2.1 右边递归） 右边递归后和 1取并集
     *                             （2.2 右边不递归） 右边组内不递归和1取并集
     *                  （3）组对组      （3.1 右边递归）  （左边默认递归 ）查看传过来的人的组上级是否配置了（包括自己所在的组），如果配置了那么这个人就可以看见右边组递归后的所有人在和1取并集        
     *                             （3.2 右边不递归） 通3.1 只是 右边组不递归下级，只能是自己组的人
     *                  （4）组对人   （左边默认递归 ）查看传过来的人的组上级是否配置了（包括自己所在的组）如果配置了 那么就可以看见 右边的人 然后和1取并集 。
     * @param uId
     * @param flag-->是1中全局定义里面的一个标志
     * @return
     */
    public ResultInfo<Map<String, Object>> getCustomRelationalUids(CustomRelationalVo customRelationalVo);

}

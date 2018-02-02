package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.model.entity.GroupCustomRelational;
import com.ambition.rcsss.model.entity.GroupInfo;
import com.ambition.rcsss.model.entity.UserGroup;

public interface UserGroupDao {
    /**
     * 获取usergroup
     *
     * @param uId
     * @return
     */
    UserGroup getUserGroupByUid(Long uId);

    /**
     * 根据角色名称来获取用户列表
     * @param roleName
     * @return
     */
    List<?> getUesrInfoByRoleName(String roleName);

    /**
     * 获取所有分组信息
     *
     * @return
     */
    List<GroupInfo> getGroupInfoByFieldId();

    /**
     * 获取分组信息
     *
     * @param groupId
     * @return
     */
    GroupInfo getGroupInfoByGroupId(Long groupId);

    /**
     * 查找子节点 (比如重庆市的groupId=1，那么就要找fgroupId=1的分组信息)
     * @param groupId
     * @return
     */
    List<GroupInfo> getGroupInfoByGroupIds(Long groupId);

    /**
     * 查找父节点（比如璧山的groupId=2，fgroupId=1，重庆的groupId=1，那么我们要找的是groupId=1的）
     * @param fgroupId
     * @return
     */
    GroupInfo getGroupInfoByFgroupId(Long fgroupId);

    /**
     * 获取在这个组里的      用户和组的 关系
     * @param groupId
     * @return
     */
    List<UserGroup> getUserGroupByGroupId(Long groupId);

    /**
     * 删除对应关系
     * @param groupId
     * @return
     */
    Boolean delUserGroupByGroupId(Long groupId);

    /**
     * 通过域Id获取所有用户的分组信息
     * @param fieldId
     * @return
     */
    List<GroupInfo> getGroupInfosByFieldId();

    /**
     * 找到自己配置能看到的组  、或者人
     * @param leftId
     * @param leftType
     * @return
     */
    List<GroupCustomRelational> getGroupCustomRelationalByLeftIdAndleftType(Long leftId,
                                                                            String leftType);

    /**
     * 右边的配置
     * @param leftId
     * @param leftType
     * @return
     */
    List<GroupCustomRelational> getGroupCustomRelationalByRightIdAndRightType(Long rightId,
                                                                              String rightType);

    /**
     *  通过uId获取用户所在的组
     * @param groupId
     * @return
     */
    GroupInfo getGroupInfoByUId(Long uId);

    /**
     *
     * @return
     */
    List<UserGroup> getUserGroups();

}

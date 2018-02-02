package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.UserGroupDao;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.GroupCustomRelational;
import com.ambition.rcsss.model.entity.GroupInfo;
import com.ambition.rcsss.model.entity.UserGroup;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class UserGroupDaoImpl extends MysqlDaoSupport implements UserGroupDao {

    /** 
     * @param uId
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getUserGroupByUid(java.lang.Long)
     */
    @Override
    public UserGroup getUserGroupByUid(Long uId) {
        String[] keys = { "uId" };
        Object[] values = { uId };
        return criteriaExecuteUniqueResult(UserGroup.class, keys, values);
    }

    /** 
     * @param roleName
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getUesrInfoByRoleName(java.lang.String)
     */
    @Override
    public List<Object[]> getUesrInfoByRoleName(String roleName) {
        String sql = "SELECT ui.u_id, ui.title, ui.description, sr.role_name, ( SELECT g.group_id FROM user_group g WHERE g.u_id = ui.u_id ) AS groupId FROM user_info ui, sys_roles sr, sys_users_roles sur WHERE ui.u_id = sur.user_id AND sur.role_id = sr.role_id AND sr.use_flag = 1 AND ui.disable_flag = 1 AND sr.role_name =:role_name ";
        String[] keys = { "role_name" };
        Object[] values = { roleName };
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getGroupInfo()
     */
    @Override
    public List<GroupInfo> getGroupInfoByFieldId() {
        String[] keys = {};
        Object[] values = {};
        return criteriaExecuteList(GroupInfo.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param groupId
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getGroupInfoByGroupId(java.lang.Long)
     */
    @Override
    public GroupInfo getGroupInfoByGroupId(Long groupId) {
        String[] keys = { "groupId" };
        Object[] values = { groupId };
        return criteriaExecuteUniqueResult(GroupInfo.class, keys, values);
    }

    /** 
     * @param groupId
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getUserGroup(java.lang.Long)
     */
    @Override
    public List<GroupInfo> getGroupInfoByGroupIds(Long groupId) {
        String[] keys = { "fGroupId" };
        Object[] values = { groupId };
        return criteriaExecuteList(GroupInfo.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param groupId
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getUserGroupByFgroupId(java.lang.Long)
     */
    @Override
    public GroupInfo getGroupInfoByFgroupId(Long fgroupId) {
        String[] keys = { "groupId" };
        Object[] values = { fgroupId };
        return criteriaExecuteUniqueResult(GroupInfo.class, keys, values);
    }

    /** 
     * @param groupId
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getUserGroupByGroupId(java.lang.Long)
     */
    @Override
    public List<UserGroup> getUserGroupByGroupId(Long groupId) {
        String[] keys = { "groupId" };
        Object[] values = { groupId };
        return criteriaExecuteList(UserGroup.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param groupId
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#delUserGroupByGroupId(java.lang.Long)
     */
    @Override
    public Boolean delUserGroupByGroupId(Long groupId) {
        String sql = "DELETE FROM user_group  WHERE group_id=:group_id";
        String[] keys = { "group_id" };
        Object[] values = { groupId };
        return sqlExecuteUpdate(sql, keys, values);
    }

    /** 
     * @param fieldId
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getGroupInfosByFieldId(java.lang.Long)
     */
    @Override
    public List<GroupInfo> getGroupInfosByFieldId() {
        String[] keys = {};
        Object[] values = {};
        return criteriaExecuteList(GroupInfo.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param leftId
     * @param leftType
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getGroupCustomRelationalByLeftIdAndleftType(java.lang.Long, java.lang.String)
     */
    @Override
    public List<GroupCustomRelational> getGroupCustomRelationalByLeftIdAndleftType(Long leftId,
                                                                                   String leftType) {
        String sql = "SELECT * FROM group_custom_relational WHERE left_id=:left_id AND left_type=:left_type";
        String[] keys = { "left_id", "left_type" };
        Object[] values = { leftId, leftType };
        return sqlExecuteList(GroupCustomRelational.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @param leftId
     * @param leftType
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getGroupCustomRelationalByLeftIdAndleftType(java.lang.Long, java.lang.String)
     */
    @Override
    public List<GroupCustomRelational> getGroupCustomRelationalByRightIdAndRightType(Long rightId,
                                                                                     String rightType) {
        String sql = "SELECT * FROM group_custom_relational WHERE right_id=:right_id AND right_type=:right_type";
        String[] keys = { "right_id", "right_type" };
        Object[] values = { rightId, rightType };
        return sqlExecuteList(GroupCustomRelational.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @param uId
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getGroupInfoByUId(java.lang.Long)
     */
    @Override
    public GroupInfo getGroupInfoByUId(Long uId) {
        String sql = "SELECT gi.* FROM user_group ug,group_info gi WHERE ug.group_id=gi.group_id AND ug.u_id=:u_id";
        String[] keys = { "u_id" };
        Object[] values = { uId };
        return sqlExecuteUniqueResult(GroupInfo.class, sql, keys, values);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.dao.UserGroupDao#getGroupInfos()
     */
    @Override
    public List<UserGroup> getUserGroups() {
        String sql = "SELECT ug.* FROM user_group ug,group_info gi WHERE ug.group_id=gi.group_id ";
        String[] keys = {};
        Object[] values = {};
        return sqlExecuteList(UserGroup.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

}

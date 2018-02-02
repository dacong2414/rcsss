package com.ambition.rcsss.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambition.rcsss.common.frame.FrameListUtils;
import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.UserGroupDao;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.entity.GroupCustomRelational;
import com.ambition.rcsss.model.entity.GroupInfo;
import com.ambition.rcsss.model.entity.UserGroup;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.model.pojo.sendmessage2c.CustomRelationalVo;
import com.ambition.rcsss.service.GroupInfoService;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoServiceImpl.java, v 0.1 2017年8月17日 上午10:11:45 ambition Exp $
 */
@Service
public class GroupInfoServiceImpl extends BaseService implements GroupInfoService {
    @Autowired
    UserGroupDao            userGroupDao;
    @Autowired
    MysqlDaoSupport         mysqlDaoSupport;
    private List<GroupInfo> list   = new ArrayList<GroupInfo>();
    private Set<Object>     seeSet = new HashSet<Object>();     //存储递归下级中可以看见的人

    /** 
     * @param grooupInfo
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#addOrModGroupInfo(com.ambition.rcsss.model.entity.GroupInfo)
     */
    @Override
    public Boolean addOrModGroupInfo(GroupInfo groupInfo) {
        Calendar calendar = Calendar.getInstance();
        GroupInfo groupInfoDB = userGroupDao.getGroupInfoByGroupId(groupInfo.getGroupId());
        if (groupInfoDB == null) {
            groupInfoDB = new GroupInfo();
            groupInfoDB.setContent(groupInfo.getContent());
            groupInfoDB.setfGroupId(groupInfo.getfGroupId());
            groupInfoDB.setGmtCreate(calendar.getTime());
            groupInfoDB.setGroupName(groupInfo.getGroupName());
            groupInfoDB.setControlFlag(groupInfo.getControlFlag());

        } else {
            if (groupInfo.getGroupId().equals(groupInfo.getfGroupId())) {//分组id和父分组id 不可能相同
                throw new ProcessException(CodeEnum.ERROR_5006);
            }
            groupInfoDB.setContent(groupInfo.getContent());
            groupInfoDB.setfGroupId(groupInfo.getfGroupId());
            groupInfoDB.setGmtMod(calendar.getTime());
            groupInfoDB.setGroupName(groupInfo.getGroupName());
            groupInfoDB.setControlFlag(groupInfo.getControlFlag());

        }
        mysqlDaoSupport.saveOrUpdate(groupInfoDB);
        return true;
    }

    /**'
     * 查找父节点    父节点的父节点
     *
     * @param fGroupId
     * @return
     */

    public List<GroupInfo> findParents(Long fGroupId) {
        GroupInfo groupInfoDBFirst = userGroupDao.getGroupInfoByFgroupId(fGroupId);
        if (groupInfoDBFirst != null) {
            list.add(groupInfoDBFirst);
            if (!groupInfoDBFirst.getGroupId().equals(0L)
                && !groupInfoDBFirst.getfGroupId().equals(groupInfoDBFirst.getGroupId())) {//自己不可能是自己的父节点
                findParents(groupInfoDBFirst.getfGroupId());
            }
        }
        return list;
    }

    /** 
     * @param groupId
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#delGroupInfo(java.lang.Long)
     */
    @Override
    public Boolean delGroupInfo(Long groupId) {
        GroupInfo groupDB = userGroupDao.getGroupInfoByGroupId(groupId);
        //有子节点不能删除
        List<GroupInfo> listDB = userGroupDao.getGroupInfoByGroupIds(groupId);
        //有用户使用这个组不能删除
        List<UserGroup> listDBF = userGroupDao.getUserGroupByGroupId(groupId);
        if (groupDB != null && listDB.size() == 0 && listDBF.size() == 0) {
            mysqlDaoSupport.delete(groupDB);
            return true;
        } else {
            return false;
        }
    }

    /** 
     * @param fieldId
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#getGroupInfosByFieldId(java.lang.Long)
     */
    @Override
    public List<GroupInfo> getGroupInfosByFieldId() {
        return userGroupDao.getGroupInfosByFieldId();
    }

    /** 
     * @param fGroupId
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#getGroupInfoByFgroupId(java.lang.Long)
     */
    @Override
    public List<GroupInfo> getGroupInfoByFgroupId(Long fGroupId) {
        list.clear();//清空list
        return findParents(fGroupId);
    }

    /** 
     * @param groupId
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#getGroupInfosBygroupId(java.lang.Long)
     */
    @Override
    public List<GroupInfo> getGroupInfosBygroupId(Long groupId) {
        return userGroupDao.getGroupInfoByGroupIds(groupId);
    }

    /** 
     * @param groupId
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#getGroupInfo(java.lang.Long)
     */
    @Override
    public GroupInfo getGroupInfo(Long groupId) {
        return userGroupDao.getGroupInfoByGroupId(groupId);
    }

    /** 
     * @param listCustomRelationals
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#modOrAddGroupCustomRelational(java.util.List)
     */
    @Override
    public Boolean modOrAddGroupCustomRelational(List<GroupCustomRelational> listCustomRelationals) {
        Long leftId = null;
        String leftType = null;
        for (GroupCustomRelational groupCustomRelational : listCustomRelationals) {//找的前端传过来左边对应id  和类型
            if (groupCustomRelational.getLeftId() != null
                && groupCustomRelational.getLeftType() != null) {
                leftId = groupCustomRelational.getLeftId();
                leftType = groupCustomRelational.getLeftType();
                break;
            }
        }
        if (leftId == null || leftType == null) {
            throw new ProcessException(CodeEnum.ERROR_5044);

        }
        List<GroupCustomRelational> listDB = userGroupDao
            .getGroupCustomRelationalByLeftIdAndleftType(leftId, leftType);
        for (GroupCustomRelational groupCustomRelational : listDB) {//先删除、后新增
            mysqlDaoSupport.delete(groupCustomRelational);
        }
        Calendar calendar = Calendar.getInstance();
        for (GroupCustomRelational groupCustomRelational : listCustomRelationals) {//新增
            GroupCustomRelational groupCustomRelationalNew = new GroupCustomRelational();
            groupCustomRelationalNew.setGmtCreate(calendar.getTime());
            groupCustomRelationalNew.setLeftId(groupCustomRelational.getLeftId());
            groupCustomRelationalNew.setLeftType(groupCustomRelational.getLeftType());
            groupCustomRelationalNew.setRecursive(groupCustomRelational.getRecursive());
            groupCustomRelationalNew.setRightId(groupCustomRelational.getRightId());
            groupCustomRelationalNew.setRightType(groupCustomRelational.getRightType());
            mysqlDaoSupport.save(groupCustomRelationalNew);
        }
        return true;
    }

    /** 
     * @param leftId
     * @param leftType
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#deleteGroupCustomRelational(java.lang.Long, java.lang.String)
     */
    @Override
    public Boolean deleteGroupCustomRelational(Long leftId, String leftType) {
        List<GroupCustomRelational> listDB = userGroupDao
            .getGroupCustomRelationalByLeftIdAndleftType(leftId, leftType);
        for (GroupCustomRelational groupCustomRelational : listDB) {//删除
            mysqlDaoSupport.delete(groupCustomRelational);
        }
        return true;
    }

    /** 
     * @param leftId
     * @param leftType
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#getGroupCustomRelationals(java.lang.Long, java.lang.String)
     */
    @Override
    public List<GroupCustomRelational> getGroupCustomRelationals(Long leftId, String leftType) {
        return userGroupDao.getGroupCustomRelationalByLeftIdAndleftType(leftId, leftType);
    }

    /** 
     *  设计1.有一个全局的定义:所有人可见、通组可见递归下级、同组可见不递归下级、所有人不可见  （四个只能选一个）
     *      2.自定义配置：（1）人对人    直接和上面的取并集
     *                  （2）人对组    （2.1 右边递归） 右边递归后和 1取并集
     *                             （2.2 右边不递归） 右边组内不递归和1取并集
     *                  （3）组对组      （3.1 右边递归）  （左边默认递归 ）查看传过来的人的组上级是否配置了（包括自己所在的组），如果配置了那么这个人就可以看见右边组递归后的所有人在和1取并集        
     *                             （3.2 右边不递归） 通3.1 只是 右边组不递归下级，只能是自己组的人
     *                  （4）组对人   （左边默认递归 ）查看传过来的人的组上级是否配置了（包括自己所在的组）如果配置了 那么就可以看见 右边的人 然后和1取并集 。
     * @param flag                   
     * @param uId                   
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#getUids(java.lang.Long)
     */
    @Override
    // @NeedEhcache
    public ResultInfo<Map<String, Object>> getCustomRelationalUids(CustomRelationalVo customRelationalVo) {
        list.clear();//清空list  递归上级的容器
        seeSet.clear(); //清空  递归所有下级的集合   getChildUidsCustom
        Long uId = customRelationalVo.getUId();
        String flag = FrameListUtils.getFlag();
        Set<Object> seeSet = new HashSet<Object>();//容器装可以看见的人
        Map<String, Object> map = new HashMap<String, Object>();//用于返回数据
        GroupInfo groupInfoDB = userGroupDao.getGroupInfoByUId(uId);//查出这个用户所在的组
        if (groupInfoDB == null) {//没有组 则报错
            throw new ProcessException(CodeEnum.ERROR_5045);
        }
        Long groupId = groupInfoDB.getGroupId();
        //判断全局定义类型
        if (GroupInfo.ALL_PERSON_SEE.equals(flag)) {//所有人可见
            seeSet.addAll(getAllPersonSee());
        } else if (GroupInfo.SAME_GROUP_NO_RECURSION.equals(flag)) {//通组可见不递归
            seeSet.addAll(getSameGroupRecursion(groupId, false));
        } else if (GroupInfo.SAME_GROUP_RECURSION.equals(flag)) {//通组可见递归
            seeSet.addAll(getSameGroupRecursion(groupId, true));
        }

        //人配置人   人配置组 的情况
        List<GroupCustomRelational> GroupCustomRelationalsUserDB = userGroupDao
            .getGroupCustomRelationalByLeftIdAndleftType(uId, GroupCustomRelational.LEFT_TYPE_UID);//查出这个人自定义配置关系
        if (GroupCustomRelationalsUserDB.size() > 0) {
            for (GroupCustomRelational groupCustomRelational : GroupCustomRelationalsUserDB) {//如果自定义配置了
                if (GroupCustomRelational.RIGHT_TYPE_UID.equals(groupCustomRelational
                    .getRightType())) {//如果自定义配置的是人 
                    seeSet.add(groupCustomRelational.getRightId());
                }
                if (GroupCustomRelational.RIGHT_TYPE_GROUPID.equals(groupCustomRelational
                    .getRightType())) {//如果自定义配置的是组 
                    if (GroupCustomRelational.RECURSION
                        .equals(groupCustomRelational.getRecursive())) {
                        seeSet
                            .addAll(getLeftOrRightGroup(groupCustomRelational.getRightId(), true));
                    } else if (GroupCustomRelational.NO_RECURSION.equals(groupCustomRelational
                        .getRecursive())) {
                        seeSet
                            .addAll(getLeftOrRightGroup(groupCustomRelational.getRightId(), false));
                    }
                }
            }
        }
        //组对组、组对人的情况   左边组递归所以配了上级  下级也可以看
        List<GroupCustomRelational> groupCustomRelationalsGroupNew = new ArrayList<GroupCustomRelational>();
        List<GroupInfo> fGroupInfosListDB = findParents(groupInfoDB.getfGroupId());//找到上级
        fGroupInfosListDB.add(groupInfoDB);//加入自己
        for (GroupInfo groupInfo : fGroupInfosListDB) {//查看自己或者自己的上级有没有配置
            //组配置组  组配置人
            List<GroupCustomRelational> groupCustomRelationalsGroupDB = userGroupDao
                .getGroupCustomRelationalByLeftIdAndleftType(groupInfo.getGroupId(),
                    GroupCustomRelational.LEFT_TYPE_GROUPID);
            if (groupCustomRelationalsGroupDB != null) {
                groupCustomRelationalsGroupNew.addAll(groupCustomRelationalsGroupDB);
            }
        }
        if (groupCustomRelationalsGroupNew.size() > 0) {//如果配置了组   这里左边的组要递归（左边的组以及下级都能看到右边的人或组）  这种情况是如果左边只是上级配了  ，下级也能看到 右边的
            for (GroupCustomRelational groupCustomRelational : groupCustomRelationalsGroupNew) {
                if (GroupCustomRelational.RIGHT_TYPE_UID.equals(groupCustomRelational
                    .getRightType())) {//如果自定义右边配置的是人 
                    seeSet.add(groupCustomRelational.getRightId());
                }
                if (GroupCustomRelational.RIGHT_TYPE_GROUPID.equals(groupCustomRelational
                    .getRightType())) {//如果右边自定义配置的是组 
                    if (GroupCustomRelational.RECURSION
                        .equals(groupCustomRelational.getRecursive())) {
                        seeSet
                            .addAll(getLeftOrRightGroup(groupCustomRelational.getRightId(), true));
                    } else if (GroupCustomRelational.NO_RECURSION.equals(groupCustomRelational
                        .getRecursive())) {
                        seeSet
                            .addAll(getLeftOrRightGroup(groupCustomRelational.getRightId(), false));
                    }
                }
            }
            //添加左边组递归能够看同组的人  以及下级的人 (在flag为所以人不可见时) -->如果要让左边的组能够看到自己 、以及下级的人放开注释
            // seeSet.addAll(getSameGroupRecursion(groupId, true));
        }
        seeSet.remove(uId); //排除自己
        map.put("uIds", seeSet);
        return ResultInfo.createSuccessResult(map);
    }

    /**
     * 所有人可见  集合
     *
     * @return
     */
    public Set<Object> getAllPersonSee() {
        Set<Object> seeSet = new HashSet<Object>();//用于返回数据
        //查出groupInfo中所有人可见的    组
        List<UserGroup> userGroupDB = userGroupDao.getUserGroups();
        for (UserGroup userGroup : userGroupDB) {
            seeSet.add(userGroup.getuId());
        }
        return seeSet;

    }

    /**
     * 左边组递归的集合和右边组递归的集合(注意--》左边组默认递归 所以isRecursion传true，右边根据情况传参数)
     *
     * @param groupId
     * @return
     */
    public Set<Object> getLeftOrRightGroup(Long groupId, Boolean isRecursion) {
        Set<Object> setRecursion = getSameGroupRecursion(groupId, isRecursion);//左边默认递归
        return setRecursion;
    }

    /**
     * 同组可见    isRecursion--》如果递归传true  不递归传false
     *
     * @param isRecursion 递归传true
     * @param groupId
     * @return
     */
    public Set<Object> getSameGroupRecursion(Long groupId, Boolean isRecursion) {
        Set<Object> seeSet = new HashSet<Object>();//用于返回数据
        //查出groupInfo中所有人可见的    组
        List<UserGroup> userGroupDB = userGroupDao.getUserGroupByGroupId(groupId);
        for (UserGroup userGroup : userGroupDB) {
            seeSet.add(userGroup.getuId());
        }
        if (isRecursion) {
            Set<Object> child = getChildUidsCustom(groupId);//找到对应组的下级
            seeSet.addAll(child);
        }
        return seeSet;
    }

    /**
     * 递归所有下级的集合
     *
     * @param groupId
     * @return
     */
    public Set<Object> getChildUidsCustom(Long groupId) {
        List<GroupInfo> listGroupInfoDB = userGroupDao.getGroupInfoByGroupIds(groupId);
        for (GroupInfo groupInfo : listGroupInfoDB) {
            /*if (GroupInfo.ALL_PERSON_NO_SEE.equals(groupInfo.getControlFlag())) {//如果组是所有人不可见 就不执行 getChildUidsCustom(groupInfo.getGroupId())
                continue;//由于自定义的优先级低于组内的同组不可见 8 的控制权限
            }
            if (isCustom && GroupInfo.SAME_GROUP_NO_RECURSION.equals(groupInfo.getControlFlag())) {
                continue;//由于没有自定义配置  所以要判断组里的权限  isCustom==true 如果是同组可见不递归 ，就不执行 getChildUidsCustom
            }*/
            List<UserGroup> userGroupsListDB = userGroupDao.getUserGroupByGroupId(groupInfo
                .getGroupId());
            for (UserGroup userGroup : userGroupsListDB) {
                seeSet.add(userGroup.getuId());
            }
            getChildUidsCustom(groupInfo.getGroupId());
        }
        return seeSet;
    }

    /** 
     * @param customRelationalVo
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#setFlag2Session(com.ambition.rcsss.model.pojo.sendmessage2c.CustomRelationalVo)
     */
    @Override
    public void setFlag(String flag) {
        FrameListUtils.setFlag(flag);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.service.GroupInfoService#getFlag()
     */
    @Override
    public String getFlag() {
        return FrameListUtils.getFlag();
    }

}

package com.ambition.rcsss.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.PropertyInfoDao;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.entity.PropertyExtend;
import com.ambition.rcsss.model.entity.PropertyInfo;
import com.ambition.rcsss.model.entity.RegularLinkage;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.PropertyInfoService;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoServiceImpl.java, v 0.1 2017年8月17日 上午10:11:45 ambition Exp $
 */
@Service
public class PropertyInfoServiceImpl extends BaseService implements PropertyInfoService {
    @Autowired
    PropertyInfoDao propertyInfoDao;

    @Autowired
    MysqlDaoSupport mysqlDaoSupport;

    /** 
     * @param propertyInfo
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#addOrModPropertyInfo(com.ambition.rcsss.model.entity.PropertyInfo)
     */
    @Override
    public Boolean addOrModPropertyInfo(List<PropertyInfo> propertyInfos) {
        Calendar calendar = Calendar.getInstance();
        for (PropertyInfo propertyInfo : propertyInfos) {
            PropertyInfo propertyInfoDB = propertyInfoDao
                .getProperInfoByPropertyIdAndFieldId(propertyInfo.getPropertyId());
            List<PropertyExtend> properExtends = propertyInfo.getPropertyExtends();
            if (propertyInfoDB == null) {//一起提交）
                propertyInfoDB = new PropertyInfo();
                propertyInfoDB.setCustomizeFlag(propertyInfo.getCustomizeFlag());//能否用户自定义 0 不能 1能
                propertyInfoDB.setDefaultValue(propertyInfo.getDefaultValue());//默认值
                propertyInfoDB.setDisableFlag(propertyInfo.getDisableFlag());//使用标志0 失效 1生效
                propertyInfoDB.setDisplayOrder(propertyInfo.getDisplayOrder());//显示优先级
                propertyInfoDB.setGlobalFlag(propertyInfo.getGlobalFlag());//是否公共属性配置  0私有配置 1是公共配置 2.都是
                propertyInfoDB.setHtmlType(propertyInfo.getHtmlType());//radio,select,checkbox,text
                propertyInfoDB.setPropertyNameCn(propertyInfo.getPropertyNameCn());//属性中午名称
                propertyInfoDB.setPropertyNameEn(propertyInfo.getPropertyNameEn());//属性英文名称（唯一）
                propertyInfoDB.setPropertyDesc(propertyInfo.getPropertyDesc());
                propertyInfoDB.setTagType(propertyInfo.getTagType());
                propertyInfoDB.setGmtCreate(calendar.getTime());
                mysqlDaoSupport.save(propertyInfoDB);
                List<PropertyExtend> listproExtendsDB = propertyInfoDao
                    .getPropertyExtendsByPropertyId(propertyInfoDB.getPropertyId());
                if (listproExtendsDB != null && listproExtendsDB.size() > 0) {//如果有 删除扩展项
                    for (PropertyExtend propertyExtend : listproExtendsDB) {
                        mysqlDaoSupport.delete(propertyExtend);
                    }
                }
                List<RegularLinkage> regularLinkagesDB = propertyInfoDao
                    .getRegularLinkageByPropertyId(propertyInfoDB.getPropertyId());
                if (regularLinkagesDB != null && regularLinkagesDB.size() > 0) {//如果联动 删除联动项
                    for (RegularLinkage regularLinkage : regularLinkagesDB) {
                        mysqlDaoSupport.delete(regularLinkage);
                    }
                }
                if (properExtends != null && properExtends.size() > 0) {//在保存扩展项  和联动项  select的联动
                    for (PropertyExtend propertyExtend : properExtends) {
                        PropertyExtend PropertyExtendNew = new PropertyExtend();
                        PropertyExtendNew.setPropertyId(propertyInfoDB.getPropertyId());
                        PropertyExtendNew.setShowName(propertyExtend.getShowName());
                        PropertyExtendNew.setShowValue(propertyExtend.getShowValue());
                        mysqlDaoSupport.save(PropertyExtendNew);
                        RegularLinkage regularLinkage = new RegularLinkage();
                        regularLinkage.setfFunctionId(0L);
                        regularLinkage.setPropertyId(propertyInfoDB.getPropertyId());
                        regularLinkage.setPropertyValue(propertyExtend.getShowValue());
                        mysqlDaoSupport.save(regularLinkage);

                    }
                } else {//input的联动
                    RegularLinkage regularLinkage = new RegularLinkage();
                    regularLinkage.setfFunctionId(0L);
                    regularLinkage.setPropertyId(propertyInfoDB.getPropertyId());
                    regularLinkage.setPropertyValue(propertyInfo.getDefaultValue());
                    mysqlDaoSupport.save(regularLinkage);
                }

            } else {
                propertyInfoDB.setCustomizeFlag(propertyInfo.getCustomizeFlag());
                propertyInfoDB.setDefaultValue(propertyInfo.getDefaultValue());
                propertyInfoDB.setDisableFlag(propertyInfo.getDisableFlag());
                propertyInfoDB.setDisplayOrder(propertyInfo.getDisplayOrder());
                propertyInfoDB.setGlobalFlag(propertyInfo.getGlobalFlag());
                propertyInfoDB.setHtmlType(propertyInfo.getHtmlType());
                propertyInfoDB.setPropertyNameCn(propertyInfo.getPropertyNameCn());
                propertyInfoDB.setPropertyNameEn(propertyInfo.getPropertyNameEn());
                propertyInfoDB.setPropertyDesc(propertyInfo.getPropertyDesc());
                propertyInfoDB.setTagType(propertyInfo.getTagType());
                propertyInfoDB.setGmtMod(calendar.getTime());
                mysqlDaoSupport.save(propertyInfoDB);
                List<PropertyExtend> listproExtendsDB = propertyInfoDao
                    .getPropertyExtendsByPropertyId(propertyInfoDB.getPropertyId());
                if (listproExtendsDB != null && listproExtendsDB.size() > 0) {//如果有 删除扩展项
                    for (PropertyExtend propertyExtend : listproExtendsDB) {
                        mysqlDaoSupport.delete(propertyExtend);
                    }
                }
                List<RegularLinkage> regularLinkagesDB = propertyInfoDao
                    .getRegularLinkageByPropertyId(propertyInfoDB.getPropertyId());
                if (regularLinkagesDB != null && regularLinkagesDB.size() > 0) {//联动 删除联动项
                    for (RegularLinkage regularLinkage : regularLinkagesDB) {
                        mysqlDaoSupport.delete(regularLinkage);
                    }
                }
                if (properExtends != null && properExtends.size() > 0) {//在保存扩展项 和联动项  select的联动
                    for (PropertyExtend propertyExtend : properExtends) {
                        PropertyExtend PropertyExtendNew = new PropertyExtend();
                        PropertyExtendNew.setPropertyId(propertyInfoDB.getPropertyId());
                        PropertyExtendNew.setShowName(propertyExtend.getShowName());
                        PropertyExtendNew.setShowValue(propertyExtend.getShowValue());
                        mysqlDaoSupport.save(PropertyExtendNew);
                        RegularLinkage regularLinkage = new RegularLinkage();
                        regularLinkage.setfFunctionId(0L);
                        regularLinkage.setPropertyId(propertyInfoDB.getPropertyId());
                        regularLinkage.setPropertyValue(propertyExtend.getShowValue());
                        mysqlDaoSupport.save(regularLinkage);
                    }
                } else {//input的联动 
                    RegularLinkage regularLinkage = new RegularLinkage();
                    regularLinkage.setfFunctionId(0L);
                    regularLinkage.setPropertyId(propertyInfoDB.getPropertyId());
                    regularLinkage.setPropertyValue(propertyInfo.getDefaultValue());
                    mysqlDaoSupport.save(regularLinkage);
                }
            }
        }
        return true;
    }

    /** 
     * @param propertyId
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#delPropertyInfo(java.lang.Long)
     */
    @Override
    public Boolean delPropertyInfo(Long propertyId) {

        List<PropertyExtend> propertyExtendListDB = propertyInfoDao
            .getPropertyExtendsByPropertyId(propertyId);
        if (propertyExtendListDB.size() > 0) {
            for (PropertyExtend propertyExtend : propertyExtendListDB) {//删除扩展属性
                mysqlDaoSupport.delete(propertyExtend);
            }
        }
        return propertyInfoDao.delPropertyInfo(propertyId);//删除属性
    }

    /** 
     * @param propertyNameEn
     * @param propertyId
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#existBoolean(java.lang.String, java.lang.Long)
     */
    @Override
    public Boolean existBoolean(String propertyNameEn, Long propertyId) {
        PropertyInfo propertyInfoDB = propertyInfoDao
            .getProperInfoByPropertyNameEnAndFieldId(propertyNameEn);
        if (propertyInfoDB != null && !propertyInfoDB.getPropertyId().equals(propertyId)) {//不是自己，而且英文名重复
            return false;
        }
        return true;
    }

    /** 
     * @param propertyId
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#getPropertyInfo(java.lang.Long)
     */
    @Override
    public PropertyInfo getPropertyInfo(Long propertyId) {
        PropertyInfo propertyInfoDB = propertyInfoDao
            .getProperInfoByPropertyIdAndFieldId(propertyId);
        List<PropertyExtend> listproExtendsDB = propertyInfoDao
            .getPropertyExtendsByPropertyId(propertyId);
        if (propertyInfoDB != null) {
            propertyInfoDB.setPropertyExtends(listproExtendsDB);
        }
        return propertyInfoDB;
    }

    /** 
     * 
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#getPropertyInfoAll()
     */
    @Override
    public Map<String, Object> getPropertyInfoAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> tagTypeListDB = propertyInfoDao.getTagTypes();//获取所有的标签类型
        List<PropertyInfo> listPropertyInfoDB = propertyInfoDao.getPropertyInfoAll();
        for (String tag : tagTypeListDB) {
            List<PropertyInfo> listNew = new ArrayList<PropertyInfo>();
            for (PropertyInfo propertyInfo : listPropertyInfoDB) {
                if (tag.equals(propertyInfo.getTagType())) {
                    List<PropertyExtend> propertyExtendsListDB = propertyInfoDao
                        .getPropertyExtendsByPropertyId(propertyInfo.getPropertyId());
                    List<RegularLinkage> regularLinkagesListDB = propertyInfoDao
                        .getRegularLinkageByPropertyId(propertyInfo.getPropertyId());
                    propertyInfo.setPropertyExtends(propertyExtendsListDB);
                    propertyInfo.setRegularLinkage(regularLinkagesListDB);
                    listNew.add(propertyInfo);
                    map.put(tag, listNew);//标签为key   List<PropertyInfo>为value
                }
            }
        }
        return map;
    }

    /** 
     * @param regularLinkageList
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#deleteRegularLinkage(java.util.List)
     */
    @Override
    public Boolean deleteRegularLinkage(Long functionId) {
        List<RegularLinkage> list = findChild(functionId);//找到子联动
        //propertyInfoDao.deleteRegularLinkage(functionId);//删除自己
        for (RegularLinkage regularLinkage : list) {
            if (regularLinkage != null) {
                mysqlDaoSupport.delete(regularLinkage);
            }
        }
        return true;
    }

    /**
     * 传fFunctionId=0的functionId获取子节点
     *
     * @param functionId
     * @return
     */
    private List<RegularLinkage> findChild(Long functionId) {
        List<RegularLinkage> regularLinkageDBList = propertyInfoDao
            .getRegularLinkageByFFunctionId(functionId);
        return regularLinkageDBList;
    }

    /** 
     * @param functionId
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#getRegularLinkages(java.lang.Long)
     */
    @Override
    public List<RegularLinkage> getRegularLinkages(Long functionId) {
        List<RegularLinkage> listNew = new ArrayList<RegularLinkage>();
        List<RegularLinkage> list = findChild(functionId);//增加子节点
        for (RegularLinkage regularLinkage : list) {//联动类中在添加propertyInfo对象
            PropertyInfo propertyInfoDB = propertyInfoDao
                .getProperInfoByPropertyIdAndFieldId(regularLinkage.getPropertyId());
            if (propertyInfoDB != null) {
                List<PropertyExtend> extendListDB = propertyInfoDao
                    .getPropertyExtendsByPropertyId(regularLinkage.getPropertyId());
                propertyInfoDB.setPropertyExtends(extendListDB);
                regularLinkage.setPropertyInfo(propertyInfoDB);
                listNew.add(regularLinkage);
            }
        }
        RegularLinkage regularLinkageDB = propertyInfoDao.getRegularLinkageByFunctionId(functionId);
        listNew.add(regularLinkageDB);//增加自己
        return listNew;
    }

    /** 
     * @param functionId
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#addOrModFRegularLinkage(java.lang.Long)
     */
    @Override
    public Boolean addOrModFRegularLinkage(RegularLinkage regularLinkage) {
        RegularLinkage regularLinkageDB = propertyInfoDao
            .getRegularLinkageByFunctionId(regularLinkage.getFunctionId());
        if (regularLinkageDB == null) {
            regularLinkageDB = new RegularLinkage();
            regularLinkageDB.setfFunctionId(0L);
            regularLinkageDB.setPropertyId(regularLinkage.getPropertyId());
            regularLinkageDB.setPropertyValue(regularLinkage.getPropertyValue());
        } else {
            regularLinkageDB.setfFunctionId(0L);
            regularLinkageDB.setPropertyId(regularLinkage.getPropertyId());
            regularLinkageDB.setPropertyValue(regularLinkage.getPropertyValue());
        }
        mysqlDaoSupport.saveOrUpdate(regularLinkageDB);
        return true;
    }

    /** 
     * @param regularLinkageList
     * @param functionId
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#addOrModRegularLinkage(java.util.List, java.lang.Long)
     */
    @Override
    public Boolean addOrModRegularLinkage(List<RegularLinkage> regularLinkageList) {
        Long functionId = null;
        for (RegularLinkage regularLinkage : regularLinkageList) {
            Long fFunctionId = regularLinkage.getfFunctionId();
            if (fFunctionId != null) {
                functionId = fFunctionId;
            }
        }
        if (functionId == null) {
            throw new ProcessException(CodeEnum.ERROR_5009);
        }
        RegularLinkage regularLinkageDB = propertyInfoDao.getRegularLinkageByFunctionId(functionId);
        if (regularLinkageDB == null) {
            throw new ProcessException(CodeEnum.ERROR_5009);
        }
        List<RegularLinkage> list = findChild(functionId);
        for (RegularLinkage regularLinkage : list) {//先删除 后新增
            mysqlDaoSupport.delete(regularLinkage);
        }
        for (RegularLinkage regularLinkage : regularLinkageList) {
            RegularLinkage regularLinkageNew = new RegularLinkage();
            regularLinkageNew.setfFunctionId(regularLinkage.getfFunctionId());
            regularLinkageNew.setPropertyId(regularLinkage.getPropertyId());
            regularLinkageNew.setPropertyValue(regularLinkage.getPropertyValue());
            mysqlDaoSupport.save(regularLinkageNew);
        }
        return true;
    }

    /** 
     * @return
     * @see com.ambition.rcsss.service.PropertyInfoService#getRegularLinkagesAll()
     */
    @Override
    public List<RegularLinkage> getRegularLinkagesAll() {
        return propertyInfoDao.getRegularLinkagesAll();
    }

}

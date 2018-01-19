/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.ambition.rcsss.dao;

import java.util.List;

import com.ambition.rcsss.model.entity.PropertyExtend;
import com.ambition.rcsss.model.entity.PropertyInfo;
import com.ambition.rcsss.model.entity.RegularLinkage;

/**
 *
 * @author ambition
 * @version $Id: PropertyInfoDao.java, v 0.1 2017年9月14日 下午2:40:03 ambition Exp $
 */
public interface PropertyInfoDao {

    /**
     * 获取属性
     * @param propertyId
     * @return
     */
    PropertyInfo getProperInfoByPropertyIdAndFieldId(Long propertyId);

    /**
     * 获取属性扩展list
     *
     * @param propretyId
     * @return
     */

    List<PropertyExtend> getPropertyExtendsByPropertyId(Long propretyId);

    /**
     * 删除属性
     * @param propertyId
     * @return
     */
    Boolean delPropertyInfo(Long propertyId);

    /**
     * 属性英文名称获取属性信息
     * 
     * @param propertyNameEn
     */
    PropertyInfo getProperInfoByPropertyNameEnAndFieldId(String propertyNameEn);

    /**
     * 获取所有属性信息用于client_info回显
     * @return
     */
    List<PropertyInfo> getPropertyInfoAll();

    /**
     * 通过ffunctionId 父几点的id获取联动规则
     * @param functionId
     */
    List<RegularLinkage> getRegularLinkageByFFunctionId(Long functionId);

    /**
     * 删除RegularLinkage
     * @param functionId
     */
    Boolean deleteRegularLinkage(Long functionId);

    /**
     * 找到自己通过functionId
     * @param functionId
     */
    RegularLinkage getRegularLinkageByFunctionId(Long functionId);

    /**
     * 通过属性id获取联动
     * @param propertyId
     * @return
     */
    List<RegularLinkage> getRegularLinkageByPropertyId(Long propertyId);

    /**
     * 获取所有联动（客户端新增）
     * @return
     */
    List<RegularLinkage> getRegularLinkagesAll();

    /**
     * 获取所有标签类型
     * @return
     */
    List<String> getTagTypes();

}

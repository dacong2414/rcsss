package com.ambition.rcsss.service;

import java.util.List;
import java.util.Map;

import com.ambition.rcsss.model.entity.PropertyInfo;
import com.ambition.rcsss.model.entity.RegularLinkage;

/**
 * 属性服务
 *
 * @author ambition
 * @version $Id: UserInfoService.java, v 0.1 2017年8月17日 上午10:05:05 ambition Exp $
 */
public interface PropertyInfoService {

    /**
     * 新增或者修改属性
     * @param propertyInfo
     * @return
     */
    public Boolean addOrModPropertyInfo(List<PropertyInfo> propertyInfo);

    /**
     * 删除属性
     * @param propertyId
     */
    public Boolean delPropertyInfo(Long propertyId);

    /**
     * 属性英文名称是否存在
     * @param propertyNameEn
     * @param propertyId
     * @return
     */
    public Boolean existBoolean(String propertyNameEn, Long propertyId);

    /**
     * 获取属性信息
     * @param propertyId
     */
    public PropertyInfo getPropertyInfo(Long propertyId);

    /**
     * 获取所有属性信息用于client_info回显
     * @return
     */
    public Map<String, Object> getPropertyInfoAll();

    /**
     *  删除联动
     * @param regularLinkageList
     */
    public Boolean deleteRegularLinkage(Long functionId);

    /**
     * 
     * @param functionId
     */
    public List<RegularLinkage> getRegularLinkages(Long functionId);

    /**
     *新增或修改父联动信息
     * @param functionId
     */
    public Boolean addOrModFRegularLinkage(RegularLinkage regularLinkage);

    /**
     * 新增或修改子联动
     * @param regularLinkageList
     * @param functionId
     */
    public Boolean addOrModRegularLinkage(List<RegularLinkage> regularLinkageList);

    /**
     * 获取所有联动（客户端回显功能）
     * @return
     */
    public List<RegularLinkage> getRegularLinkagesAll();

}

package com.ambition.rcsss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.PropertyInfoDao;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.PropertyExtend;
import com.ambition.rcsss.model.entity.PropertyInfo;
import com.ambition.rcsss.model.entity.RegularLinkage;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class PropertyInfoDaoImpl extends MysqlDaoSupport implements PropertyInfoDao {

    /** 
     * @param propertyId
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getProperInfoByPropertyIdAndFieldId(java.lang.Long, java.lang.Long)
     */
    @Override
    public PropertyInfo getProperInfoByPropertyIdAndFieldId(Long propertyId) {
        String[] keys = { "propertyId" };
        Object[] values = { propertyId };
        return criteriaExecuteUniqueResult(PropertyInfo.class, keys, values);
    }

    /** 
     * @param propretyId
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getPropertyExtendsByPropertyId(java.lang.Long)
     */
    @Override
    public List<PropertyExtend> getPropertyExtendsByPropertyId(Long propertyId) {
        String[] keys = { "propertyId" };
        Object[] values = { propertyId };
        return criteriaExecuteList(PropertyExtend.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param propertyId
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#delPropertyInfo(java.lang.Long)
     */
    @Override
    public Boolean delPropertyInfo(Long propertyId) {
        String sql = "DELETE FROM property_info  WHERE property_id=:property_id";
        String[] keys = { "property_id" };
        Object[] values = { propertyId };
        return sqlExecuteUpdate(sql, keys, values);
    }

    /** 
     * @param propertyNameEn
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getProperInfoByPropertyNameEnAndFieldId(java.lang.Long, java.lang.Long)
     */
    @Override
    public PropertyInfo getProperInfoByPropertyNameEnAndFieldId(String propertyNameEn) {
        String[] keys = { "propertyNameEn" };
        Object[] values = { propertyNameEn };
        return criteriaExecuteUniqueResult(PropertyInfo.class, keys, values);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getPropertyInfoAll(java.lang.Long)
     */
    @Override
    public List<PropertyInfo> getPropertyInfoAll() {
        String sql = "SELECT * from property_info WHERE disable_flag=1 ORDER BY display_order DESC ";
        String[] keys = {};
        Object[] values = {};
        return sqlExecuteList(PropertyInfo.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @param functionId
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getRegularLinkageByFFunctionId(java.lang.Long)
     */
    @Override
    public List<RegularLinkage> getRegularLinkageByFFunctionId(Long functionId) {
        String[] keys = { "fFunctionId" };//子节点的   fFunctionId
        Object[] values = { functionId };//父节点的   functionId
        return criteriaExecuteList(RegularLinkage.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param functionId
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#deleteRegularLinkage(java.lang.Long)
     */
    @Override
    public Boolean deleteRegularLinkage(Long functionId) {
        String sql = "DELETE FROM regular_linkage  WHERE function_id=:function_id";
        String[] keys = { "function_id" };
        Object[] values = { functionId };
        return sqlExecuteUpdate(sql, keys, values);
    }

    /** 
     * @param functionId
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getRegularLinkageByFunctionId(java.lang.Long)
     */
    @Override
    public RegularLinkage getRegularLinkageByFunctionId(Long functionId) {
        String[] keys = { "functionId" };//父节点的   functionId
        Object[] values = { functionId };//父节点的   functionId
        return criteriaExecuteUniqueResult(RegularLinkage.class, keys, values);
    }

    /** 
     * @param propertyId
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getRegularLinkageByPropertyId(java.lang.Long)
     */
    @Override
    public List<RegularLinkage> getRegularLinkageByPropertyId(Long propertyId) {
        String[] keys = { "propertyId" };//子节点的   fFunctionId
        Object[] values = { propertyId };//父节点的   functionId
        return criteriaExecuteList(RegularLinkage.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getRegularLinkagesAll()
     */
    @Override
    public List<RegularLinkage> getRegularLinkagesAll() {
        String[] keys = {};
        Object[] values = {};
        return criteriaExecuteList(RegularLinkage.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @return
     * @see com.ambition.rcsss.dao.PropertyInfoDao#getTagTypes()
     */
    @Override
    public List<String> getTagTypes() {
        String sql = "SELECT DISTINCT tag_type FROM property_info WHERE disable_flag = 1 ";
        String[] keys = {};
        Object[] values = {};
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

}

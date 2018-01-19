package com.ambition.rcsss.dao;

import java.util.ArrayList;

/**
 *  统计超声会诊数据相关操作
 * Created by wxh on 2017/9/14.
 */
public interface CountSuperSoundConsultDao {

    /**
     *  根据会诊总表主键ID获取统计超声会诊详情集合信息
     * @param linkId 会诊总表主键ID
     * @return ArrayList 集合
     */
    ArrayList<Object[]> listSuperSoundConsultDetail(Long linkId);
}

package com.ambition.rcsss.service;

import com.ambition.rcsss.model.pojo.sendmessage2c.consult.SuperSoundConsultDetailVo;

import java.util.ArrayList;

/**
 * 统计超声会诊服务
 * Created by wxh on 2017/9/14.
 */
public interface CountSuperSoundConsultService {
    /**
     * 根据会诊总表主键ID获取统计超声会诊详情集合信息
     * @param linkId 会诊总表主键ID
     * @return ArrayList 统计超声会诊详情集合
     */
    ArrayList<SuperSoundConsultDetailVo> listSuperSoundConsultDetail(Long linkId);
}

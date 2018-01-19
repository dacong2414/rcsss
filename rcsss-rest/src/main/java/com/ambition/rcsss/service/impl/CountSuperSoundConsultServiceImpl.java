package com.ambition.rcsss.service.impl;

import com.ambition.rcsss.dao.CountSuperSoundConsultDao;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.model.pojo.sendmessage2c.consult.SuperSoundConsultDetailVo;
import com.ambition.rcsss.service.CountSuperSoundConsultService;
import com.ambition.rcsss.utils.ObjectUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 *  统计超声会诊服务实现
 * Created by wxh on 2017/9/14.
 */
@Service
public class CountSuperSoundConsultServiceImpl extends BaseService
                                               implements CountSuperSoundConsultService {

    @Resource
    private CountSuperSoundConsultDao countSuperSoundConsultDao;

    @Override
    public ArrayList<SuperSoundConsultDetailVo> listSuperSoundConsultDetail(Long linkId) {
        ArrayList<Object[]> list = countSuperSoundConsultDao.listSuperSoundConsultDetail(linkId);
        ArrayList<SuperSoundConsultDetailVo> voList = Lists.newArrayList();
        try {
            // 联合查询多个字段封装到集合中
            for (Object[] objects : list) {
                SuperSoundConsultDetailVo consultDetailVo = new SuperSoundConsultDetailVo();
                consultDetailVo.setUserName(String.valueOf(objects[0]));
                consultDetailVo.setInviterName(String.valueOf(objects[1]));
                consultDetailVo.setOperationType(ObjectUtil.objectToLong(objects[2], Long.class));
                consultDetailVo.setGmtCreate(ObjectUtil.objectToDate(objects[3], Date.class));
                voList.add(consultDetailVo);
            }
        } catch (Exception e) {
            throw new ProcessException(CodeEnum.ERROR);
        }
        return voList;
    }
}

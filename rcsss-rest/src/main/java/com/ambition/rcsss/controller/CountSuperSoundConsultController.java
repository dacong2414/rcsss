package com.ambition.rcsss.controller;

import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.ResultInfo;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.model.pojo.sendmessage2c.consult.SuperSoundConsultDetailVo;
import com.ambition.rcsss.service.CountSuperSoundConsultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 *  统计超声会诊控制类
 * Created by wxh on 2017/9/14.
 */
@RestController
@RequestMapping("/countSuperSoundConsult")
@Api(tags = "CountSuperSoundConsultController")
@Slf4j
public class CountSuperSoundConsultController {
    @Resource
    private CountSuperSoundConsultService countSuperSoundConsultService;

    @PostMapping("/get/consultDetail")
    @ApiOperation(value = "获取统计超声会诊详情", notes = "获取统计超声会诊详情")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "linkId", value = "会诊总表主键ID,必填", required = true, paramType = "query", dataType = "Long") })
    public ResultInfo<ArrayList<SuperSoundConsultDetailVo>> getConsultDetail(Long linkId) {
        log.debug("==[/countSuperSoundConsult/get/consultDetail]==>参数：linkId={}", linkId);
        // 请求参数非空校验
        if (linkId == null) {
            throw new ProcessException(CodeEnum.ERROR_40027);
        }
        // 获取统计超声会诊详情信息
        ArrayList<SuperSoundConsultDetailVo> superSoundConsultDetailList = countSuperSoundConsultService
            .listSuperSoundConsultDetail(linkId);
        log.debug("==[/countSuperSoundConsult/get/consultDetail]=={}", superSoundConsultDetailList);
        return ResultInfo.createSuccessResult(superSoundConsultDetailList);
    }
}

package com.ambition.rcsss.service;

import java.util.HashMap;

/**
 * 列表框架service
 *
 * @author zhangxi
 * @version $Id: FrameListService.java, v 0.1 2017年7月20日 上午9:54:03 zhangxi Exp $
 */
public interface FrameListService {

    /**
     * 根据查询编码获取对应列表数据
     * @param frameName 框架名称
     * @param curPage 当前页码
     * @param queryObj 查询条件json
     * @param arguments 
     * @return
     */
    public HashMap<String, Object> getDataList(String frameName, Integer curPage, String queryObj,
                                               String[] arguments);

}

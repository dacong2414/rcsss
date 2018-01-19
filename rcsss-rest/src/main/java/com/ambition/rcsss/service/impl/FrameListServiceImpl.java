package com.ambition.rcsss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ambition.rcsss.common.frame.Page;
import com.ambition.rcsss.common.frame.SqlConstantInfo;
import com.ambition.rcsss.common.frame.SqlFieldInfo;
import com.ambition.rcsss.common.frame.SqlQueryInfo;
import com.ambition.rcsss.common.frame.SqlRowData;
import com.ambition.rcsss.dao.FrameListDao;
import com.ambition.rcsss.model.common.CodeEnum;
import com.ambition.rcsss.model.common.IGlobalConstant;
import com.ambition.rcsss.model.entity.LogonInfo;
import com.ambition.rcsss.model.exception.BaseException;
import com.ambition.rcsss.model.exception.ProcessException;
import com.ambition.rcsss.service.EhCacheService;
import com.ambition.rcsss.service.FrameListService;
import com.ambition.rcsss.utils.StringUtils;

/**
 * @author zhangxi
 * @version $Id: FrameListService.java, v 0.1 2017年7月20日 上午9:52:45 zhangxi Exp $
 */
@Service
public class FrameListServiceImpl extends BaseService implements FrameListService {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(FrameListService.class);

    @Autowired
    EhCacheService              ehCacheService;
    @Autowired
    FrameListDao                frameListDao;

    /**
     * @param frameName 框架名称
     * @param curPage   当前页码
     * @param queryObj  查询条件json
     * @return
     */
    @Override
    public HashMap<String, Object> getDataList(String frameName, Integer curPage, String queryObj,
                                               String[] arguments) {

        SqlQueryInfo queryInfo = ehCacheService.getQueryBySqlId(frameName);
        //查询条件
        List<SqlFieldInfo> fieldInfos = queryInfo.getFieldInfos();
        HashMap<String, Object> retData = new HashMap<String, Object>();
        //sqlId对应查询不存在返回错误页面
        if (null == queryInfo || fieldInfos.size() == 0) {
            throw new ProcessException(CodeEnum.ERROR_90001);
        }
        //替换uId与拼接查询条件
        // newReplaceSql(queryInfo);
        replaceSql(queryInfo, request, arguments);
        //从request获取用户输入的查询条件
        String condition = getConFromRequest(fieldInfos, queryObj);
        //拼装SQL 设置page的总条数
        StringBuffer initPageSql = new StringBuffer();
        initPageSql.append("SELECT count(1) FROM ( ");
        initPageSql.append(queryInfo.getSqlDetial());
        initPageSql.append(" ) sqlutil " + condition);
        //页码数据
        Page page = new Page();
        page.setCurPage(curPage);
        page.setPerRows(page.getPerRows());
        page = frameListDao.initPage("(" + queryInfo.getSqlDetial().toString() + ") sqlutil"
                                     + condition, page);
        //获取总页数=总条数/每页显示条数
        Integer totalPage = page.getTotalRows() / page.getPerRows();
        //判断是否有余数存在，存在表示总页数=整除+1页
        Integer remainderPage = page.getTotalRows() % page.getPerRows();
        if (remainderPage > 0) {
            page.setTotalPages(totalPage + 1);
        } else {
            page.setTotalPages(totalPage);
        }
        //超过最大页数时，返回最大页
        if (page.getCurPage() > page.getTotalPages()) {
            page.setCurPage(page.getTotalPages());
        }
        //拼装SQL，分页查询使用mysql自带的limit 0,20
        StringBuffer finalSql = new StringBuffer();
        finalSql.append("SELECT ");
        for (int i = 0; i < fieldInfos.size(); i++) {
            finalSql.append(((SqlFieldInfo) fieldInfos.get(i)).getFieldName());
            if (i < fieldInfos.size() - 1) {
                finalSql.append(",");
            }
        }
        finalSql.append(" FROM (" + queryInfo.getSqlDetial() + ") sqlutil " + condition);
        //获取当前页数据
        List<?> list = frameListDao.getDataBySQL(null, finalSql.toString(), page.getPerRows(),
            page.getCurPage());
        //重新封装查询出来的数据
        List<Object> fieldDatas = new ArrayList<Object>();
        for (Object object : list) {
            Object[] fieldObjects = (Object[]) object;
            SqlRowData rowData = new SqlRowData();
            List<SqlFieldInfo> fieldData = new ArrayList<SqlFieldInfo>();
            for (int i = 0; i < fieldInfos.size(); i++) {
                SqlFieldInfo fieldInfo = (SqlFieldInfo) fieldInfos.get(i);
                SqlFieldInfo oneFieldInfo = new SqlFieldInfo();
                oneFieldInfo.setFieldId(fieldInfo.getFieldId());
                oneFieldInfo.setFieldName(fieldInfo.getFieldName());
                oneFieldInfo.setFieldType(fieldInfo.getFieldType());
                oneFieldInfo.setQueryType(fieldInfo.getQueryType());
                oneFieldInfo.setShowIndex(fieldInfo.getShowIndex());
                oneFieldInfo.setShowName(fieldInfo.getShowName());
                oneFieldInfo.setFrameName(fieldInfo.getFrameName());
                if (!IGlobalConstant.NO_CSID.equals(fieldInfo.getCsId())) {
                    oneFieldInfo.setData(getCsValue(fieldInfo.getConstantInfos(),
                        StringUtils.sqlHtml(fieldObjects[i])));
                } else {
                    oneFieldInfo.setData(StringUtils.sqlHtml(fieldObjects[i]));
                }
                fieldData.add(oneFieldInfo);
            }
            rowData.setFieldInfos(fieldData);
            fieldDatas.add(rowData);
        }
        retData.put("page", page);
        retData.put("fieldInfos", fieldInfos);
        retData.put("fieldDatas", fieldDatas);
        return retData;
    }

    /**
     * 当cs_id不等于0时，把常量ID转为对应值返给前台
     *
     * @param constantInfos
     * @param dataValue
     * @return
     */
    private String getCsValue(List<SqlConstantInfo> constantInfos, String dataValue) {
        String value = dataValue;
        for (int i = 0; i < constantInfos.size(); i++) {
            if (dataValue.equals(constantInfos.get(i).getCsValue())) {
                value = constantInfos.get(i).getCsName();
            }
        }
        return value;
    }

    /**
     * 替换uId与拼接查询条件
     *
     * @param queryInfo 查询主体
     */
    private void newReplaceSql(SqlQueryInfo queryInfo) {

        String sql = queryInfo.getSqlDetial();
        //替换SQL中的*uId*为当前登录用户的UID
        LogonInfo loginInfo = getCurrentLoginInfo();
        sql = sql.replace("##uId##", String.valueOf(loginInfo.getuId()));
        queryInfo.setSqlDetial(sql);
    }

    /**
     * 用request带过来的参数替换sql中的##xx##值，以达到动态修改内层sql取值范围的目的
     *
     * @param queryInfo 查询条件
     * @param request 查询参数
     * @throws RcsCheckedException 
     */
    private void replaceSql(SqlQueryInfo queryInfo, HttpServletRequest request, String[] arguments) {
        StringBuffer urlCon = new StringBuffer();//保存访问的url条件
        urlCon.append("frameName=" + queryInfo.getFrameName());
        String sql = queryInfo.getSqlDetial();
        //替换SQL中的*uId*为当前登录用户的UID
        List<?> sqlpices = StringUtils.myRegex(sql);
        for (int i = 0; i < sqlpices.size() && sqlpices.size() == arguments.length; i++) {
            String str = (String) sqlpices.get(i);
            String value = arguments[i];
            if (null == value || "".equals(value)) {
                throw new ProcessException(CodeEnum.ERROR_90002);
            } else {
                sql = sql.replace(str, value);
                urlCon.append("&" + str.replace("##", "") + "=" + value);
            }
        }
        request.setAttribute("urlCon", urlCon.toString());
        queryInfo.setSqlDetial(sql);
    }

    /**
     * 获取用户在页面输入的查询条件,拼装SQL并将条件置入model中用于页面回显
     *
     * @param fieldInfos
     * @return
     */
    private String getConFromRequest(List<SqlFieldInfo> fieldInfos, String queryObj)
                                                                                    throws BaseException {
        StringBuffer con = new StringBuffer();
        String str = "";
        Boolean flagBoolean = false;
        //获取前台传回的查询数据，以JSON格式存储
        if (!StringUtils.isEmpty(queryObj)) {
            con.append(" where ");
            JSONObject jsStr = JSONObject.parseObject(queryObj);
            for (int i = 0; i < jsStr.size(); i++) {
                for (SqlFieldInfo sqlFieldInfo : fieldInfos) {
                    //根据fieldName获取非空的对应value
                    if (jsStr.get(sqlFieldInfo.getFieldName()) != null) {
                        String queryValue = jsStr.get(sqlFieldInfo.getFieldName()).toString();
                        //精确查询
                        if (sqlFieldInfo.getQueryType().equals(IGlobalConstant.QUERYTYPE_EXACT)) {
                            flagBoolean = true;
                            con.append(" " + sqlFieldInfo.getFieldName() + " = '" + queryValue
                                       + "' and ");
                        }
                        //模糊查询
                        if (sqlFieldInfo.getQueryType().equals(IGlobalConstant.QUERYTYPE_FUZZY)) {
                            flagBoolean = true;
                            con.append(" " + sqlFieldInfo.getFieldName() + " like '%" + queryValue
                                       + "%' and ");
                        }
                        //精确日期查询
                        if (sqlFieldInfo.getQueryType().equals(IGlobalConstant.QUERYTYPE_DATE)) {
                            flagBoolean = true;
                            con.append(" DATE_FORMAT(" + sqlFieldInfo.getFieldName()
                                       + ",'%Y-%m-%d') ='" + queryValue + "' and ");
                        }
                        //下拉列表查询,从常量表取值
                        if (sqlFieldInfo.getQueryType().equals(IGlobalConstant.QUERYTYPE_LIST_CONS)) {
                            flagBoolean = true;
                            con.append(" " + sqlFieldInfo.getFieldName() + " = '" + queryValue
                                       + "' and ");
                        }
                        /*//下拉列表查询，从SQL中取值
                        if (sqlFieldInfo.getQueryType().equals(IGlobalConstant.QUERYTYPE_LIST_SQL)) {
                            con.append(" " + sqlFieldInfo.getFieldName() + " = '" + queryValue + "' and ");
                            sqlFieldInfo.setRetCondition1(queryValue);
                        }
                        //下拉列表查询，从SQL中取值模糊查询
                        if (sqlFieldInfo.getQueryType().equals(IGlobalConstant.QUERYTYPE_FUZZY_SQL)) {
                            con.append(" " + sqlFieldInfo.getFieldName() + " like '%" + queryValue + "%' and ");
                            sqlFieldInfo.setRetCondition1(queryValue);
                        }*/
                        //范围查询时，value是以##分割
                        //数字范围查询
                        if (sqlFieldInfo.getQueryType().equals(IGlobalConstant.QUERYTYPE_NUMRANGE)) {
                            flagBoolean = true;
                            String value1 = queryValue.split("##", -1)[0];
                            String value2 = queryValue.split("##", -1)[1];
                            if (!StringUtils.isNumeric(value1) && !StringUtils.isNumeric(value2)) {
                                throw new ProcessException(CodeEnum.ERROR_90002);
                            }
                            if (!StringUtils.isEmpty(value1)) {
                                con.append(" " + sqlFieldInfo.getFieldName() + " >= " + value1
                                           + " and ");
                            }
                            if (!StringUtils.isEmpty(value2)) {
                                con.append(" " + sqlFieldInfo.getFieldName() + " <= " + value2
                                           + " and ");
                            }
                        }
                        //时间范围查询
                        if (sqlFieldInfo.getQueryType().equals(IGlobalConstant.QUERYTYPE_TIMERANGE)) {
                            flagBoolean = true;
                            String value1 = queryValue.split("##", -1)[0];
                            String value2 = queryValue.split("##", -1)[1];
                            if (!StringUtils.isEmpty(value1)) {
                                con.append(" TO_DAYS(STR_TO_DATE('" + value1
                                           + "','%Y-%m-%d'))-TO_DAYS("
                                           + sqlFieldInfo.getFieldName() + ") <= 0 and ");
                            }
                            if (!StringUtils.isEmpty(value2)) {
                                con.append(" TO_DAYS(STR_TO_DATE('" + value2
                                           + "','%Y-%m-%d'))-TO_DAYS("
                                           + sqlFieldInfo.getFieldName() + ") >= 0 and ");
                            }
                        }
                    }
                }
                //去除结尾 "and "字段,StringBuffer 运用substring方法后得到的值为string  解决如果没有进if 就只剩where 执行下面代码后就剩下wh而报错
                if (flagBoolean) {
                    str = con.substring(0, con.length() - 4);
                }
            }
        }
        return str;
    }
}

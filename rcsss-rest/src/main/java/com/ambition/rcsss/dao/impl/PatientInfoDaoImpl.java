package com.ambition.rcsss.dao.impl;

import org.springframework.stereotype.Repository;

import com.ambition.rcsss.dao.MysqlDaoSupport;
import com.ambition.rcsss.dao.PatientInfoDao;
import com.ambition.rcsss.model.entity.PatientInfo;
import com.ambition.rcsss.model.entity.ReportInfo;
import com.ambition.rcsss.model.entity.VideoInfo;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserGroupImpl.java, v 0.1 2017年8月22日 下午2:17:34 ambition Exp $
 */
@Repository
public class PatientInfoDaoImpl extends MysqlDaoSupport implements PatientInfoDao {

    /** 
     * @param patientId
     * @return
     * @see com.ambition.rcsss.dao.PatientInfoDao#getPatientInfoByPatientId(java.lang.String)
     */
    @Override
    public PatientInfo getPatientInfoByPatientId(String patientId) {
        String[] keys = { "patientId" };
        Object[] values = { patientId };
        return criteriaExecuteUniqueResult(PatientInfo.class, keys, values);
    }

    /** 
     * @param reportId
     * @return
     * @see com.ambition.rcsss.dao.PatientInfoDao#getReportInfoByReportId(java.lang.String)
     */
    @Override
    public ReportInfo getReportInfoByReportId(String reportId) {
        String[] keys = { "reportId" };
        Object[] values = { reportId };
        return criteriaExecuteUniqueResult(ReportInfo.class, keys, values);
    }

    /** 
     * @param videoId
     * @return
     * @see com.ambition.rcsss.dao.PatientInfoDao#getVideoInfoByVideoId(java.lang.Long)
     */
    @Override
    public VideoInfo getVideoInfoByVideoId(Long videoId) {
        String[] keys = { "videoId" };
        Object[] values = { videoId };
        return criteriaExecuteUniqueResult(VideoInfo.class, keys, values);
    }

    /** 
     * @param reportId
     * @see com.ambition.rcsss.dao.PatientInfoDao#deleteVideo(java.lang.Long)
     */
    @Override
    public Boolean deleteVideo(String reportId) {
        String sql = "DELETE FROM video_info v WHERE v.report_id =:report_id";
        String[] keys = { "report_id" };
        Object[] values = { reportId };
        return sqlExecuteUpdate(sql, keys, values);
    }
}

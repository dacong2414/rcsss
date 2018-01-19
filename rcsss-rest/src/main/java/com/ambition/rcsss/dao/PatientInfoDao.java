package com.ambition.rcsss.dao;

import com.ambition.rcsss.model.entity.PatientInfo;
import com.ambition.rcsss.model.entity.ReportInfo;
import com.ambition.rcsss.model.entity.VideoInfo;

public interface PatientInfoDao {

    /**
     *
     * @param patientId
     * @return
     */
    PatientInfo getPatientInfoByPatientId(String patientId);

    /**
     *
     * @param reportId
     * @return
     */
    ReportInfo getReportInfoByReportId(String reportId);

    /**
     *
     * @param videoId
     * @return
     */
    VideoInfo getVideoInfoByVideoId(Long videoId);

    /**
     *
     * @param reportId
     */
    Boolean deleteVideo(String reportId);

}

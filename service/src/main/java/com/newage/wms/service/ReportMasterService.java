package com.newage.wms.service;

import com.newage.wms.entity.ReportMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportMasterService {

    Page<ReportMaster> getAllReports(Predicate predicate, Pageable pageRequest);

    ReportMaster createReport(ReportMaster reportMaster);

    void updateReport(ReportMaster reportMaster);

    ReportMaster findByReportName(String reportName);

    void delete(Long id);

    ReportMaster getReportById(Long id);
}

package com.newage.wms.service.impl;

import com.newage.wms.entity.ReportMaster;
import com.newage.wms.repository.ReportMasterRepository;
import com.newage.wms.service.ReportMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ReportMasterServiceImpl implements ReportMasterService {

    @Autowired
    ReportMasterRepository reportMasterRepository;

    @Override
    public Page<ReportMaster> getAllReports(Predicate predicate, Pageable pageRequest) {
        return reportMasterRepository.findAll(predicate, pageRequest);
    }

    @Override
    public ReportMaster createReport(ReportMaster reportMaster) {
        return reportMasterRepository.save(reportMaster);
    }

    @Override
    public void updateReport(ReportMaster reportMaster) {
        reportMasterRepository.save(reportMaster);
    }

    @Override
    public ReportMaster findByReportName(String reportName) {
        ReportMaster report = reportMasterRepository.findByReportName(reportName);
        return report;
    }

    @Override
    public void delete(Long id) {
        reportMasterRepository.deleteById(id);
    }

    @Override
    public ReportMaster getReportById(Long id) {
        ReportMaster report = reportMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.REPORT_DOES_NOT_EXIST.CODE,
                        ServiceErrors.REPORT_DOES_NOT_EXIST.KEY));
        return report;
    }
}


package com.newage.wms.service;

import com.newage.wms.entity.CompanyMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CompanyMasterService {

    CompanyMaster getCompanyById(Long id);

    CompanyMaster createCompany(CompanyMaster companyMaster);

    CompanyMaster updateCompany(CompanyMaster companyMaster);

    void deleteCompany(CompanyMaster companyMaster);

    Page<CompanyMaster> getAllCompanies(Predicate predicate, Pageable pageRequest);

    void validateNewCompanyCode(String code);

    void validateNewCompanyName(String name);

    void validateNewCompanyReportingName(String reportingName);

    List<CompanyMaster> getByGroupCompanyMasterId(Long groupCompanyMasterId);

}

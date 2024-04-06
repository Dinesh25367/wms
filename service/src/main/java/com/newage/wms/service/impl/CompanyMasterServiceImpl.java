package com.newage.wms.service.impl;

import com.newage.wms.entity.CompanyMaster;
import com.newage.wms.repository.CompanyMasterRepository;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Log4j2
@Service
@Transactional
public class CompanyMasterServiceImpl implements CompanyMasterService {

    private final CompanyMasterRepository companyMasterRepository;

    @Autowired
    public CompanyMasterServiceImpl(CompanyMasterRepository companyMasterRepository) {
        this.companyMasterRepository = companyMasterRepository;
    }

    /*
     * Method to get Company by Id
     * @Return Company
     */
    @Override
    public CompanyMaster getCompanyById(Long id) {
        log.info("ENTRY-EXIT - get Company by Id");
        return companyMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.COMPANY_ID_NOT_FOUND.CODE,
                        ServiceErrors.COMPANY_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to Create Company
     * @Return Company
     */
    @Override
    public CompanyMaster createCompany(CompanyMaster companyMaster) {
        log.info("ENTRY-EXIT - create Company");
        return companyMasterRepository.save(companyMaster);
    }

    /*
     * Method to Update
     * @Return Company
     */
    @Override
    public CompanyMaster updateCompany(CompanyMaster companyMaster) {
        log.info("ENTRY-EXIT - Update Company");
        return companyMasterRepository.save(companyMaster);
    }

    /*
     * Method to delete Company
     */
    @Override
    public void deleteCompany(CompanyMaster companyMaster) {
        log.info("ENTRY-EXIT - delete Company");
        companyMasterRepository.delete(companyMaster);
    }

    /*
     * Method to get all Companies
     * @Return Companies page
     */
    @Override
    public Page<CompanyMaster> getAllCompanies(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all Companies Id");
        return companyMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to Validate Company Name
     */
    @Override
    public void validateNewCompanyName(String name) {
        log.info("ENTRY-EXIT - validate Company name");
        if (companyMasterRepository.existsByName(name)) {
            throw new ServiceException(ServiceErrors.COMPANY_NAME_ALREADY_EXIST.CODE,
                    ServiceErrors.COMPANY_NAME_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to Validate Company Reporting Name
     */
    @Override
    public void validateNewCompanyReportingName(String reportingName) {
        log.info("ENTRY-EXIT - Validate Company Reporting Name");
        if(companyMasterRepository.existsByReportingName(reportingName)){
            throw new ServiceException(ServiceErrors.COMPANY_REPORTING_NAME_ALREADY_EXIST.CODE,
                    ServiceErrors.COMPANY_REPORTING_NAME_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to Validate New Company code
     */
    @Override
    public void validateNewCompanyCode(String code) {
        log.info("ENTRY-EXIT - get City by Id");
        if (companyMasterRepository.existsByCode(code)) {
            throw new ServiceException(ServiceErrors.COMPANY_CODE_ALREADY_EXIST.CODE,
                    ServiceErrors.COMPANY_CODE_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to get by Group Company Master Id
     * @Return Company
     */
    @Override
    public List<CompanyMaster> getByGroupCompanyMasterId(Long groupCompanyMasterId) {
        return companyMasterRepository.findByGroupCompanyId(groupCompanyMasterId).
                orElseThrow(() -> new ServiceException(ServiceErrors.GROUP_COMPANY_ID_NOT_FOUND.CODE,
                        ServiceErrors.GROUP_COMPANY_ID_NOT_FOUND.KEY));
    }

}

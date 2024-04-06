package com.newage.wms.service.impl;

import com.newage.wms.entity.GroupCompanyMaster;
import com.newage.wms.repository.GroupCompanyMasterRepository;
import com.newage.wms.service.GroupCompanyMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class GroupCompanyMasterServiceImpl implements GroupCompanyMasterService {

    private final GroupCompanyMasterRepository groupCompanyMasterRepository;

    @Autowired
    public GroupCompanyMasterServiceImpl(GroupCompanyMasterRepository groupCompanyMasterRepository) {
        this.groupCompanyMasterRepository = groupCompanyMasterRepository;
    }

    /*
     * Method to find all Group Companies
     * @Return Group Companies
     */
    @Override
    public Page<GroupCompanyMaster> getAllGroupCompanies(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - find all Group Companies");
        return groupCompanyMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to validate new Code
     */
    @Override
    public void validateNewCode(String code) {
        log.info("ENTRY-EXIT - validate new Code");
        if(groupCompanyMasterRepository.existsByCode(code)){
            throw new ServiceException(ServiceErrors.GROUP_COMPANY_CODE_ALREADY_EXIST.CODE,
                    ServiceErrors.GROUP_COMPANY_CODE_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to validate new name
     */
    @Override
    public void validateNewName(String name) {
        log.info("ENTRY-EXIT - validate new name");
        if(groupCompanyMasterRepository.existsByName(name)){
            throw new ServiceException(ServiceErrors.GROUP_COMPANY_NAME_ALREADY_EXIST.CODE,
                    ServiceErrors.GROUP_COMPANY_NAME_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to validate new Reporting Name
     */
    @Override
    public void validateNewReportingName(String reportingName) {
        log.info("ENTRY-EXIT - validate new reporting name");
        if(groupCompanyMasterRepository.existsByReportingName(reportingName)){
            throw new ServiceException(ServiceErrors.GROUP_COMPANY_REPORTING_NAME_ALREADY_EXISTS.CODE,
                    ServiceErrors.GROUP_COMPANY_REPORTING_NAME_ALREADY_EXISTS.KEY);
        }
    }

    /*
     * Method to get Group Company by Id
     * @Return Group Company
     */
    @Override
    public GroupCompanyMaster getGroupCompanyById(Long id) {
        log.info("ENTRY-EXIT - get GroupCompany by id");
        return groupCompanyMasterRepository.findById(id).
                orElseThrow(() ->  new ServiceException(ServiceErrors.GROUP_COMPANY_ID_NOT_FOUND.CODE,
                        ServiceErrors.GROUP_COMPANY_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to Create Group Company
     * @Return Group Company
     */
    @Override
    public GroupCompanyMaster createGroupCompany(GroupCompanyMaster groupCompanyMaster) {
        log.info("ENTRY-EXIT - create new Group Company");
        return groupCompanyMasterRepository.save(groupCompanyMaster);
    }

    /*
     * Method to Update Group Company
     * @Return Group Company
     */
    @Override
    public GroupCompanyMaster updateGroupCompany(GroupCompanyMaster groupCompanyMaster) {
        log.info("ENTRY-EXIT - update Group Company");
        return groupCompanyMasterRepository.save(groupCompanyMaster);
    }

    /*
     * Method to delete Group Company
     */
    @Override
    public void deleteGroupCompany(GroupCompanyMaster groupCompanyMaster) {
        log.info("ENTRY-EXIT - delete Group company");
        groupCompanyMasterRepository.delete(groupCompanyMaster);
    }

}

package com.newage.wms.service.impl;

import com.newage.wms.entity.BranchMaster;
import com.newage.wms.repository.BranchMasterRepository;
import com.newage.wms.repository.CountryMasterRepository;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class BranchMasterServiceImpl implements BranchMasterService {

    private final BranchMasterRepository branchMasterRepository;

    @Autowired
    public BranchMasterServiceImpl ( BranchMasterRepository branchMasterRepository ) {
        this.branchMasterRepository = branchMasterRepository;
    }

    /*
     *Method to get Branch by Id
     * Return Branch
     */
    @Override
    public BranchMaster getBranchById ( Long id ) {
        log.info("ENTRY-EXIT - get Branch By Id");
        return branchMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.BRANCH_ID_NOT_FOUND.CODE,
                        ServiceErrors.BRANCH_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to find Country by BranchId
     * @Return Country
     */
    @Override
    public Long getCountryIdByBranchMasterId(Long id) {
        log.info("ENTRY-EXIT - find Country by BranchId");
        BranchMaster branchMaster = branchMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.BRANCH_ID_NOT_FOUND.CODE,
                        ServiceErrors.BRANCH_ID_NOT_FOUND.KEY));
        Long countryId = branchMaster.getCountryMaster().getId();
        if (countryId == null){
            throw new ServiceException(
                    ServiceErrors.COUNTRY_ID_NOT_FOUND.CODE,
                    ServiceErrors.COUNTRY_ID_NOT_FOUND.KEY
            );
        }
        return countryId;
    }

}

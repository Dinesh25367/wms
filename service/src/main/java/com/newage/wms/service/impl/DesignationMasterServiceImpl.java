package com.newage.wms.service.impl;

import com.newage.wms.entity.DesignationMaster;
import com.newage.wms.repository.DesignationMasterRepository;
import com.newage.wms.service.DesignationMasterService;
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
public class DesignationMasterServiceImpl  implements DesignationMasterService {

    private final DesignationMasterRepository designationMasterRepository;

    @Autowired
    public DesignationMasterServiceImpl(DesignationMasterRepository designationMasterRepository) {
        this.designationMasterRepository = designationMasterRepository;
    }

    /*
     * Method to validate New Designation Name
     */
    @Override
    public void validateNewDesignationName(String name) {
        log.info("ENTRY-EXIT - validate new Designation Name");
        if(designationMasterRepository.existsByName(name)){
            throw new com.newage.wms.service.exception.ServiceException(com.newage.wms.service.exception.ServiceErrors.DESIGNATION_NAME_ALREADY_EXIST.CODE,
                    com.newage.wms.service.exception.ServiceErrors.DESIGNATION_NAME_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to validate New Designation Code
     */
    @Override
    public void validateNewDesignationCode(String code) {
        log.info("ENTRY-EXIT - validate new Designation Code");
        if(designationMasterRepository.existsByCode(code)){
            throw new com.newage.wms.service.exception.ServiceException(com.newage.wms.service.exception.ServiceErrors.DESIGNATION_CODE_ALREADY_EXIST.CODE,
                    com.newage.wms.service.exception.ServiceErrors.DESIGNATION_CODE_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to get Designation by  Id
     * @Return Designation
     */
    @Override
    public DesignationMaster getDesignationById(Long id) {
        log.info("ENTRY-EXIT - get Designation by Id");
        return designationMasterRepository.findById(id).
                orElseThrow(() -> new com.newage.wms.service.exception.ServiceException(com.newage.wms.service.exception.ServiceErrors.DESIGNATION_ID_NOT_FOUND.CODE,
                        com.newage.wms.service.exception.ServiceErrors.DESIGNATION_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to Create Designation
     * @Return Designation
     */
    @Override
    public DesignationMaster createDesignation(DesignationMaster designationMaster) {
        log.info("ENTRY-EXIT - create Designation");
        return designationMasterRepository.save(designationMaster);
    }

    /*
     * Method to Update Designation
     * @Return Designation
     */
    @Override
    public DesignationMaster updateDesignation(DesignationMaster designationMaster) {
        log.info("ENTRY-EXIT - update Designation");
        return designationMasterRepository.save(designationMaster);
    }

    /*
     * Method to Delete Designation
     */
    @Override
    public void deleteDesignation(DesignationMaster designationMaster) {
        log.info("ENTRY-EXIT - delete Designation");
        designationMasterRepository.delete(designationMaster);
    }

    /*
     * Method to get all Designation
     * @Return Designation Page
     */
    @Override
    public Page<DesignationMaster> getAllDesignation(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all Designation");
        return designationMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to find all Designation for autoComplete
     * @Return Designation
     */
    @Override
    public Iterable<DesignationMaster> getAllDesignationAutoSearch(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - get all Designation autocomplete");
        return designationMasterRepository.findAll(predicate,pageable);
    }

}

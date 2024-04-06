package com.newage.wms.service.impl;

import com.newage.wms.entity.UomMaster;
import com.newage.wms.repository.UomMasterRepository;
import com.newage.wms.service.UomMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UomMasterServiceImpl implements UomMasterService {

    @Autowired
    private UomMasterRepository uomMasterRepository;

    /*
     * Method to get all Uom with pagination, sort and filter
     * @return Page<UomMaster>
     */
    @Override
    public Page<UomMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Find all Uom");
        return uomMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save Uom
     * @return UomMaster
     */
    @Override
    public UomMaster saveUom(UomMaster uomMaster) {
        log.info("ENTRY-EXIT - Create Uom");
        return uomMasterRepository.save(uomMaster);
    }

    /*
     * Method to update Uom
     */
    @Override
    public UomMaster updateUom(UomMaster uomMaster) {
        log.info("ENTRY-EXIT - Update Uom");
        return uomMasterRepository.save(uomMaster);
    }

    /*
     * Method to get Uom by id
     * @return UomMaster
     */
    @Override
    public UomMaster getUomById(Long id) {
        log.info("ENTRY-EXIT - Get Uom by id");
        return uomMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.UOM_MASTER_ID_NOT_FOUND.CODE,
                        ServiceErrors.UOM_MASTER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all Uom for autoComplete
     * @return Iterable<UomMaster>
     */
    @Override
    public Iterable<UomMaster> fetchAllUom(Predicate predicate, Pageable pageable){
        log.info("ENTRY-EXIT - Get all Uom for autoComplete");
        return uomMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to get Uom by code
     * @return UomMaster
     */
    @Override
    public UomMaster getUomByCode(String locationHandlingUomCode) {
        log.info("ENTRY-EXIT - Get Uom by code");
        return uomMasterRepository.findByCode(locationHandlingUomCode);
    }

    /*
     * Method to validate unique Uom attribute in save
     */
    @Override
    public void validateUniqueUomAttributeSave(String code) {
        log.info("ENTRY-EXIT - validate unique Uom attribute in save");
        List<UomMaster> uomMasterList = uomMasterRepository.findAll();
        Boolean codeExists = uomMasterList.stream()
                .anyMatch(uom -> uom.getCode().equals(code));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.UOM_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.UOM_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique Uom attribute in save
     */
    @Override
    public void validateUniqueUomAttributeUpdate(String code,Long id) {
        log.info("ENTRY-EXIT - validate unique Uom attribute in update");
        List<UomMaster> uomMasterList = uomMasterRepository.findAll();
        Boolean codeExists = uomMasterList.stream()
                .anyMatch(uom -> uom.getCode().equals(code) && !uom.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.UOM_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.UOM_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}

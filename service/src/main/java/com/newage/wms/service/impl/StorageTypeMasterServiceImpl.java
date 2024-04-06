package com.newage.wms.service.impl;

import com.newage.wms.entity.StorageTypeMaster;
import com.newage.wms.repository.StorageTypeMasterRepository;
import com.newage.wms.service.StorageTypeMasterService;
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
public class StorageTypeMasterServiceImpl implements StorageTypeMasterService {

    @Autowired
    private StorageTypeMasterRepository storageTypeMasterRepository;

    /*
     * Method to get all StorageType
     * @return Page<StorageTypeMaster>
     */
    @Override
    public Page<StorageTypeMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all StorageType");
        return storageTypeMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save StorageArea
     * @Return StorageAreaMaster
     */
    @Override
    public StorageTypeMaster saveStorageType(StorageTypeMaster storageTypeMaster) {
        log.info("ENTRY-EXIT - Save StorageType");
        return storageTypeMasterRepository.save(storageTypeMaster);
    }

    /*
     * Method to update StorageType
     * @Return StorageTypeMaster
     */
    @Override
    public StorageTypeMaster updateStorageType(StorageTypeMaster storageTypeMaster) {
        log.info("ENTRY-EXIT - Update StorageType");
        return storageTypeMasterRepository.save(storageTypeMaster);
    }

    /*
     * Method to get StorageType by id
     * @return StorageTypeMaster
     */
    @Override
    public StorageTypeMaster getStorageTypeMasterById(Long id) {
        log.info("ENTRY-EXIT - Get StorageType by id");
        return storageTypeMasterRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.STORAGE_TYPE_ID_NOT_FOUND.CODE,
                        ServiceErrors.STORAGE_TYPE_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all StorageType for autocomplete
     * @return Iterable<StorageTypeMaster>
     */
    @Override
    public Iterable<StorageTypeMaster> fetchAllStorageType(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all StorageType");
        return storageTypeMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to get StorageType by code
     * @return StorageTypeMaster
     */
    @Override
    public StorageTypeMaster getStorageTypeMasterByCode(String storageTypeCode) {
        log.info("ENTRY-EXIT - Get StorageType by code");
        return storageTypeMasterRepository.findByCode(storageTypeCode);
    }

    @Override
    public void validateUniqueStorageTypeAttributeSave(String code) {
        log.info("ENTRY - validate unique storageType attributes in save");
        List<StorageTypeMaster> storageTypeMasterList = storageTypeMasterRepository.findAll();
        Boolean codeExists = storageTypeMasterList.stream()
                .anyMatch(storageType -> storageType.getCode().equals(code));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.STORAGE_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.STORAGE_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }



    /*
     * Method to validate unique aisle attributes in Update
     */
    @Override
    public void validateUniqueStorageTypeAttributeUpdate(String code,Long id){
        log.info("ENTRY - validate unique storageType attributes in update");
        List<StorageTypeMaster> storageTypeMasterList = storageTypeMasterRepository.findAll();
        Boolean codeExists = storageTypeMasterList.stream()
                .anyMatch(storageType -> storageType.getCode().equals(code) && !storageType.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.STORAGE_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.STORAGE_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}
package com.newage.wms.service.impl;

import com.newage.wms.entity.StorageAreaMaster;
import com.newage.wms.repository.StorageAreaMasterRepository;
import com.newage.wms.service.StorageAreaMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class StorageAreaMasterServiceImpl implements StorageAreaMasterService {

    @Autowired
    private StorageAreaMasterRepository storageAreaMasterRepository;

    /*
     * Method to get all StorageType
     * @return Page<StorageAreaMaster>
     */
    @Override
    public Page<StorageAreaMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all StorageType");
        return storageAreaMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save StorageArea
     * @Return StorageAreaMaster
     */
    @Override
    public StorageAreaMaster saveStorageArea(StorageAreaMaster storageAreaMaster) {
        log.info("ENTRY-EXIT - Save StorageArea");
        return storageAreaMasterRepository.save(storageAreaMaster);
    }

    /*
     * Method to update StorageArea
     * @Return StorageAreaMaster
     */
    @Override
    public StorageAreaMaster updateStorageArea(StorageAreaMaster storageAreaMaster) {
        log.info("ENTRY-EXIT - Update StorageArea");
        return storageAreaMasterRepository.save(storageAreaMaster);
    }

    /*
     * Method to get StorageArea by id
     * @return StorageAreaMaster
     */
    @Override
    public StorageAreaMaster getStorageAreaMasterById(Long id){
        log.info("ENTRY-EXIT - Get StorageArea by id");
        return storageAreaMasterRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.STORAGE_AREA_ID_NOT_FOUND.CODE,
                        ServiceErrors.STORAGE_AREA_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all StorageArea for autocomplete
     * @return Iterable<StorageAreaMaster>
     */
    @Override
    public Iterable<StorageAreaMaster> fetchAllStorageArea(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all StorageArea");
        return storageAreaMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to get StorageArea by code
     * @return StorageAreaMaster
     */
    @Override
    public StorageAreaMaster getStorageAreaMasterByCode(String storageAreaCode) {
        log.info("ENTRY-EXIT - Get StorageArea by code");
        return storageAreaMasterRepository.findByCode(storageAreaCode);
    }

    @Override
    public void validateUniqueStorageAreaAttributeSave(String code) {
        log.info("ENTRY - validate unique storageType attributes in save");
        List<StorageAreaMaster> storageAreaMasterList = storageAreaMasterRepository.findAll();
        Boolean codeExists = storageAreaMasterList.stream()
                .anyMatch(storageType -> storageType.getCode().equals(code));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.STORAGE_AREA_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.STORAGE_AREA_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }



    /*
     * Method to validate unique aisle attributes in Update
     */
    @Override
    public void validateUniqueStorageAreaAttributeUpdate(String code,Long id){
        log.info("ENTRY - validate unique storageType attributes in update");
        List<StorageAreaMaster> storageAreaMasterList = storageAreaMasterRepository.findAll();
        Boolean codeExists = storageAreaMasterList.stream()
                .anyMatch(storageType -> storageType.getCode().equals(code) && !storageType.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.STORAGE_AREA_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.STORAGE_AREA_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}

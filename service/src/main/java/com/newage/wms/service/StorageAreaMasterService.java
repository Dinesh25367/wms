package com.newage.wms.service;

import com.newage.wms.entity.StorageAreaMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StorageAreaMasterService {

    Page<StorageAreaMaster> findAll(Predicate predicate, Pageable pageable);

    StorageAreaMaster saveStorageArea(StorageAreaMaster storageAreaMaster);

    StorageAreaMaster updateStorageArea(StorageAreaMaster storageAreaMaster);

    StorageAreaMaster getStorageAreaMasterById(Long id);

    Iterable<StorageAreaMaster> fetchAllStorageArea(Predicate predicate, Pageable pageable);

    StorageAreaMaster getStorageAreaMasterByCode(String storageAreaCode);

    void validateUniqueStorageAreaAttributeSave(String code);

    void validateUniqueStorageAreaAttributeUpdate(String code,Long id);

}

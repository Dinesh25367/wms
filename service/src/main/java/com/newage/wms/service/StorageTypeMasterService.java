package com.newage.wms.service;

import com.newage.wms.entity.StorageTypeMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StorageTypeMasterService {

    Page<StorageTypeMaster> findAll(Predicate predicate, Pageable pageable);

    StorageTypeMaster saveStorageType(StorageTypeMaster storageTypeMaster);

    StorageTypeMaster updateStorageType(StorageTypeMaster storageTypeMaster);

    StorageTypeMaster getStorageTypeMasterById(Long id);

    Iterable<StorageTypeMaster> fetchAllStorageType(Predicate predicate, Pageable pageable);

    StorageTypeMaster getStorageTypeMasterByCode(String storageTypeCode);

    void validateUniqueStorageTypeAttributeSave(String code);

    void validateUniqueStorageTypeAttributeUpdate(String code,Long id);

}

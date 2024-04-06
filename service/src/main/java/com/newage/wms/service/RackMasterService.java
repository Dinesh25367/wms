package com.newage.wms.service;

import com.newage.wms.entity.RackMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RackMasterService {

    Page<RackMaster> findAll(Predicate predicate, Pageable pageable);

    RackMaster saveRack(RackMaster rackMaster);

    RackMaster updateRack(RackMaster rackMaster);

    RackMaster getRackMasterById(Long id);

    Iterable<RackMaster> fetchAllRack(Predicate predicate, Pageable pageable);

    RackMaster getRackMasterByCode(String rackCode);

    void validateUniqueRackAttributeSave(String code,Long warehouseId);

    void validateUniqueRackAttributeUpdate(String code,Long id,Long warehouseId);

}

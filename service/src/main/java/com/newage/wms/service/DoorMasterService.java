package com.newage.wms.service;

import com.newage.wms.entity.DoorMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoorMasterService {

    Page<DoorMaster> findAll(Predicate predicate, Pageable pageable);

    DoorMaster saveDoor(DoorMaster doorMaster);

    DoorMaster updateDoor(DoorMaster doorMaster);

    DoorMaster findById(Long id);

    Iterable<DoorMaster> findAllAutoComplete(String query,String status, Long branchId);

    void validateUniqueDoorAttributeSave(String code);

    void validateUniqueDoorAttributeUpdate(String code,Long id);

}

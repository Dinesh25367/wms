package com.newage.wms.service;

import com.newage.wms.entity.InventoryStatusMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryStatusMasterService {

    InventoryStatusMaster getById(Long id);

    InventoryStatusMaster saveInventory(InventoryStatusMaster inventoryStatusMaster);

    Page<InventoryStatusMaster> findAll(Predicate predicate, Pageable pageable);

    InventoryStatusMaster updateInventory(InventoryStatusMaster inventoryStatusMaster);

    void validateUniqueInventoryStatusAttributeSave(String code);

    void validateUniqueInventoryStatusAttributeUpdate(String code,Long id);

}

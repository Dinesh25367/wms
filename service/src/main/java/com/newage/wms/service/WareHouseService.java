package com.newage.wms.service;

import com.newage.wms.entity.WareHouseMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WareHouseService {

    Page<WareHouseMaster> findAll(Predicate predicate, Pageable pageable);

    WareHouseMaster saveWareHouse(WareHouseMaster wareHouse);

    WareHouseMaster updateWareHouse(WareHouseMaster wareHouse);

    WareHouseMaster getWareHouseById(Long id);

    Long getWareHouseCount();

    WareHouseMaster getWareHouseByCode(String code);

    String generateNewWareHouseId();

    Long generateVersion();

    void validateUniqueWareHouseAttributeSave(String code);

    void validateUniqueWareHouseAttributeUpdate(String code,Long id);

    Page<WareHouseMaster> fetchWarehouseList(String code, String name,
                                            String wareHouseLocationPrefix, String isBonded, Long branchId, String status,Pageable pageable);


}


package com.newage.wms.service;

import com.newage.wms.entity.ZoneMasterWMS;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZoneMasterWMSService {

    Page<ZoneMasterWMS> findAll(Predicate predicate, Pageable pageable);

    ZoneMasterWMS saveZoneWMS(ZoneMasterWMS zoneMasterWMS);

    ZoneMasterWMS updateZoneMasterWMS(ZoneMasterWMS zoneMasterWMS);

    ZoneMasterWMS getZoneMasterWMSById(Long id);

    Iterable<ZoneMasterWMS> fetchAllZoneWMS(Predicate predicate, Pageable pageable);

    ZoneMasterWMS getZoneMasterWMSByCode(String zoneCode);

    void validateUniqueZoneAttributeSave(String code,Long warehouseId);

    void validateUniqueZoneAttributeUpdate(String code,Long id,Long warehouseId);

}

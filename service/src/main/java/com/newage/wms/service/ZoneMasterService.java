package com.newage.wms.service;

import com.newage.wms.entity.ZoneMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ZoneMasterService {

    void validateNewCode(String code);

    void validateNewName(String name);

    ZoneMaster getZoneById(Long id);

    List<ZoneMaster> getZoneByCountryId(Long id);

    Long finMaxId();

    ZoneMaster createZone(ZoneMaster zoneMaster);

    ZoneMaster updateZone(ZoneMaster zoneMaster);

    void deleteZone(ZoneMaster zoneMaster);

    Page<ZoneMaster> getAllZones(Predicate predicate, Pageable pageRequest);

}

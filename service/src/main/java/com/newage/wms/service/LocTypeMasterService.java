package com.newage.wms.service;

import com.newage.wms.entity.LocTypeMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocTypeMasterService {

    Page<LocTypeMaster> findAll(Predicate predicate, Pageable pageable);

    LocTypeMaster saveLocType(LocTypeMaster locTypeMaster);

    LocTypeMaster updateLocType(LocTypeMaster locTypeMaster);

    LocTypeMaster getLocTypeById(Long id);

    Iterable<LocTypeMaster> fetchAllLocationType(Predicate predicate, Pageable pageable);

    LocTypeMaster getLocTypeByCode(String locTypeCode);

    void validateUniqueLocTypeAttributeSave(String code);

    void validateUniqueLocTypeAttributeUpdate(String code,Long id);

}

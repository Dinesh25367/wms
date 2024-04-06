package com.newage.wms.service;

import com.newage.wms.entity.UomMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UomMasterService {

    Page<UomMaster> findAll(Predicate predicate, Pageable pageable);

    UomMaster saveUom(UomMaster uomMaster);

    UomMaster updateUom(UomMaster uomMaster);

    UomMaster getUomById(Long id);

    Iterable<UomMaster> fetchAllUom(Predicate predicate, Pageable pageable);

    UomMaster getUomByCode(String locationHandlingUomCode);

    void validateUniqueUomAttributeSave(String code);

    void validateUniqueUomAttributeUpdate(String code,Long id);

}

package com.newage.wms.service;

import com.newage.wms.entity.ShipmentHeader;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

public interface ShipmentHeaderService {

    ShipmentHeader getById(Long id);

    Iterable<ShipmentHeader> getAllAutoComplete(Predicate predicate, Pageable pageable);

}

package com.newage.wms.service;

import com.newage.wms.entity.OrderTypeMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderTypeMasterService {
    OrderTypeMaster getById(Long id);

    Iterable<OrderTypeMaster> getAllAutoComplete(Predicate predicate, Pageable pageable);

    OrderTypeMaster save(OrderTypeMaster orderTypeMaster);

    Page<OrderTypeMaster> findAll(Predicate predicate, Pageable pageable);

    OrderTypeMaster update(OrderTypeMaster orderTypeMaster);

    void validateUniqueRackAttributeSave(String code);

    void validateUniqueRackAttributeUpdate(String code, Long id);

}

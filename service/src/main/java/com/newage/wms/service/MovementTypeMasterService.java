package com.newage.wms.service;

import com.newage.wms.entity.MovementTypeMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovementTypeMasterService {

    MovementTypeMaster getById(Long id);

    Iterable<MovementTypeMaster> getAllAutoComplete(Predicate predicate, Pageable pageable);

    MovementTypeMaster getByCode();

    MovementTypeMaster save(MovementTypeMaster movementTypeMaster);

    Page<MovementTypeMaster> findAll(Predicate predicate, Pageable pageable);

    MovementTypeMaster update(MovementTypeMaster movementTypeMaster);

    void validateUniqueRackAttributeSave(String code);

    void validateUniqueRackAttributeUpdate(String code, Long id);
}

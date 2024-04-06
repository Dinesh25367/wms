package com.newage.wms.service;

import com.newage.wms.entity.StateMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StateMasterService {

    Page<StateMaster> getAllStates(Predicate predicate, Pageable pageRequest);

    StateMaster getStateById(Long id);

}

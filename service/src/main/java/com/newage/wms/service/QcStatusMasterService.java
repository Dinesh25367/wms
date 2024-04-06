package com.newage.wms.service;

import com.newage.wms.entity.QcStatusMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

public interface QcStatusMasterService {

    QcStatusMaster getById(Long id);

    Iterable<QcStatusMaster> getAllAutoComplete(Predicate predicate, Pageable pageable);

}

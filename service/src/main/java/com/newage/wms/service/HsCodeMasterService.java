package com.newage.wms.service;

import com.newage.wms.entity.HsCodeMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HsCodeMasterService {

    Page<HsCodeMaster> findAll(Predicate predicate, Pageable pageable);

    Iterable<HsCodeMaster> getAllAutoComplete(Predicate predicate, Pageable pageable);

    HsCodeMaster getById(Long id);

    HsCodeMaster getByCode(String code);
}


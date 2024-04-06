package com.newage.wms.service;

import com.newage.wms.entity.CurrencyMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyMasterService {

    Page<CurrencyMaster> findAll(Predicate predicate, Pageable pageRequest);

    CurrencyMaster getCurrencyById(Long id);

    Iterable<CurrencyMaster> fetchAllCurrency(Predicate predicate, Pageable pageable);

    CurrencyMaster getByCode(String code);
}

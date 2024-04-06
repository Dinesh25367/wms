package com.newage.wms.service;

import com.newage.wms.entity.CurrencyRateMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyRateMasterService {

    Page<CurrencyRateMaster> getCurrencyRateByFromAndToCurrency(Predicate predicate, Pageable pageRequest, Long fromCurrency, Long toCurrency);

    Page<CurrencyRateMaster> getCurrencyRateByCode(Predicate predicate, Pageable pageRequest, String code);

}

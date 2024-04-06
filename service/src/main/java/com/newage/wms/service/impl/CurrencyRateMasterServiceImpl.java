package com.newage.wms.service.impl;

import com.newage.wms.entity.CurrencyRateMaster;
import com.newage.wms.entity.QCurrencyRateMaster;
import com.newage.wms.repository.CurrencyRateMasterRepository;
import com.newage.wms.service.CurrencyRateMasterService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@Service
@Transactional
public class CurrencyRateMasterServiceImpl implements CurrencyRateMasterService {

    @Autowired
    CurrencyRateMasterRepository currencyRateMasterRepository;

    @Override
    public Page<CurrencyRateMaster> getCurrencyRateByFromAndToCurrency(Predicate predicate, Pageable pageRequest, Long fromCurrency, Long toCurrency) {
        QCurrencyRateMaster qCurrencyRateMaster = QCurrencyRateMaster.currencyRateMaster;
        Predicate predicate1 = qCurrencyRateMaster.accountingCurrency.id.in(fromCurrency).and(qCurrencyRateMaster.toCurrency.id.in(toCurrency));
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(predicate);
        predicates.add(predicate1);
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        return currencyRateMasterRepository.findAll(predicateAll, pageRequest);
    }

    @Override
    public Page<CurrencyRateMaster> getCurrencyRateByCode(Predicate predicate, Pageable pageRequest, String code) {
        QCurrencyRateMaster qCurrencyRateMaster = QCurrencyRateMaster.currencyRateMaster;
        Collection<Predicate> predicates = new ArrayList<>();
        Predicate predicateCode = qCurrencyRateMaster.toCurrency.code.eq(code);
        predicates.add(predicateCode);
        Predicate predicateAll = ExpressionUtils.allOf(predicateCode);
        return currencyRateMasterRepository.findAll(predicateAll, pageRequest);
    }

}
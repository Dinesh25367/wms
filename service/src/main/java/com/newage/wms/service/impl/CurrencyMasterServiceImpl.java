package com.newage.wms.service.impl;

import com.newage.wms.entity.CurrencyMaster;
import com.newage.wms.repository.CurrencyMasterRepository;
import com.newage.wms.service.CurrencyMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class CurrencyMasterServiceImpl implements CurrencyMasterService {

    private final CurrencyMasterRepository currencyMasterRepository;

    @Autowired
    public CurrencyMasterServiceImpl(CurrencyMasterRepository currencyMasterRepository) {
        this.currencyMasterRepository = currencyMasterRepository;
    }

    /*
     *Method to get all Currencies with pagination, filter and sort
     * @Return Currencies
     */
    @Override
    public Page<CurrencyMaster> findAll(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all Currencies with pagination, filter and sort");
        return currencyMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     *Method to get  Currency by Id
     * @Return CurrencyMaster
     */
    @Override
    public CurrencyMaster getCurrencyById(Long id) {
        log.info("ENTRY-EXIT - get Currency by Id");
        return currencyMasterRepository.findById(id).
                orElseThrow(() ->  new ServiceException(ServiceErrors.CURRENCY_ID_NOT_FOUND.CODE));
    }

    /*
     *Method to get all Currencies for autoComplete
     * @Return Currencies
     */
    @Override
    public Iterable<CurrencyMaster> fetchAllCurrency(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - get all Currencies for autoComplete");
        return currencyMasterRepository.findAll(predicate, pageable);
    }

    @Override
    public CurrencyMaster getByCode(String code) {
        return currencyMasterRepository.getByCode(code);
    }

}
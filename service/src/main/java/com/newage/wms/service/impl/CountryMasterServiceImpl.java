package com.newage.wms.service.impl;

import com.newage.wms.entity.CountryMaster;
import com.newage.wms.repository.CountryMasterRepository;
import com.newage.wms.service.CountryMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CountryMasterServiceImpl implements CountryMasterService {

    private final CountryMasterRepository countryMasterRepository;

    @Autowired
    public CountryMasterServiceImpl(CountryMasterRepository countryMasterRepository) {
        this.countryMasterRepository = countryMasterRepository;
    }

    /*
     * Method to get Country by Id
     * @Return Country
     */
    @Override
    public CountryMaster getCountryById(Long id) {
        log.info("ENTRY-EXIT - get Country by Id");
        return countryMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.COUNTRY_ID_NOT_FOUND.CODE,
                        ServiceErrors.COUNTRY_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all Countries AutoComplete
     * @Return Countries
     */
    @Override
    public Iterable<CountryMaster> getAllCountriesAutoSearch(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - get all Countries AutoComplete");
        return countryMasterRepository.findAll(predicate,pageable);
    }

}
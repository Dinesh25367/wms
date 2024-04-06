package com.newage.wms.service;

import com.newage.wms.entity.CountryMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

public interface CountryMasterService {

    CountryMaster getCountryById(Long id);

    Iterable<CountryMaster> getAllCountriesAutoSearch(Predicate predicate, Pageable pageable);

}

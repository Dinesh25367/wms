package com.newage.wms.service;

import com.newage.wms.entity.CityMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityMasterService {

    Page<CityMaster> getAllCities(Predicate predicate, Pageable pageRequest);

    CityMaster getCityById(Long id);

}

package com.newage.wms.service;

import com.newage.wms.entity.OriginMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OriginMasterService {

    OriginMaster getOriginById(Long id);

    OriginMaster createOrigin(OriginMaster originMaster);

    OriginMaster updateOrigin(OriginMaster originMaster);

    void deleteOrigin(OriginMaster originMaster);

    Page<OriginMaster> getAllOrigins(Predicate predicate, Pageable pageRequest);

    Page<OriginMaster> getFilteredAllOrigins(Predicate predicate, Pageable pageRequest, String portName, String portCode, String countryName);

    void validateNewOriginCode(String code);

    void validateNewOriginName(String name);

    Iterable<OriginMaster> getAllAutoComplete(Predicate combinedPredicate, Pageable pageable);

}

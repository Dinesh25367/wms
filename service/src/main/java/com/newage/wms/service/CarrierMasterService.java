package com.newage.wms.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.newage.wms.entity.CarrierMaster;

public interface CarrierMasterService {

     CarrierMaster getCarrierById ( Long id );

     Page<CarrierMaster> getAllCarrier (Predicate predicate, Pageable pageRequest );

     Page<CarrierMaster> getFilteredAllCarrier(Predicate predicate, Pageable pageRequest, String carrierName, String carrierCode);

     Iterable<CarrierMaster> getAllAutoComplete(String query,String transportMode,String status);

}

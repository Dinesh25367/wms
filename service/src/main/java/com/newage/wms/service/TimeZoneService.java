package com.newage.wms.service;

import com.newage.wms.entity.TimeZoneMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TimeZoneService {

    Page<TimeZoneMaster> getAllTimeZone(Predicate predicate, Pageable pageRequest);

    TimeZoneMaster getTimeZoneById(Long id);

}

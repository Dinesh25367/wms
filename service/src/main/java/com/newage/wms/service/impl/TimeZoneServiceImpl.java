package com.newage.wms.service.impl;

import com.newage.wms.entity.TimeZoneMaster;
import com.newage.wms.repository.TimeZoneRepository;
import com.newage.wms.service.TimeZoneService;
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
public class TimeZoneServiceImpl implements TimeZoneService {

    private final TimeZoneRepository timeZoneRepository;

    @Autowired
    public TimeZoneServiceImpl(TimeZoneRepository timeZoneRepository) {
        this.timeZoneRepository = timeZoneRepository;
    }

    /*
     * Method to find all Time Zone
     * @Return TimeZone
     */
    @Override
    public Page<TimeZoneMaster> getAllTimeZone(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all TimeZone");
        return timeZoneRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to get Time Zone by Id
     * @Return TimeZone
     */
    @Override
    public TimeZoneMaster getTimeZoneById(Long id) {
        log.info("ENTRY-EXIT - get TimeZone by Id");
        return timeZoneRepository.findById(id).orElseThrow(() -> new ServiceException(ServiceErrors.ZONE_ID_NOT_FOUND.CODE));
    }

}

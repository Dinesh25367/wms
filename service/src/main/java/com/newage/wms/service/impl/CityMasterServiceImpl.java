package com.newage.wms.service.impl;

import com.newage.wms.entity.CityMaster;
import com.newage.wms.repository.CityMasterRepository;
import com.newage.wms.service.CityMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class CityMasterServiceImpl implements CityMasterService {

    private final CityMasterRepository cityMasterRepository;

    @Autowired
    public CityMasterServiceImpl(CityMasterRepository cityMasterRepository) {
        this.cityMasterRepository = cityMasterRepository;
    }

    /*
     *Method to get all Cities
     * Return Cities
     */
    @Override
    public Page<CityMaster> getAllCities(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all Cities");
        return cityMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     *Method to get City  by Id
     * Return City
     */
    @Override
    public CityMaster getCityById(Long id) {
        log.info("ENTRY-EXIT - get City by Id");
        return cityMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.CITY_ID_NOT_FOUND.CODE,
                        ServiceErrors.CITY_ID_NOT_FOUND.KEY));
    }

}

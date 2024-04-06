package com.newage.wms.service.impl;

import com.newage.wms.entity.ShipmentHeader;
import com.newage.wms.repository.ShipmentHeaderRepository;
import com.newage.wms.service.ShipmentHeaderService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ShipmentHeaderServiceImpl implements ShipmentHeaderService {

    @Autowired
    private ShipmentHeaderRepository shipmentHeaderRepository;

    /*
     * Method to get ShipmentHeader by Id
     * @Return ShipmentHeader
     */
    @Override
    public ShipmentHeader getById(Long id) {
        log.info("ENTRY-EXIT - get ShipmentHeader by Id");
        return shipmentHeaderRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.SHIPMENT_ID_NOT_FOUND.CODE,
                        ServiceErrors.SHIPMENT_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to find All ShipmentHeader for autoComplete
     * @Return Iterable<ShipmentHeader>
     */
    @Override
    public Iterable<ShipmentHeader> getAllAutoComplete(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Find All ShipmentHeader for autoComplete");
        return shipmentHeaderRepository.findAll(predicate,pageable);
    }

}
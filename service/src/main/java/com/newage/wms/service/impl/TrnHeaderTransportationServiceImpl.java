package com.newage.wms.service.impl;

import com.newage.wms.entity.TrnHeaderTransportation;
import com.newage.wms.repository.TrnHeaderTransportationRepository;
import com.newage.wms.service.TrnHeaderTransportationService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrnHeaderTransportationServiceImpl implements TrnHeaderTransportationService {

    @Autowired
    private TrnHeaderTransportationRepository trnHeaderTransportationRepository;

    /*
     * Method to get TrnHeaderTransportation by id
     * @Return TrnHeaderTransportation
     */
    @Override
    public TrnHeaderTransportation getById(Long id) {
        log.info("ENTRY-EXIT - Update TrnHeaderTransportation by Id");
        return trnHeaderTransportationRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.TRN_HEADER_TRANSPORTATION_ID_NOT_FOUND.CODE,
                        ServiceErrors.TRN_HEADER_TRANSPORTATION_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to delete all TrnHeaderTransportation Iterable
     */
    @Override
    public void deleteAllInIterable(Iterable<TrnHeaderTransportation> trnHeaderTransportationIterable) {
        log.info("ENTRY-EXIT - Delete all TrnHeaderTransportation Iterable");
        trnHeaderTransportationRepository.deleteAll(trnHeaderTransportationIterable);
    }

}

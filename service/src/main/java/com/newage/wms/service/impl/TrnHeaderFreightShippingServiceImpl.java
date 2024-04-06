package com.newage.wms.service.impl;

import com.newage.wms.repository.TrnHeaderFreightShippingRepository;
import com.newage.wms.service.TrnHeaderFreightShippingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrnHeaderFreightShippingServiceImpl implements TrnHeaderFreightShippingService {

    @Autowired
    private TrnHeaderFreightShippingRepository trnHeaderFreightShippingRepository;

    /*
     * Method to delete TrnHeaderFreightShipping by id
     */
    @Override
    public void deleteById(Long id) {
        log.info("ENTRY-EXIT - Delete TrnHeaderFreightShipping by id");
        trnHeaderFreightShippingRepository.deleteById(id);
    }

}

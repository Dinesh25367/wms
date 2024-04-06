package com.newage.wms.service.impl;

import com.newage.wms.repository.TrnDetailQcRepository;
import com.newage.wms.service.TrnDetailQcService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrnDetailQcServiceImpl implements TrnDetailQcService {

    @Autowired
    private TrnDetailQcRepository trnDetailQcRepository;

    /*
     * Method to delete TrnDetailQc by id
     */
    @Override
    public void deleteById(Long id) {
        log.info("ENTRY-EXIT - Delete TrnDetailQc by id");
        trnDetailQcRepository.deleteById(id);
    }

}

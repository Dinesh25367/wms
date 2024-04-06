package com.newage.wms.service.impl;

import com.newage.wms.repository.TrnDetailLotRepository;
import com.newage.wms.service.TrnDetailLotService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrnDetailLotServiceImpl implements TrnDetailLotService {

    @Autowired
    private TrnDetailLotRepository trnDetailLotRepository;

    /*
     * Method to delete TrnDetailLot by id
     */
    @Override
    public void deleteById(Long id) {
        log.info("ENTRY-EXIT - Delete TrnDetailLot by id");
        trnDetailLotRepository.deleteById(id);
    }

}

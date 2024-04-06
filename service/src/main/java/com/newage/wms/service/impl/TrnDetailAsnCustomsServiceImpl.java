package com.newage.wms.service.impl;

import com.newage.wms.repository.TrnDetailAsnCustomsRepository;
import com.newage.wms.service.TrnDetailAsnCustomsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrnDetailAsnCustomsServiceImpl implements TrnDetailAsnCustomsService {

    @Autowired
    private TrnDetailAsnCustomsRepository trnDetailAsnCustomsRepository;

    /*
     * Method to delete TrnDetailAsnCustoms by id
     */
    @Override
    public void deleteById(Long id) {
        log.info("ENTRY-EXIT - Delete TrnDetailAsnCustoms by id");
        trnDetailAsnCustomsRepository.deleteById(id);
    }

}

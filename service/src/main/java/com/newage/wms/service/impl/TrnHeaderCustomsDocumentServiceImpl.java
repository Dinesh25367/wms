package com.newage.wms.service.impl;

import com.newage.wms.repository.TrnHeaderCustomsDocumentRepository;
import com.newage.wms.service.TrnHeaderCustomsDocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrnHeaderCustomsDocumentServiceImpl implements TrnHeaderCustomsDocumentService {

    @Autowired
    private TrnHeaderCustomsDocumentRepository trnHeaderCustomsDocumentRepository;

    /*
     * Method to delete TrnHeaderCustomsDocument by id
     */
    @Override
    public void deleteById(Long id) {
        log.info("ENTRY-EXIT - Delete TrnHeaderCustomsDocument by id");
        trnHeaderCustomsDocumentRepository.deleteById(id);
    }

}

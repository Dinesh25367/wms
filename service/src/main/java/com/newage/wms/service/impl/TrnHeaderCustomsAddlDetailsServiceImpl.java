package com.newage.wms.service.impl;

import com.newage.wms.entity.TrnHeaderCustomsAddlDetails;
import com.newage.wms.repository.TrnHeaderCustomsAddlDetailsRepository;
import com.newage.wms.service.TrnHeaderCustomsAddlDetailsService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrnHeaderCustomsAddlDetailsServiceImpl implements TrnHeaderCustomsAddlDetailsService {

    @Autowired
    private TrnHeaderCustomsAddlDetailsRepository trnHeaderCustomsAddlDetailsRepository;

    /*
     * Method to get TrnHeaderCustomsAddlDetails by id
     * @Return TrnHeaderCustomsAddlDetails
     */
    @Override
    public TrnHeaderCustomsAddlDetails getById(Long id) {
        log.info("ENTRY-EXIT - Update TrnHeaderCustomsAddlDetails by Id");
        return trnHeaderCustomsAddlDetailsRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.TRN_HEADER_CUSTOMS_ADDL_DETAILS_ID_NOT_FOUND.CODE,
                        ServiceErrors.TRN_HEADER_CUSTOMS_ADDL_DETAILS_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to delete all TrnHeaderCustomsAddlDetails Iterable
     */
    @Override
    public void deleteAllInIterable(Iterable<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsIterable) {
        log.info("ENTRY-EXIT - Delete all TrnHeaderCustomsAddlDetails Iterable");
        trnHeaderCustomsAddlDetailsRepository.deleteAll(trnHeaderCustomsAddlDetailsIterable);
    }

}

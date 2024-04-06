package com.newage.wms.service.impl;

import com.newage.wms.entity.CustomerConfigurationMaster;
import com.newage.wms.entity.TrnHeaderAddlDetails;
import com.newage.wms.repository.TrnHeaderAddlDetailsRepository;
import com.newage.wms.service.TrnHeaderAddlDetailsService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrnHeaderAddlDetailsServiceImpl implements TrnHeaderAddlDetailsService {

    @Autowired
    private TrnHeaderAddlDetailsRepository trnHeaderAddlDetailsRepository;

    /*
     * Method to get TrnHeaderAddlDetails by id
     * @Return TrnHeaderAddlDetails
     */
    @Override
    public TrnHeaderAddlDetails getById(Long id) {
        log.info("ENTRY-EXIT - Update TrnHeaderAddlDetails by Id");
        return trnHeaderAddlDetailsRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.TRN_HEADER_ADDL_DETAILS_ID_NOT_FOUND.CODE,
                        ServiceErrors.TRN_HEADER_ADDL_DETAILS_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to delete all TrnHeaderAddlDetails Iterable
     */
    @Override
    public void deleteAllInIterable(Iterable<TrnHeaderAddlDetails> trnHeaderAddlDetailsIterable) {
        log.info("ENTRY-EXIT - Delete all TrnHeaderAddlDetails Iterable");
        trnHeaderAddlDetailsRepository.deleteAll(trnHeaderAddlDetailsIterable);
    }



}

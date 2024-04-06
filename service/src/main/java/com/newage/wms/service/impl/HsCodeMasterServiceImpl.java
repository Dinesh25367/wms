package com.newage.wms.service.impl;

import com.newage.wms.entity.HsCodeMaster;
import com.newage.wms.repository.HsCodeMasterRepository;
import com.newage.wms.service.HsCodeMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class HsCodeMasterServiceImpl implements HsCodeMasterService {

    @Autowired
    private HsCodeMasterRepository hsCodeMasterRepository;

    /*
     * Method to find all HsCode with filter, sort and pagination
     * @Return HsCodeMaster
     */
    @Override
    public Page<HsCodeMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - find HsCode by id");
        return hsCodeMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to find HsCode by id
     * @Return HsCodeMaster
     */
    @Override
    public HsCodeMaster getById(Long id) {
        log.info("ENTRY-EXIT - find HsCode by id");
        return hsCodeMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.HS_CODE_ID_NOT_FOUND.CODE,
                        ServiceErrors.HS_CODE_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to find all HsCode for autocomplete
     * @Return Iterable<HsCodeMaster>
     */
    @Override
    public Iterable<HsCodeMaster> getAllAutoComplete(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - find all HsCode for autocomplete");
        return hsCodeMasterRepository.findAll(predicate,pageable);
    }

    @Override
    public HsCodeMaster getByCode(String code) {
        return hsCodeMasterRepository.getByCode(code);
    }


}
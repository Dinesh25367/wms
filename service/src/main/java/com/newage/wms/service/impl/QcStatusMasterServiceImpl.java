package com.newage.wms.service.impl;

import com.newage.wms.entity.QcStatusMaster;
import com.newage.wms.repository.QcStatusMasterRepository;
import com.newage.wms.service.QcStatusMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QcStatusMasterServiceImpl implements QcStatusMasterService {

    @Autowired
    private QcStatusMasterRepository qcStatusMasterRepository;

    /*
     * Method to get QcStatusMaster by Id
     * @Return QcStatusMaster
     */
    @Override
    public QcStatusMaster getById(Long id) {
        log.info("ENTRY-EXIT - get QcStatusMaster by Id");
        return qcStatusMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.QC_STATUS_ID_NOT_FOUND.CODE,
                        ServiceErrors.QC_STATUS_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to find All QcStatusMaster for autoComplete
     * @Return Iterable<QcStatusMaster>
     */
    @Override
    public Iterable<QcStatusMaster> getAllAutoComplete(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Find All QcStatusMaster for autoComplete");
        return qcStatusMasterRepository.findAll(predicate,pageable);
    }

}

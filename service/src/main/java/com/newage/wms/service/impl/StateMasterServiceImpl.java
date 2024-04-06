package com.newage.wms.service.impl;

import com.newage.wms.entity.StateMaster;
import com.newage.wms.repository.StateMasterRepository;
import com.newage.wms.service.StateMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class StateMasterServiceImpl implements StateMasterService {

    private final StateMasterRepository stateMasterRepository;

    @Autowired
    public StateMasterServiceImpl(StateMasterRepository stateMasterRepository) {
        this.stateMasterRepository = stateMasterRepository;
    }

    /*
     * Method to get all States
     * @Return StateMaster
     */
    @Override
    public Page<StateMaster> getAllStates(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all States");
        return stateMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to get State by Id
     * @Return State Master
     */
    @Override
    public StateMaster getStateById(Long id) {
        log.info("ENTRY-EXIT - get State by Id");
        return stateMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.STATE_ID_NOT_FOUND.CODE));
    }

}

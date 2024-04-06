package com.newage.wms.service.impl;

import com.newage.wms.entity.QTosMaster;
import com.newage.wms.entity.TosMaster;
import com.newage.wms.repository.TosMasterRepository;
import com.newage.wms.service.TosMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Log4j2
public class TosMasterServiceImpl implements TosMasterService {

    private final TosMasterRepository tosMasterRepository;


    @Autowired
    public TosMasterServiceImpl(TosMasterRepository tosMasterRepository) {
        this.tosMasterRepository = tosMasterRepository;
    }

    /*
     * Method to get TosMaster by Id
     * @Return TosMaster
     */
    @Override
    public TosMaster getTosById(Long id) {
        log.info("ENTRY-EXIT - get TosMaster by Id");
        return tosMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.TOS_ID_NOT_FOUND.CODE,
                        ServiceErrors.TOS_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to Save TosMaster
     * @Return TosMaster
     */
    @Override
    public TosMaster createTos(TosMaster tosMaster) {
        log.info("ENTRY-EXIT - Save TosMaster");
        return tosMasterRepository.save(tosMaster);
    }

    /*
     * Method to Update TosMaster
     * @Return TosMaster
     */
    @Override
    public TosMaster updateTos(TosMaster tosMaster) {
        log.info("ENTRY-EXIT - Update TosMaster");
        return tosMasterRepository.save(tosMaster);
    }

    /*
     * Method to delete TosMaster
     */
    @Override
    public void deleteTos(TosMaster tosMaster) {
        log.info("ENTRY-EXIT - delete TosMaster");
        tosMasterRepository.delete(tosMaster);
    }

    /*
     * Method to find All TosMaster with pagination, sort and filter
     * @Return Page<TosMaster>
     */
    @Override
    public Page<TosMaster> getAllTos(Predicate predicate, Pageable pageRequest, String name, String code) {
        log.info("ENTRY-EXIT - Find All TosMaster with pagination, sort and filter");
        QTosMaster qTosMaster = QTosMaster.tosMaster;
        Collection<Predicate> predicates = new ArrayList<>();
        if (name != null && code != null) {
            predicates.add(qTosMaster.name.containsIgnoreCase(name).or(qTosMaster.code.containsIgnoreCase(code)));
        } else if (name != null) {
            predicates.add(qTosMaster.name.containsIgnoreCase(name));
        } else if (code != null) {
            predicates.add(qTosMaster.code.containsIgnoreCase(code));
        }
        predicates.add(predicate);
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        return tosMasterRepository.findAll(predicateAll, pageRequest);
    }

    /*
     * Method to validate TosMaster by name
     */
    @Override
    public void validateNewTosName(String name) {
        log.info("ENTRY-EXIT - validate TosMaster by name");
        if (tosMasterRepository.existsByName(name)) {
            throw new ServiceException(ServiceErrors.TOS_NAME_ALREADY_EXIST.CODE,
                    ServiceErrors.TOS_NAME_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to validate TosMaster by code
     */
    @Override
    public void validateNewTosCode(String code) {
        log.info("ENTRY-EXIT - validate TosMaster by code");
        if (tosMasterRepository.existsByCode(code)) {
            throw new ServiceException(ServiceErrors.TOS_CODE_ALREADY_EXIST.CODE,
                    ServiceErrors.TOS_CODE_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to get all TosMaster for AutoComplete
     * @return Iterable<TosMaster>
     */
    @Override
    public Iterable<TosMaster> getAllAutoComplete(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all Tos Master for AutoComplete");
        return tosMasterRepository.findAll(predicate,pageable);
    }

}

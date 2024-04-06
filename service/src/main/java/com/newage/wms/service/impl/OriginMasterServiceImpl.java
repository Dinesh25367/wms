package com.newage.wms.service.impl;

import com.newage.wms.entity.OriginMaster;
import com.newage.wms.entity.QOriginMaster;
import com.newage.wms.repository.OriginMasterRepository;
import com.newage.wms.service.OriginMasterService;
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
public class OriginMasterServiceImpl implements OriginMasterService{

    private final OriginMasterRepository originMasterRepository;

    @Autowired
    public OriginMasterServiceImpl(OriginMasterRepository originMasterRepository) {
        this.originMasterRepository = originMasterRepository;
    }

    /*
     * Method to get OriginMaster by Id
     * @Return OriginMaster
     */
    @Override
    public OriginMaster getOriginById(Long id) {
        log.info("ENTRY-EXIT - get OriginMaster by Id");
        return originMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.ORIGIN_ID_NOT_FOUND.CODE,
                        ServiceErrors.ORIGIN_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to Save OriginMaster
     * @Return OriginMaster
     */
    @Override
    public OriginMaster createOrigin(OriginMaster originMaster) {
        log.info("ENTRY-EXIT - Save OriginMaster");
        return originMasterRepository.save(originMaster);
    }

    /*
     * Method to Update OriginMaster
     * @Return OriginMaster
     */
    @Override
    public OriginMaster updateOrigin(OriginMaster originMaster) {
        log.info("ENTRY-EXIT - Update OriginMaster");
        return originMasterRepository.save(originMaster);
    }

    /*
     * Method to delete OriginMaster
     */
    @Override
    public void deleteOrigin(OriginMaster originMaster) {
        log.info("ENTRY-EXIT - delete OriginMaster");
        originMasterRepository.delete(originMaster);
    }

    /*
     * Method to find All OriginMaster with pagination, sort and filter
     * @Return Page<OriginMaster>
     */
    @Override
    public Page<OriginMaster> getAllOrigins(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - Find All OriginMaster with pagination, sort and filter");
        return originMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to find All OriginMaster with pagination, sort and filter
     * @Return Page<OriginMaster>
     */
    @Override
    public Page<OriginMaster> getFilteredAllOrigins(Predicate predicate, Pageable pageRequest, String portName,
                                                    String portCode, String countryName) {
        log.info("ENTRY-EXIT - Find All OriginMaster with pagination, sort and filter");
        QOriginMaster qOriginMaster = QOriginMaster.originMaster;
        Collection<Predicate> predicates = new ArrayList<>();
        if (portName != null && !portName.isEmpty() && portCode != null &&
                !portCode.isEmpty() && countryName != null && !countryName.isEmpty()) {
            predicates.add(qOriginMaster.name.startsWithIgnoreCase(portName).
                    or(qOriginMaster.code.startsWithIgnoreCase(portCode)).
                    or(qOriginMaster.country.name.containsIgnoreCase(countryName)));
        } else if (portName != null && !portName.isEmpty() && portCode != null && !portCode.isEmpty()) {
            predicates.add(qOriginMaster.name.startsWithIgnoreCase(portName).
                    or(qOriginMaster.code.startsWithIgnoreCase(portCode)));
        }
        predicates.add(predicate);
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        return originMasterRepository.findAll(predicateAll, pageRequest);
    }

    /*
     * Method to validate OriginMaster by code
     */
    @Override
    public void validateNewOriginCode(String code) {
        log.info("ENTRY-EXIT - validate OriginMaster by code");
        if (originMasterRepository.existsByCode(code)) {
            throw new ServiceException(ServiceErrors.ORIGIN_CODE_ALREADY_EXIST.CODE,
                    ServiceErrors.ORIGIN_CODE_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to validate OriginMaster by name
     */
    @Override
    public void validateNewOriginName(String name) {
        log.info("ENTRY-EXIT - validate OriginMaster by name");
        if (originMasterRepository.existsByName(name)) {
            throw new ServiceException(ServiceErrors.ORIGIN_NAME_ALREADY_EXIST.CODE,
                    ServiceErrors.ORIGIN_NAME_ALREADY_EXIST.KEY);
        }
    }

    /*
     * Method to find all OriginMaster for autocomplete
     * @Return Iterable<OriginMaster>
     */
    @Override
    public Iterable<OriginMaster> getAllAutoComplete(Predicate combinedPredicate, Pageable pageable) {
        log.info("ENTRY-EXIT - find all OriginMaster for autocomplete");
        return originMasterRepository.findAll(combinedPredicate,pageable);
    }

}

package com.newage.wms.service.impl;

import com.newage.wms.entity.PortMaster;
import com.newage.wms.entity.QPortMaster;
import com.newage.wms.repository.PortMasterRepository;
import com.newage.wms.service.PortMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@Service
@Transactional
public class PortMasterServiceImpl implements PortMasterService {

    private final PortMasterRepository portMasterRepository;

    @Autowired
    public PortMasterServiceImpl(PortMasterRepository portMasterRepository) {
        this.portMasterRepository = portMasterRepository;
    }

    /*
    * Method to get Port By Id
    * @Return Port Master
     */
    @Override
    public PortMaster getPortById(Long id) {
        log.info("ENTRY-EXIT - get Port by Id");
        return portMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.PORT_ID_NOT_FOUND.CODE));
    }

    /*
     * Method to create port
     * @return Post Master
     */
    @Override
    public PortMaster createPort(PortMaster portMaster) {
        log.info("ENTRY-EXIT - Create Port");
        return portMasterRepository.save(portMaster);
    }

    /*
     * Method to update port
     * @Return port Master
     */
    @Override
    public PortMaster updatePort(PortMaster portMaster) {
        log.info("ENTRY-EXIT - update Port");
        return portMasterRepository.save(portMaster);
    }

    /*
     * Method to delete port
     */
    @Override
    public void deletePort(PortMaster portMaster) {
        log.info("ENTRY-EXIT - delete Port");
        portMasterRepository.delete(portMaster);
    }

    /*
     * Method to find all port
     * @Return port Master
     */
    @Override
    public Page<PortMaster> getAllPorts(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all Port");
        return portMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to fetch all ports with filter
     * @Return  PortMaster
     */
    @Override
    public Page<PortMaster> getFilteredAllPorts(Predicate predicate, Pageable pageRequest, String portName, String portCode, String countryName) {
        log.info("ENTRY-EXIT - get all Ports with filter");
        QPortMaster qPortMaster = QPortMaster.portMaster;
        Collection<Predicate> predicates = new ArrayList<>();
        if (portName != null && !portName.isEmpty() && portCode != null && !portCode.isEmpty() && countryName != null && !countryName.isEmpty()) {
            predicates.add(qPortMaster.name.startsWithIgnoreCase(portName).or(qPortMaster.code.startsWithIgnoreCase(portCode)).or(qPortMaster.country.name.containsIgnoreCase(countryName)));
        } else if (portName != null && !portName.isEmpty() && portCode != null && !portCode.isEmpty()) {
            predicates.add(qPortMaster.name.startsWithIgnoreCase(portName).or(qPortMaster.code.startsWithIgnoreCase(portCode)));
        }
        predicates.add(predicate);
        log.info("predicates  " + predicates);
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        log.info("predicateAll  " + predicateAll);
        return portMasterRepository.findAll(predicateAll, pageRequest);
    }

    /*
     * Method to validate new port code
     */
    @Override
    public void validateNewPortCode(String code) {
        log.info("ENTRY-EXIT - validate new port code");
        if (portMasterRepository.existsByCode(code)) {
            throw new ServiceException(ServiceErrors.PORT_CODE_ALREADY_EXIST.CODE);
        }
    }

    /*
     * Method to validate new port name
     */
    @Override
    public void validateNewPortName(String name) {
        log.info("ENTRY-EXIT - validate new port name");
        if (portMasterRepository.existsByName(name)) {
            throw new ServiceException(ServiceErrors.PORT_NAME_ALREADY_EXIST.CODE);
        }
    }

}
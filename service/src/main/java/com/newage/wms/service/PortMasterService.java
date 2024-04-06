package com.newage.wms.service;

import com.newage.wms.entity.PortMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PortMasterService {

    PortMaster getPortById(Long id);

    PortMaster createPort(PortMaster portMaster);

    PortMaster updatePort(PortMaster portMaster);

    void deletePort(PortMaster portMaster);

    Page<PortMaster> getAllPorts(Predicate predicate, Pageable pageRequest);

    Page<PortMaster> getFilteredAllPorts(Predicate predicate, Pageable pageRequest, String portName, String portCode, String countryName);

    void validateNewPortCode(String code);

    void validateNewPortName(String name);

}
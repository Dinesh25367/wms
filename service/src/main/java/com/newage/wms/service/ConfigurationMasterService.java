package com.newage.wms.service;

import com.newage.wms.entity.ConfigurationMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConfigurationMasterService {

    Page<ConfigurationMaster> findAll(Predicate predicate, Pageable pageable);

    ConfigurationMaster save(ConfigurationMaster configurationMaster);

    ConfigurationMaster update(ConfigurationMaster configurationMaster);

    ConfigurationMaster getById(Long id);

    Iterable<ConfigurationMaster> getAllForWmsAsnCustoms();

    Iterable<ConfigurationMaster> getAllForWmsSoCustoms();

}

package com.newage.wms.service;

import com.newage.wms.entity.ConfigurationFlagMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConfigurationFlagMasterService {

    Page<ConfigurationFlagMaster> findAll(Predicate predicate, Pageable pageable);

    ConfigurationFlagMaster save(ConfigurationFlagMaster configurationFlagMaster);

    ConfigurationFlagMaster update(ConfigurationFlagMaster configurationFlagMaster);

    ConfigurationFlagMaster getById(Long id);

    Iterable<ConfigurationFlagMaster> fetchAllConfigurationFlag(Predicate predicate, Pageable pageable);

    void validateUniqueConfigurationFlagAttributeSave(String code);

    void validateUniqueConfigurationFlagAttributeUpdate(String code, Long id);
}

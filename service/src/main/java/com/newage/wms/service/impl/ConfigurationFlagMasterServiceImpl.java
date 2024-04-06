package com.newage.wms.service.impl;

import com.newage.wms.entity.ConfigurationFlagMaster;
import com.newage.wms.repository.ConfigurationFlagMasterRepository;
import com.newage.wms.service.ConfigurationFlagMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
@Log4j2
public class ConfigurationFlagMasterServiceImpl implements ConfigurationFlagMasterService {

    @Autowired
    private ConfigurationFlagMasterRepository configurationFlagMasterRepository;

    /*
     * Method to find All ConfigurationFlagMaster with pagination, sort and filter
     * @Return Page<ConfigurationFlagMaster>
     */
    @Override
    public Page<ConfigurationFlagMaster> findAll(Predicate predicate, Pageable pageable){
        log.info("ENTRY-EXIT - Find All Configuration Master with pagination, sort and filter");
        return configurationFlagMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to Save ConfigurationFlagMaster
     * @Return ConfigurationFlagMaster
     */
    @Override
    public ConfigurationFlagMaster save(ConfigurationFlagMaster configurationFlagMaster) {
        log.info("ENTRY-EXIT - Save Configuration Master");
        return configurationFlagMasterRepository.save(configurationFlagMaster);
    }

    /*
     * Method to Update ConfigurationFlagMaster
     * @Return ConfigurationFlagMaster
     */
    @Override
    public ConfigurationFlagMaster update(ConfigurationFlagMaster configurationFlagMaster){
        log.info("ENTRY-EXIT - Update Configuration Master");
        return configurationFlagMasterRepository.save(configurationFlagMaster);
    }

    /*
     * Method to get ConfigurationFlagMaster by Id
     * @Return ConfigurationFlagMaster
     */
    @Override
    public ConfigurationFlagMaster getById(Long id) {
        log.info("ENTRY-EXIT - get ConfigurationMaster by Id");
        return configurationFlagMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.CONFIGURATION_FIELD_NOT_FOUND.CODE,
                        ServiceErrors.CONFIGURATION_FIELD_NOT_FOUND.KEY));
    }

    /*
     * Method to validate unique Configuration Flag attributes in Save
     */
    @Override
    public void validateUniqueConfigurationFlagAttributeSave(String code) {
        log.info("ENTRY - validate unique MoveMent Type attributes in save");
        List<ConfigurationFlagMaster> configurationFlagMasterList = configurationFlagMasterRepository.findAll();
        Boolean codeExists = configurationFlagMasterList.stream()
                .anyMatch(configurationFlagMaster -> configurationFlagMaster.getCode().equals(code));
        if (codeExists) {
            throw new ServiceException(
                    ServiceErrors.CONFIGURATION_FLAG_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.CONFIGURATION_FLAG_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    @Override
    public void validateUniqueConfigurationFlagAttributeUpdate(String code, Long id) {
        log.info("ENTRY - validate unique  Movement Type attributes in update");
        List<ConfigurationFlagMaster> configurationFlagMasterList = configurationFlagMasterRepository.findAll();
        Boolean codeExists = configurationFlagMasterList.stream()
                .anyMatch(configurationFlagMaster -> configurationFlagMaster.getCode().equals(code) && !configurationFlagMaster.getId().equals(id));
        if (codeExists) {
            throw new ServiceException(
                    ServiceErrors.CONFIGURATION_FLAG_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.CONFIGURATION_FLAG_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to get all ConfigurationFlag for autocomplete
     * @return Iterable<StorageAreaMaster>
     */
    @Override
    public Iterable<ConfigurationFlagMaster> fetchAllConfigurationFlag(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all StorageArea");
        return configurationFlagMasterRepository.findAll(predicate,pageable);
    }

}

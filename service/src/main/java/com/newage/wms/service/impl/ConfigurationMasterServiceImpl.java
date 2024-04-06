package com.newage.wms.service.impl;

import com.newage.wms.entity.ConfigurationMaster;
import com.newage.wms.entity.QConfigurationMaster;
import com.newage.wms.repository.ConfigurationMasterRepository;
import com.newage.wms.service.ConfigurationMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ConfigurationMasterServiceImpl implements ConfigurationMasterService {

    @Autowired
    private ConfigurationMasterRepository configurationMasterRepository;

    /*
     * Method to find All Configuration Master with pagination, sort and filter
     * @Return Page<ConfigurationMaster>
     */
    @Override
    public Page<ConfigurationMaster> findAll(Predicate predicate, Pageable pageable){
        log.info("ENTRY-EXIT - Find All Configuration Master with pagination, sort and filter");
        return configurationMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to Save Configuration Master
     * @Return ConfigurationMaster
     */
    @Override
    public ConfigurationMaster save(ConfigurationMaster configurationMaster) {
        log.info("ENTRY-EXIT - Save Configuration Master");
        return configurationMasterRepository.save(configurationMaster);
    }

    /*
     * Method to Update Configuration Master
     * @Return ConfigurationMaster
     */
    @Override
    public ConfigurationMaster update(ConfigurationMaster configurationMaster){
        log.info("ENTRY-EXIT - Update Configuration Master");
        return configurationMasterRepository.save(configurationMaster);
    }

    /*
     * Method to get ConfigurationMaster by Id
     * @Return ConfigurationMaster
     */
    @Override
    public ConfigurationMaster getById(Long id) {
        log.info("ENTRY-EXIT - get ConfigurationMaster by Id");
        return configurationMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.CONFIGURATION_FIELD_NOT_FOUND.CODE,
                        ServiceErrors.CONFIGURATION_FIELD_NOT_FOUND.KEY));
    }

    /*
     * Method to get all Flexi fields for ASN screen customs tab
     * @Return Iterable<ConfigurationMaster>
     */
    @Override
    public Iterable<ConfigurationMaster> getAllForWmsAsnCustoms() {
        log.info("ENTRY-EXIT - get all Flexi fields for ASN screen customs tab");
        QConfigurationMaster qConfigurationMaster = QConfigurationMaster.configurationMaster;
        BooleanBuilder predicate = new BooleanBuilder();
        OrderSpecifier<String> orderSpecifier = qConfigurationMaster.configurationFlag.asc();
        predicate.and(qConfigurationMaster.module.equalsIgnoreCase("wms"));
        predicate.and(qConfigurationMaster.screen.equalsIgnoreCase("asn"));
        predicate.and(qConfigurationMaster.tab.equalsIgnoreCase("customs"));
        predicate.and(qConfigurationMaster.status.eq("Active"));
        return configurationMasterRepository.findAll(predicate,orderSpecifier);
    }

    /*
     * Method to get all Flexi fields for SO screen customs tab
     * @Return Iterable<ConfigurationMaster>
     */
    @Override
    public Iterable<ConfigurationMaster> getAllForWmsSoCustoms() {
        log.info("ENTRY-EXIT - get all Flexi fields for SO screen customs tab");
        QConfigurationMaster qConfigurationMaster = QConfigurationMaster.configurationMaster;
        BooleanBuilder predicate = new BooleanBuilder();
        OrderSpecifier<String> orderSpecifier = qConfigurationMaster.configurationFlag.asc();
        predicate.and(qConfigurationMaster.module.equalsIgnoreCase("wms"));
        predicate.and(qConfigurationMaster.screen.equalsIgnoreCase("so"));
        predicate.and(qConfigurationMaster.tab.equalsIgnoreCase("customs"));
        predicate.and(qConfigurationMaster.status.eq("Active"));
        return configurationMasterRepository.findAll(predicate,orderSpecifier);
    }

}
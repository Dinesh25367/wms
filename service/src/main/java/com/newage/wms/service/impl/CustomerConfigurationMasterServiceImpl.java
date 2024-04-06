package com.newage.wms.service.impl;

import com.newage.wms.entity.CustomerConfigurationMaster;
import com.newage.wms.entity.QCustomerConfigurationMaster;
import com.newage.wms.repository.CustomerConfigurationMasterRepository;
import com.newage.wms.service.CustomerConfigurationMasterService;
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
public class CustomerConfigurationMasterServiceImpl implements CustomerConfigurationMasterService {

    @Autowired
    private CustomerConfigurationMasterRepository customerConfigurationMasterRepository;

    /*
     * Method to get CustomerConfigurationMaster by Id
     * @Return CustomerConfigurationMaster
     */
    @Override
    public CustomerConfigurationMaster getById(Long id) {
        log.info("ENTRY-EXIT - get CustomerConfigurationMaster by Id");
        return customerConfigurationMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.CUSTOMER_CONFIGURATION_FIELD_NOT_FOUND.CODE,
                        ServiceErrors.CUSTOMER_CONFIGURATION_FIELD_NOT_FOUND.KEY));
    }

    @Override
    public CustomerConfigurationMaster saveCustomer(CustomerConfigurationMaster customerConfigurationMaster) {
        log.info("ENTRY - EXIT save All CustomerConfigurationMaster");
        return customerConfigurationMasterRepository.save(customerConfigurationMaster);

    }

    @Override
    public CustomerConfigurationMaster updateCustomer(CustomerConfigurationMaster customerConfigurationMaster) {
        log.info("ENTRY - EXIT Update CustomerConfigurationMaster");
        return customerConfigurationMasterRepository.save(customerConfigurationMaster);
    }

    @Override
    public Page<CustomerConfigurationMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY - EXIT find All CustomerConfigurationMaster ");
        return customerConfigurationMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to find All CustomerConfigurationMaster for autoComplete
     * @Return Iterable<CustomerConfigurationMaster>
     */
    @Override
    public Iterable<CustomerConfigurationMaster> getAll(Predicate predicate, OrderSpecifier<String> orderSpecifier) {
        log.info("ENTRY-EXIT - Find All CustomerConfigurationMaster for autoComplete");
        return customerConfigurationMasterRepository.findAll(predicate,orderSpecifier);
    }

    /*
     * Method to get Flexi fields for ASN screen Additional details tab
     * @Return Iterable<CustomerConfigurationMaster>
     */
    @Override
    public Iterable<CustomerConfigurationMaster> getAllForWmsCustomer(Long customerId) {
        log.info("ENTRY-EXIT - get all Flexi fields for ASN screen Additional details tab");
        QCustomerConfigurationMaster qCustomerConfigurationMaster = QCustomerConfigurationMaster.customerConfigurationMaster;
        BooleanBuilder predicate = new BooleanBuilder();
        OrderSpecifier<String> orderSpecifier = qCustomerConfigurationMaster.configurationFlag.asc();
        predicate.and(qCustomerConfigurationMaster.module.equalsIgnoreCase("wms"));
        predicate.and(qCustomerConfigurationMaster.configurationFlag.containsIgnoreCase("asnheaderadditional"));
        predicate.and(qCustomerConfigurationMaster.customerMaster.id.eq(customerId));
        return customerConfigurationMasterRepository.findAll(predicate,orderSpecifier);
    }

    /*
     * Method to get Flexi fields for SO screen Additional details tab
     * @Return Iterable<CustomerConfigurationMaster>
     */
    @Override
    public Iterable<CustomerConfigurationMaster> getAllForWmsCustomerSo(Long customerId) {
        log.info("ENTRY-EXIT - get all Flexi fields for SO screen Additional details tab");
        QCustomerConfigurationMaster qCustomerConfigurationMaster = QCustomerConfigurationMaster.customerConfigurationMaster;
        BooleanBuilder predicate = new BooleanBuilder();
        OrderSpecifier<String> orderSpecifier = qCustomerConfigurationMaster.configurationFlag.asc();
        predicate.and(qCustomerConfigurationMaster.module.equalsIgnoreCase("wms"));
        predicate.and(qCustomerConfigurationMaster.customerMaster.id.eq(customerId));
        return customerConfigurationMasterRepository.findAll(predicate,orderSpecifier);
    }

}


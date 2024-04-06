package com.newage.wms.service;

import com.newage.wms.entity.CustomerConfigurationMaster;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerConfigurationMasterService {

    CustomerConfigurationMaster getById(Long id);

    CustomerConfigurationMaster saveCustomer(CustomerConfigurationMaster customerConfigurationMaster);

    CustomerConfigurationMaster updateCustomer(CustomerConfigurationMaster customerConfigurationMaster);

    Page<CustomerConfigurationMaster> findAll(Predicate predicate, Pageable pageable);

    Iterable<CustomerConfigurationMaster> getAll(Predicate predicate, OrderSpecifier<String> orderSpecifier);

    Iterable<CustomerConfigurationMaster> getAllForWmsCustomer(Long customerId);

    Iterable<CustomerConfigurationMaster> getAllForWmsCustomerSo(Long customerId);

}

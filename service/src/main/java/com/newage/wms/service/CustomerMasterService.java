package com.newage.wms.service;

import com.newage.wms.entity.CustomerMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerMasterService {

    CustomerMaster getCustomerById(Long id);

    Page<CustomerMaster> getAllCustomer(Predicate predicate, Pageable pageable);

    CustomerMaster getCustomerByCode(String code);
}

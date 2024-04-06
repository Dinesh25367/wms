package com.newage.wms.service.impl;

import com.newage.wms.entity.CustomerMaster;
import com.newage.wms.repository.CustomerMasterRepository;
import com.newage.wms.repository.CustomerProductMasterRepository;
import com.newage.wms.service.CustomerMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Log4j2
@Service
@Transactional
public class CustomerMasterServiceImpl implements CustomerMasterService {

    private final CustomerMasterRepository customerMasterRepository;

    @Autowired
    CustomerProductMasterRepository customerProductMasterRepository;

    @Autowired
    public CustomerMasterServiceImpl(CustomerMasterRepository customerMasterRepository) {
        this.customerMasterRepository = customerMasterRepository;
    }

    /*
     * Method to get CustomerMaster by Id
     * @Return CustomerMaster
     */
    @Override
    public CustomerMaster getCustomerById(Long id) {
        log.info("ENTRY-EXIT - get CustomerMaster by Id");
        return customerMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.CUSTOMER_ID_NOT_FOUND.CODE,
                        ServiceErrors.CUSTOMER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to find All CustomerMaster with pagination, sort and filter
     * @Return Page<CustomerMaster>
     */
    @Override
    public Page<CustomerMaster> getAllCustomer(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Find All CustomerMaster with pagination, sort and filter");
        return customerMasterRepository.findAll(predicate,pageable);
    }

    @Override
    public CustomerMaster getCustomerByCode(String code) {
        return customerMasterRepository.findByCode(code) ;
    }
}


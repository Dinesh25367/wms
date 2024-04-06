package com.newage.wms.service.impl;

import com.newage.wms.entity.OrderTypeMaster;
import com.newage.wms.repository.OrderTypeMasterRepository;
import com.newage.wms.service.OrderTypeMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class OrderTypeMasterServiceImpl implements OrderTypeMasterService {

    @Autowired
    private OrderTypeMasterRepository orderTypeMasterRepository;

    @Override
    public OrderTypeMaster getById(Long id) {
        return orderTypeMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.ORDER_TYPE_ID_NOT_FOUND.CODE,
                        ServiceErrors.ORDER_TYPE_ID_NOT_FOUND.KEY));
    }

    @Override
    public Iterable<OrderTypeMaster> getAllAutoComplete(Predicate predicate, Pageable pageable) {
        return orderTypeMasterRepository.findAll(predicate,pageable);
    }

    @Override
    public OrderTypeMaster save(OrderTypeMaster orderTypeMaster) {
        return orderTypeMasterRepository.save(orderTypeMaster);
    }

    @Override
    public Page<OrderTypeMaster> findAll(Predicate predicate, Pageable pageable) {
        return orderTypeMasterRepository.findAll(predicate,pageable);
    }

    @Override
    public OrderTypeMaster update(OrderTypeMaster orderTypeMaster) {
        return orderTypeMasterRepository.save(orderTypeMaster);
    }

    /*
     * Method to validate unique Movement Type attributes in Save
     */
    @Override
    public void validateUniqueRackAttributeSave(String code) {
        log.info("ENTRY - validate unique MoveMent Type attributes in save");
        List<OrderTypeMaster> orderTypeMasterList = orderTypeMasterRepository.findAll();
        Boolean codeExists = orderTypeMasterList.stream()
                .anyMatch(orderTypeMaster -> orderTypeMaster.getCode().equals(code));
        if (codeExists) {
            throw new ServiceException(
                    ServiceErrors.ORDER_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.ORDER_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    @Override
    public void validateUniqueRackAttributeUpdate(String code, Long id) {
        log.info("ENTRY - validate unique  Movement Type attributes in update");
        List<OrderTypeMaster> orderTypeMasterList = orderTypeMasterRepository.findAll();
        Boolean codeExists = orderTypeMasterList.stream()
                .anyMatch(orderTypeMaster -> orderTypeMaster.getCode().equals(code) && !orderTypeMaster.getId().equals(id));
        if (codeExists) {
            throw new ServiceException(
                    ServiceErrors.ORDER_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.ORDER_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }
}

package com.newage.wms.service;

import com.newage.wms.entity.TrnHeaderAsn;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface TrnHeaderAsnService {

    Page<TrnHeaderAsn> findAll(Predicate predicate, Pageable pageable, Date fromDate,Date toDate,String dateFilter);

    Page<TrnHeaderAsn> findAll(Predicate predicate, Pageable pageable);

    TrnHeaderAsn save(TrnHeaderAsn trnHeaderAsn);

    TrnHeaderAsn update(TrnHeaderAsn trnHeaderAsn);

    TrnHeaderAsn findById(Long id);

    String generateTransactionUid();

    void validateUniqueCustomerOrderNoSave(String customerOrderNo,Long warehouseId,Long customerId);

    void validateUniqueCustomerOrderNoUpdate(String customerOrderNo, Long id,Long warehouseId,Long customerId);

    void validateUniqueCustomerInvoiceNoSave(String customerInvoiceNo,Long warehouseId,Long customerId);

    void validateUniqueCustomerInvoiceNoUpdate(String customerInvoiceNo, Long id,Long warehouseId,Long customerId);

    String getTransactionStatus(TrnHeaderAsn trnHeaderAsn);

}

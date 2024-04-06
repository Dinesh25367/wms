package com.newage.wms.service;

import com.newage.wms.entity.GrnDetail;
import com.newage.wms.entity.GrnHeader;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GrnHeaderService {

    Page<GrnHeader> findAll(Predicate predicate, Pageable pageable);

    Iterable<GrnHeader> findAllAutoComplete(Predicate predicate, Pageable pageable);

    GrnHeader save(GrnHeader grnHeader);

    GrnHeader update(GrnHeader grnHeader);

    GrnHeader findById(Long id);

    String generateTransactionUidDirect();

    String generateGrnReference();

    boolean checkIfAsnCanBeCancelledFromGrn(Long trnId);

    boolean isCancelled(Long id);

    boolean isUnCompletedGrnPresent(Long id);

    boolean checkIfAsnHeaderCanBeEditableFromGrn(Long trnId);

    GrnHeader getIncompleteGrn(Long transactionId);

    List<GrnDetail> getCompletedGrnListForAsn(Long transactionId);

    void validateGrnForSave(Long transactionId);

    void validateGrnForUpdate(Long transactionId,Long grnId);

    String getPutAwayTaskStatus(GrnHeader grnHeader);

}
package com.newage.wms.service;

import com.newage.wms.entity.TransactionLot;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TransactionLotService {

    List<TransactionLot> findAll();

    TransactionLot save(TransactionLot transactionLot);

}

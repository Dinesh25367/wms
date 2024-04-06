package com.newage.wms.service.impl;

import com.newage.wms.entity.TransactionHistory;
import com.newage.wms.entity.TransactionLot;
import com.newage.wms.repository.TransactionLotRepository;
import com.newage.wms.service.TransactionLotService;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class TransactionLotServiceImpl implements TransactionLotService {


    @Autowired
    private TransactionLotRepository transactionLotRepository;

    @Override
    public List<TransactionLot> findAll() {
        return transactionLotRepository.findAll();
    }

    @Override
    public TransactionLot save(TransactionLot transactionLot) {
        return transactionLotRepository.save(transactionLot);
    }

}

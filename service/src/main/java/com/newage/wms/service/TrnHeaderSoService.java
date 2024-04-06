package com.newage.wms.service;

import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.entity.TrnHeaderSo;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrnHeaderSoService {

    TrnHeaderSo findById(Long id);

    void deleteById(Long id);

    String generateTransactionUid();
}

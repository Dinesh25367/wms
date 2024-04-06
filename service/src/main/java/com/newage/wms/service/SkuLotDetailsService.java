package com.newage.wms.service;

import com.newage.wms.entity.SkuLotDetails;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkuLotDetailsService {

    Page<SkuLotDetails> findAll(Predicate predicate, Pageable pageable);

    SkuLotDetails findById(Long id);

    void deleteAllInIterable(Iterable<SkuLotDetails> skuLotDetailsIterable);

}
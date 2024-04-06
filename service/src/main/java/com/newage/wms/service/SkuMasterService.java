package com.newage.wms.service;

import com.newage.wms.entity.SkuMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SkuMasterService {

    Page<SkuMaster> getAll(Predicate predicate, Pageable pageable);

    SkuMaster save(SkuMaster skuMaster);

    SkuMaster update(SkuMaster skuMaster);

    SkuMaster getById(Long id);

    void validateUniqueSkuAttributeSave(String code,Long customerId);

    void validateUniqueSkuAttributeUpdate(String code,Long id,Long customerId);

    List<SkuMaster> findAll();

    List<SkuMaster> saveAll(List<SkuMaster> skuMasterList);

}

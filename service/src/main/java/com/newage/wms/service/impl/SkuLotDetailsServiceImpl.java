package com.newage.wms.service.impl;

import com.newage.wms.entity.SkuLotDetails;
import com.newage.wms.repository.SkuLotDetailsRepository;
import com.newage.wms.service.SkuLotDetailsService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SkuLotDetailsServiceImpl implements SkuLotDetailsService {

    @Autowired
    private SkuLotDetailsRepository skuLotDetailsRepository;

    @Override
    public Page<SkuLotDetails> findAll(Predicate predicate, Pageable pageable) {
        return skuLotDetailsRepository.findAll(predicate,pageable);
    }

    @Override
    public SkuLotDetails findById(Long id) {
        return skuLotDetailsRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.SKU_LOT_ID_NOT_FOUND.CODE,
                        ServiceErrors.SKU_LOT_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to delete all skuLotDetailsIterable Iterable
     */
    @Override
    public void deleteAllInIterable(Iterable<SkuLotDetails> skuLotDetailsIterable) {
        log.info("ENTRY-EXIT - Delete all skuLotDetailsIterable Iterable");
        skuLotDetailsRepository.deleteAll(skuLotDetailsIterable);
    }

}

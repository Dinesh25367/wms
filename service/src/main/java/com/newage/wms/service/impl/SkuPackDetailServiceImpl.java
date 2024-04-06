package com.newage.wms.service.impl;

import com.newage.wms.entity.SkuPackDetail;
import com.newage.wms.repository.SkuPackDetailRepository;
import com.newage.wms.service.SkuPackDetailService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SkuPackDetailServiceImpl implements SkuPackDetailService {

    @Autowired
    private SkuPackDetailRepository skuPackDetailRepository;

    /*
     *Method to find SkuPackDetail by id
     * @Return SkuPackDetail
     */
    @Override
    public SkuPackDetail getById(Long id) {
        log.info("ENTRY-EXIT - find SkuPackDetail by id");
        return skuPackDetailRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.SKU_PACK_ID_NOT_FOUND.CODE,
                        ServiceErrors.SKU_PACK_ID_NOT_FOUND.KEY));
    }

}
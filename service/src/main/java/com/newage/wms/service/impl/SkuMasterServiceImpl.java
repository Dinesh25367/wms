package com.newage.wms.service.impl;

import com.newage.wms.entity.SkuMaster;
import com.newage.wms.repository.SkuMasterRepository;
import com.newage.wms.service.SkuMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Log4j2
@Service
public class SkuMasterServiceImpl implements SkuMasterService {

    @Autowired
    private SkuMasterRepository skuMasterRepository;

    /*
     * Method to find all Sku with sort, pagination and filter
     * @Return SkuMaster
     */
    @Override
    public Page<SkuMaster> getAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - find all Sku with sort, pagination and filter");
        return skuMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save Sku
     * @Return SkuMaster
     */
    @Override
    public SkuMaster save(SkuMaster skuMaster) {
        log.info("ENTRY-EXIT - save Sku");
        return skuMasterRepository.save(skuMaster);
    }

    /*
     * Method to update Sku by id
     */
    @Override
    public SkuMaster update(SkuMaster skuMaster) {
        log.info("ENTRY-EXIT - update Sku");
        return skuMasterRepository.save(skuMaster);
    }

    /*
     * Method to find Sku by id
     * @Return SkuMaster
     */
    @Override
    public SkuMaster getById(Long id) {
        log.info("ENTRY-EXIT - find Sku by id");
        return skuMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.SKU_ID_NOT_FOUND.CODE,
                        ServiceErrors.SKU_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to validate unique Sku attribute in save
     */
    @Override
    public void validateUniqueSkuAttributeSave(String code,Long customerId) {
        log.info("ENTRY - validate unique Sku attribute in save");
        List<SkuMaster> skuMasterList = skuMasterRepository.findAll();
        Boolean codeExists = skuMasterList.stream()
                .anyMatch(skuMaster -> skuMaster.getCode().equals(code) && skuMaster.getCustomerMaster().getId().equals(customerId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.SKU_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.SKU_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
        log.info("EXIT");
    }

    /*
     * Method to validate unique Sku attribute in update
     */
    @Override
    public void validateUniqueSkuAttributeUpdate(String code, Long id,Long customerId) {
        log.info("ENTRY - validate unique Sku attribute in update");
        List<SkuMaster> skuMasterList = skuMasterRepository.findAll();
        Boolean codeExists = skuMasterList.stream()
                .anyMatch(skuMaster -> skuMaster.getCode().equals(code)
                        && !skuMaster.getId().equals(id) && skuMaster.getCustomerMaster().getId().equals(customerId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.SKU_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.SKU_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
        log.info("EXIT");
    }

    /*
     * Method to get all SkuMaster for autocomplete
     * @return List<SkuMaster>
     */
    @Override
    public List<SkuMaster> findAll() {
        log.info("ENTRY-EXIT - Get all SkuMaster");
        return skuMasterRepository.findAll();
    }

    @Override
    public List<SkuMaster> saveAll(List<SkuMaster> skuMasterList) {
        return skuMasterRepository.saveAll(skuMasterList);
    }


}

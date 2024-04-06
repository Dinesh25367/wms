package com.newage.wms.service.impl;

import com.newage.wms.entity.InventoryStatusMaster;
import com.newage.wms.repository.InventoryStatusMasterRepository;
import com.newage.wms.service.InventoryStatusMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Log4j2
public class InventoryStatusMasterServiceImpl implements InventoryStatusMasterService {

    @Autowired
    private InventoryStatusMasterRepository inventoryStatusMasterRepository ;

    @Override
    public InventoryStatusMaster getById(Long id) {
        log.info("ENTRY - EXIT Get Inventory Status Master By Id");
        return inventoryStatusMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.INVENTORY_STATUS_MASTER_ID_NOT_FOUND.CODE,
                        ServiceErrors.INVENTORY_STATUS_MASTER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to save new InventoryStatusMaster
     * @return  InventoryStatusMaster
     */
    @Override
    public InventoryStatusMaster saveInventory(InventoryStatusMaster inventoryStatusMaster){
        log.info("ENTRY - EXIT save all inventoryStatusMaster");
        return inventoryStatusMasterRepository.save(inventoryStatusMaster);
    }

    @Override
    public InventoryStatusMaster updateInventory(InventoryStatusMaster inventoryStatusMaster) {
        log.info("ENTRY - EXIT - Update InventoryStatusMAster");
        return inventoryStatusMasterRepository.save(inventoryStatusMaster);
    }

    @Override
    public void validateUniqueInventoryStatusAttributeSave(String code) {
        log.info("ENTRY - validate Unique InventoryStatus attribute in save");
        List<InventoryStatusMaster>inventoryStatusMasterList=inventoryStatusMasterRepository.findAll();
        Boolean codeExists=inventoryStatusMasterList.stream()
                .anyMatch(inventoryStatusMaster -> inventoryStatusMaster.getCode().equals(code));

        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.INVENTORY_STATUS_MASTER_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.INVENTORY_STATUS_MASTER_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    @Override
    public void validateUniqueInventoryStatusAttributeUpdate(String code, Long id) {
        log.info("ENTRY - validate unique InventoryStatus Attribute in update");
        List<InventoryStatusMaster>inventoryStatusMasterList=inventoryStatusMasterRepository.findAll();
        Boolean codeExists=inventoryStatusMasterList.stream()
                .anyMatch(inventoryStatusMaster ->  inventoryStatusMaster.getCode().equals(code) && !inventoryStatusMaster.getId().equals(id));
        if (codeExists){
            throw  new ServiceException(
                    ServiceErrors.INVENTORY_STATUS_MASTER_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.INVENTORY_STATUS_MASTER_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }

    }

    @Override
    public Page<InventoryStatusMaster> findAll(Predicate predicate, Pageable pageable){
        log.info("ENTRY - EXIT - find InventoryStatusMAster By id");
        return inventoryStatusMasterRepository.findAll(predicate,pageable);
    }

}

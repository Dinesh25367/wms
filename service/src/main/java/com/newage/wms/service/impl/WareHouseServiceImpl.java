package com.newage.wms.service.impl;

import com.newage.wms.entity.QWareHouseMaster;
import com.newage.wms.entity.WareHouseMaster;
import com.newage.wms.repository.WareHouseRepository;
import com.newage.wms.service.WareHouseService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import static com.newage.wms.service.exception.ServiceErrors.WAREHOUSE_ID_NOT_FOUND;

@Log4j2
@Service
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    private WareHouseRepository wareHouseRepository;

    /*
     * Method to list all WareHouse with pagination, sort and filter
     * @return WareHouse Page
     */
    @Override
    public Page<WareHouseMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY - List all WareHouse with pagination, sort and filter");
        Page<WareHouseMaster> wareHousePage = wareHouseRepository.findAll(predicate,pageable);
        log.info("EXIT");
        return wareHousePage;
    }

    /*
     * Method to create new WareHouse
     * @return WareHouse
     */
    @Override
    public WareHouseMaster saveWareHouse(WareHouseMaster wareHouse) {
        log.info("ENTRY-EXIT - Create WareHouse");
        return wareHouseRepository.save(wareHouse);
    }

    /*
     * Method to update WareHouse
     * @return WareHouse
     */
    @Override
    public WareHouseMaster updateWareHouse(WareHouseMaster wareHouse) {
        log.info("ENTRY-EXIT - Update WareHouse by Id");
        return wareHouseRepository.save(wareHouse);
    }

    /*
     * Method to fetch WareHouse by Id
     * @return WareHouse
     */
    @Override
    public WareHouseMaster getWareHouseById(Long id) {
        log.info("ENTRY-EXIT - Get WareHouse by Id");
        return wareHouseRepository.findById(id).
                orElseThrow(() -> new com.newage.wms.service.exception.ServiceException(WAREHOUSE_ID_NOT_FOUND.CODE,
                        WAREHOUSE_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to count WareHouse
     * @return count of WareHouses
     */
    @Override
    public Long getWareHouseCount() {
        log.info("ENTRY-EXIT - Get WareHouse count");
        return wareHouseRepository.count();
    }

    /*
     * Method to fetch WareHouse by code
     * @return WareHouse
     */
    @Override
    public WareHouseMaster getWareHouseByCode(String code) {
        return wareHouseRepository.findByCode(code);
    }

    /*
     * Method to generate new WareHouseId
     * @return new WareHouseId
     */
    @Override
    public String generateNewWareHouseId() {
        log.info("ENTRY - generate new WareHouse Id");
        List<WareHouseMaster> wareHouseList = wareHouseRepository.findAll();
        if (wareHouseList.isEmpty()){
            return "WH1000001";
        }
        else {
            WareHouseMaster maxWareHouse = wareHouseList.stream()
                    .max(Comparator.comparing(WareHouseMaster::getWareHouseId)).orElse(wareHouseList.get(0));
            String maxWareHouseId = maxWareHouse.getWareHouseId();
            if (maxWareHouseId.isEmpty()){
                return "WH1000001";
            }else {
                String maxWareHouseIdNumericPart = maxWareHouseId.replaceFirst("^" + "WH", "");
                try {
                    Integer newWareHouseIdNumericPart = Integer.parseInt(maxWareHouseIdNumericPart) + 1;
                    return "WH" + newWareHouseIdNumericPart;
                } catch (NumberFormatException e) {
                    // Handle parsing error, if any
                    throw new ServiceException(com.newage.wms.service.exception.ServiceErrors.UNABLE_TO_GENERATE_NEW_WAREHOUSE_ID.CODE);
                }
            }
        }
    }

    /*
     * Method to generate version
     * @Return Long version
     * */
    @Override
    public Long generateVersion() {
        List<WareHouseMaster> wareHouseList = wareHouseRepository.findAll();
        if (wareHouseList.isEmpty()){
            return 1L;
        }
        else {
            WareHouseMaster maxWareHouse = wareHouseList.stream()
                    .max(Comparator.comparing(WareHouseMaster::getVersion)).orElse(wareHouseList.get(0));
            Long version = maxWareHouse.getVersion();
            if (version == null){
                return 1L;
            }else {
                return version+1;
            }
        }
    }

    /*
     * Method to validate unique warehouseRequestDto attributes in Save
     */
    @Override
    public void validateUniqueWareHouseAttributeSave(String code){
        log.info("ENTRY - validate unique warehouseRequestDto attributes in save");
        List<WareHouseMaster> wareHouseList = wareHouseRepository.findAll();
        Boolean codeExists = wareHouseList.stream()
                .anyMatch(wareHouse -> wareHouse.getCode().equals(code));
        if (codeExists){
            throw new com.newage.wms.service.exception.ServiceException(
                    com.newage.wms.service.exception.ServiceErrors.WAREHOUSE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    com.newage.wms.service.exception.ServiceErrors.WAREHOUSE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique warehouseRequestDto attributes in Update
     */
    @Override
    public void validateUniqueWareHouseAttributeUpdate(String code,Long id){
        log.info("ENTRY - validate unique warehouseRequestDto attributes in update");
        List<WareHouseMaster> wareHouseList = wareHouseRepository.findAll();
        Boolean codeExists = wareHouseList.stream()
                .anyMatch(wareHouse -> wareHouse.getCode().equals(code) && !wareHouse.getId().equals(id));
        if (codeExists){
            throw new com.newage.wms.service.exception.ServiceException(
                    com.newage.wms.service.exception.ServiceErrors.WAREHOUSE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    com.newage.wms.service.exception.ServiceErrors.WAREHOUSE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    @Override
    public Page<WareHouseMaster> fetchWarehouseList(String code, String name,
                                                   String wareHouseLocationPrefix, String isBonded, Long branchId, String status,Pageable pageable) {
        log.info("ENTRY-EXIT - Get all Warehouse ");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QWareHouseMaster.wareHouseMaster.id.isNotNull());
        if (name != null && !name.isEmpty() && !name.isBlank()){
            predicates.add(QWareHouseMaster.wareHouseMaster.name.containsIgnoreCase(name));
        }
        if (code != null && !code.isEmpty() && !code.isBlank()){
            predicates.add(QWareHouseMaster.wareHouseMaster.code.containsIgnoreCase(code));
        }
        if (wareHouseLocationPrefix != null && !wareHouseLocationPrefix.isEmpty() && !wareHouseLocationPrefix.isBlank()){
            predicates.add(QWareHouseMaster.wareHouseMaster.wareHouseLocationPrefix.containsIgnoreCase(wareHouseLocationPrefix));
        }
        if (isBonded != null && !isBonded.isEmpty() && !isBonded.isBlank()) {
            predicates.add(getIsBondedPredicate(isBonded));
        }
        if (branchId != null){
            predicates.add(QWareHouseMaster.wareHouseMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QWareHouseMaster.wareHouseMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        return wareHouseRepository.findAll(predicateAll,pageable);
    }

    private Predicate getIsBondedPredicate(String isBonded) {
        Predicate predicate = null;
        predicate = QWareHouseMaster.wareHouseMaster.isBonded.eq('Z');
        if ("yes".contains(isBonded.toLowerCase())){
            predicate = QWareHouseMaster.wareHouseMaster.isBonded.eq('Y');
        }
        if ("no".contains(isBonded.toLowerCase())){
            predicate = QWareHouseMaster.wareHouseMaster.isBonded.eq('N');
        }
        return predicate;
    }

}

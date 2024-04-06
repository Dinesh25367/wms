package com.newage.wms.service.impl;

import com.newage.wms.entity.AisleMaster;
import com.newage.wms.entity.QAisleMaster;
import com.newage.wms.repository.AisleMasterRepository;
import com.newage.wms.service.AisleMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@Service
public class AisleMasterServiceImpl implements AisleMasterService {

    @Autowired
    private AisleMasterRepository aisleMasterRepository;

    /*
     * Method to get all Aisle
     * @return Page<AisleMaster>
     */
    @Override
    public Page<AisleMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all Aisle");
        return aisleMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save new Aisle
     * @return AisleMaster
     */
    @Override
    public AisleMaster saveAisle(AisleMaster aisleMaster) {
        log.info("ENTRY-EXIT - Save new Aisle");
        return aisleMasterRepository.save(aisleMaster);
    }

    /*
     * Method to update existing Aisle
     * @return AisleMaster
     */
    @Override
    public AisleMaster updateAisle(AisleMaster aisleMaster) {
        log.info("ENTRY-EXIT - Update Aisle");
        return aisleMasterRepository.save(aisleMaster);
    }

    /*
     * Method to get Aisle by id
     * @return AisleMaster
     */
    @Override
    public AisleMaster getAisleById(Long id) {
        log.info("ENTRY-EXIT - Get Aisle by id");
        return aisleMasterRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.AISLE_MASTER_ID_NOT_FOUND.CODE,
                        ServiceErrors.AISLE_MASTER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to Get all Aisle in AutoComplete
     * @return Iterable<AisleMaster>
     */
    @Override
    public Iterable<AisleMaster> fetchAllAisle(String query, Long warehouseId, Long zoneId,
                                               Long userId, Long branchId,String status) {
        log.info("ENTRY-EXIT - Get all Aisle in AutoComplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QAisleMaster.aisleMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QAisleMaster.aisleMaster.name.containsIgnoreCase(query)
                    .or(QAisleMaster.aisleMaster.code.containsIgnoreCase(query)));
        }
        if (warehouseId != null){
            predicates.add(QAisleMaster.aisleMaster.wareHouseMaster.id.eq(warehouseId));
        }
        if (zoneId != null){
            predicates.add(QAisleMaster.aisleMaster.zoneMaster.id.eq(zoneId));
        }
        if (branchId != null){
            predicates.add(QAisleMaster.aisleMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QAisleMaster.aisleMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        return aisleMasterRepository.findAll(predicateAll,pageable);
    }

    /*
     * Method to get Aisle by code
     * @return AisleMaster
     */
    @Override
    public AisleMaster getAisleByCode(String aisleCode) {
        log.info("ENTRY-EXIT - Get Aisle by code");
        return aisleMasterRepository.findByCode(aisleCode);
    }

    /*
     * Method to validate unique aisle attributes in Save
     */
    @Override
    public void validateUniqueAisleAttributeSave(String code,Long warehouseId){
        log.info("ENTRY - validate unique aisle attributes in save");
        List<AisleMaster> aisleMasterList = aisleMasterRepository.findAll();
        Boolean codeExists = aisleMasterList.stream()
                .anyMatch(aisle -> aisle.getCode().equals(code) && aisle.getWareHouseMaster().getId().equals(warehouseId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique aisle attributes in Update
     */
    @Override
    public void validateUniqueAisleAttributeUpdate(String code,Long id,Long warehouseId){
        log.info("ENTRY - validate unique aisle attributes in update");
        List<AisleMaster> aisleMasterList = aisleMasterRepository.findAll();
        Boolean codeExists = aisleMasterList.stream()
                .anyMatch(aisle -> (aisle.getCode().equals(code) && aisle.getWareHouseMaster().getId().equals(warehouseId)) && !aisle.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}

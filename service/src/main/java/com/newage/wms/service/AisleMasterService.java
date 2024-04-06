package com.newage.wms.service;

import com.newage.wms.entity.AisleMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AisleMasterService {

    Page<AisleMaster> findAll(Predicate predicate, Pageable pageable);

    AisleMaster saveAisle(AisleMaster aisleMaster);

    AisleMaster updateAisle(AisleMaster aisleMaster);

    AisleMaster getAisleById(Long id);

    Iterable<AisleMaster> fetchAllAisle(String query, Long warehouseId, Long zoneId,
                                        Long userId, Long branchId,String status);

    AisleMaster getAisleByCode(String aisleCode);

    void validateUniqueAisleAttributeSave(String code,Long warehouseId);

    void validateUniqueAisleAttributeUpdate(String code,Long id,Long warehouseId);

}

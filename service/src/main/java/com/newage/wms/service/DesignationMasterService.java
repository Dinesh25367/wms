package com.newage.wms.service;

import com.newage.wms.entity.DesignationMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DesignationMasterService {

    void validateNewDesignationName(String name);

    void validateNewDesignationCode(String code);

    DesignationMaster getDesignationById(Long id);

    DesignationMaster createDesignation(DesignationMaster designationMaster);

    DesignationMaster updateDesignation(DesignationMaster designationMaster);

    void deleteDesignation(DesignationMaster designationMaster);

    Page<DesignationMaster> getAllDesignation(Predicate predicate, Pageable pageRequest);

    Iterable<DesignationMaster> getAllDesignationAutoSearch(Predicate predicate, Pageable pageable);

}
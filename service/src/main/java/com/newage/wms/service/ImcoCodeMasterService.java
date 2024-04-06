package com.newage.wms.service;

import com.newage.wms.entity.ImcoCodeMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImcoCodeMasterService {

    ImcoCodeMaster getById(Long id);

    Iterable<ImcoCodeMaster> getAllAutoComplete(Predicate predicate, Pageable pageable);

    ImcoCodeMaster saveImcoCode(ImcoCodeMaster imcoCodeMaster);

    Page<ImcoCodeMaster> findAll(Predicate predicate, Pageable pageable);

    ImcoCodeMaster updateImcoCodeMaster (ImcoCodeMaster imcoCodeMaster);

    void validateUniqueImcoAttributeSave(String code);

    void validateUniqueImcoAttributeUpdate(String code,Long id);

    ImcoCodeMaster getByCode(String code);
}

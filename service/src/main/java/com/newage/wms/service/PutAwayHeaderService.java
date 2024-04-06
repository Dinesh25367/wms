package com.newage.wms.service;

import com.newage.wms.entity.PutAwayTaskHeader;

import java.util.List;
import java.util.Optional;


public interface PutAwayHeaderService {


    PutAwayTaskHeader save(PutAwayTaskHeader putAwayTaskHeader);

    PutAwayTaskHeader findById(Long id);

    List<PutAwayTaskHeader> fetchAll();

    Optional<PutAwayTaskHeader> getPutAwayTaskForGRNIfExists(Long grnId);

    String generatePutAwayTaskId();

    String getPutAwayTaskIdFormat();
}

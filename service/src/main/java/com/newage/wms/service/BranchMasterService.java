package com.newage.wms.service;

import com.newage.wms.entity.BranchMaster;

public interface BranchMasterService {

    BranchMaster getBranchById(Long id);

    Long getCountryIdByBranchMasterId(Long id);

}

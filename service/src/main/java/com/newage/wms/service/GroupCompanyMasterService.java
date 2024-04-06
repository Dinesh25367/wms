package com.newage.wms.service;

import com.newage.wms.entity.GroupCompanyMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupCompanyMasterService {

    void validateNewCode(String code);

    void validateNewName(String name);

    void validateNewReportingName(String reportingName);

    GroupCompanyMaster getGroupCompanyById(Long id);

    GroupCompanyMaster createGroupCompany(GroupCompanyMaster groupCompanyMaster);

    GroupCompanyMaster updateGroupCompany(GroupCompanyMaster groupCompanyMaster);

    void deleteGroupCompany(GroupCompanyMaster groupCompanyMaster);

    Page<GroupCompanyMaster> getAllGroupCompanies(Predicate predicate, Pageable pageRequest);


}

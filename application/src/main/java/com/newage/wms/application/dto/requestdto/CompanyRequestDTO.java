package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestDTO {

    private String code;
    private String name;
    private String reportingName;
    private String logo;
    private String financialYear;
    private String startMonth;
    private String endMonth;
    private String status;
    private Long groupCompanyId;
    List<BranchRequestDTO> branchMasterList;

}
package com.newage.wms.application.dto.responsedto;

import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDTO {

    private Long id;
    private String code;
    private String name;
    private String reportingName;
    private String logo;
    private String financialYear;
    private String startMonth;
    private String endMonth;
    private String status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private GroupCompanyMasterDTO groupCompany;
    private List<BranchResponseDTO> branchMasterList;

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupCompanyMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

}

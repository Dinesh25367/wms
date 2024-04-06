package com.newage.wms.application.dto.responsedto;

import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupCompanyResponseDTO {

    private Long id;
    private String code;
    private String name;
    private String reportingName;
    private String logo;
    private String status;
    private List<CompanyMasterDto> companyDetails;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class CompanyMasterDto {

        private Long id;
        private String name;
        private String code;
        private String status;
        private List<BranchMasterDto> branchDetails;

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class BranchMasterDto {

        private Long id;
        private String name;
        private String code;

    }

}

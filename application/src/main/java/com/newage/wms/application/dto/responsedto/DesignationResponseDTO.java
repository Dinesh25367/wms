package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignationResponseDTO {

    private Long id;
    private String code;
    private String name;
    private String status;
    private String note;
    private String type;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private DepartmentMasterDTO department;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartmentMasterDTO {

        private Long id;
        private String name;

    }

}

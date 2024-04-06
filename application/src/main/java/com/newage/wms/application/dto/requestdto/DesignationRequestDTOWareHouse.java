package com.newage.wms.application.dto.requestdto;

import com.newage.wms.application.dto.responsedto.DesignationResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignationRequestDTOWareHouse {

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String id;
    private String code;
    private String name;
    private String status;
    private String note;
    private String type;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private DesignationResponseDTO.DepartmentMasterDTO department;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartmentMasterDTO {

        private Long id;
        private String name;

    }

}
package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupCompanyRequestDTO {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    private String reportingName;

    private String logo;
    private String status;
    private Long companyId;

}
